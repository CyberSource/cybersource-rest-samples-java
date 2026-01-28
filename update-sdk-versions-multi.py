#!/usr/bin/env python3
"""
CyberSource SDK Version Updater (Multi-Language)
Fetches latest releases from GitHub and updates SDK version JSON files for all configured languages

Usage:
  Basic (updates latest_version and last_updated only):
    python update-sdk-versions-multi.py --enable-pr --token ghp_xxx
  
  With version history (also adds to versions array):
    python update-sdk-versions-multi.py --add-to-versions-list --enable-pr --token ghp_xxx
"""
import os
import sys
import json
import argparse
import requests
import subprocess
import shutil
from datetime import datetime, timezone
from typing import Dict, Optional, Tuple, List


class SDKVersionUpdater:
    def __init__(self, config_path: str = "config.json", github_token: Optional[str] = None):
        """Initialize the updater with configuration"""
        self.config = self.load_config(config_path)
        # Priority: CLI argument > environment variable > config file
        self.github_token = github_token or os.environ.get("GITHUB_TOKEN") or self.config.get("github_token", "")
        self.add_to_versions_list = self.config.get("add_to_versions_list", False)
        self.languages = self.config.get("languages", {})
        self.sdk_support_repo = self.config.get("sdk_support_repo", "cybersource-mcp-sdk-support-files")
        self.pr_base_branch = self.config.get("pr_base_branch", "test-all-sdk")
        self.pr_target_branch = self.config.get("pr_target_branch", "test-all-sdk")
        
    def load_config(self, config_path: str) -> Dict:
        """Load configuration from JSON file"""
        if os.path.exists(config_path):
            with open(config_path, 'r') as f:
                return json.load(f)
        return {}
    
    def validate_config(self) -> bool:
        """Validate configuration before execution"""
        if not self.sdk_support_repo:
            print("Error: No sdk_support_repo configured in config.json")
            return False
        
        if not self.languages:
            print("Error: No languages configured in config.json")
            return False
        
        enabled_languages = [lang for lang, cfg in self.languages.items() if cfg.get("enabled", False)]
        if not enabled_languages:
            print("Error: No languages are enabled in config.json")
            return False
        
        print(f"Validating {len(enabled_languages)} enabled language(s): {', '.join(enabled_languages)}")
        
        # Validate each enabled language has required fields
        for lang_name, lang_config in self.languages.items():
            if not lang_config.get("enabled", False):
                continue
            
            required_fields = ["sdk_repo", "json_file", "tag_format"]
            missing = [f for f in required_fields if not lang_config.get(f)]
            if missing:
                print(f"Error: Language '{lang_name}' missing required fields: {', '.join(missing)}")
                return False
        
        print(f"Configuration validated for {len(enabled_languages)} language(s)")
        return True
    
    def get_latest_release(self, lang_config: Dict) -> Optional[Dict]:
        """Fetch the latest release from GitHub API"""
        owner = "CyberSource"
        repo = lang_config["sdk_repo"]
        url = f"https://api.github.com/repos/{owner}/{repo}/releases/latest"
        
        headers = {}
        if self.github_token:
            headers["Authorization"] = f"token {self.github_token}"
        
        try:
            # Disable SSL verification for corporate proxy #kept import here to be removed later
            import urllib3
            urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
            
            response = requests.get(url, headers=headers, verify=False)
            response.raise_for_status()
            return response.json()
        except requests.RequestException as e:
            print(f"Error fetching release from GitHub: {e}")
            return None
    
    def parse_release_data(self, release: Dict, lang_config: Dict) -> Dict:
        """Parse GitHub release data into our format"""
        tag_name = release.get("tag_name", "")
        tag_format = lang_config.get("tag_format", "{version}")
        
        # Extract version from tag using tag_format
        # tag_format examples: "cybersource-rest-client-java-{version}", "{version}", "v{version}"
        if "{version}" in tag_format:
            # Remove the format parts to extract version
            version = tag_name
            # Replace parts before {version}
            prefix = tag_format.split("{version}")[0]
            if prefix:
                version = version.replace(prefix, "", 1)
            # Replace parts after {version}
            suffix = tag_format.split("{version}")[1] if len(tag_format.split("{version}")) > 1 else ""
            if suffix:
                version = version.replace(suffix, "", 1)
        else:
            version = tag_name
        
        # Get release date
        published_at = release.get("published_at", "")
        release_date = published_at.split("T")[0] if published_at else datetime.now().strftime("%Y-%m-%d")
        
        # Construct download URL
        sdk_repo = lang_config["sdk_repo"]
        download_url = f"https://github.com/CyberSource/{sdk_repo}/archive/refs/tags/{tag_name}.zip"
        
        return {
            "version": version,
            "release_date": release_date,
            "tag_name": tag_name,
            "download_url": download_url
        }
    
    def load_json_file(self, file_path: str, lang_name: str, lang_config: Dict) -> Optional[Dict]:
        """Load the current JSON file, return None if not found"""
        try:
            with open(file_path, 'r') as f:
                return json.load(f)
        except FileNotFoundError:
            print(f"File not found for {lang_name}: {os.path.basename(file_path)}")
            print(f"   Skipping {lang_name} SDK update")
            return None
        except json.JSONDecodeError as e:
            print(f"Error: Invalid JSON in {os.path.basename(file_path)}: {e}")
            return None
    
    def save_json_file(self, data: Dict, file_path: str):
        """Save updated data to JSON file"""
        with open(file_path, 'w') as f:
            json.dump(data, f, indent=2)
        print(f"Updated {os.path.basename(file_path)}")
    
    def update_json_data(self, current_data: Dict, new_release: Dict) -> Tuple[Dict, bool]:
        """Update JSON data with new release info"""
        current_version = current_data.get("latest_version", "")
        new_version = new_release["version"]
        
        # Check if this is actually a new version
        if new_version == current_version:
            print(f"No new release. Current version {current_version} is up to date.")
            return current_data, False
        
        print(f"New release found: {new_version} (current: {current_version})")
        
        # Always update latest_version and last_updated
        current_data["latest_version"] = new_version
        current_data["last_updated"] = datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
        
        # If add_to_versions_list is enabled, add to versions array
        if self.add_to_versions_list:
            print(f"Adding version {new_version} to versions list")
            if "versions" not in current_data:
                current_data["versions"] = []
            
            # Insert at the beginning of the array
            current_data["versions"].insert(0, new_release)
        else:
            print(f"Updated latest_version only (add_to_versions_list is disabled)")
        
        return current_data, True
    
    def create_isolated_workspace(self) -> str:
        """Create timestamped workspace for safe parallel execution"""
        timestamp = datetime.now(timezone.utc).strftime("%Y%m%d_%H%M%S")
        workspace = f"./workspace_sdk_update_{timestamp}"
        
        print(f"Creating isolated workspace: {workspace}")
        os.makedirs(workspace, exist_ok=True)
        print(f"Created workspace: {workspace}")
        
        return workspace
    
    def clone_repository(self, workspace: str) -> str:
        """Clone the central repository into workspace"""
        owner = "CyberSource"
        repo = self.sdk_support_repo
        base_branch = self.pr_base_branch
        
        repo_url = f"https://github.com/{owner}/{repo}.git"
        clone_dir = os.path.join(workspace, repo)
        
        print(f"Cloning repository: {repo_url}")
        print(f"   Branch: {base_branch}")
        
        try:
            subprocess.run(
                ["git", "clone", "--branch", base_branch, "--single-branch", repo_url, clone_dir],
                check=True,
                capture_output=True,
                text=True
            )
            print(f"Cloned repository to: {clone_dir}")
            return clone_dir
        except subprocess.CalledProcessError as e:
            print(f"Error cloning repository: {e.stderr}")
            raise
    
    def branch_exists_on_remote(self, repo_dir: str, branch_name: str) -> bool:
        """Check if branch already exists on remote"""
        try:
            result = subprocess.run(
                ["git", "ls-remote", "--heads", "origin", branch_name],
                cwd=repo_dir,
                capture_output=True,
                text=True,
                check=True
            )
            return branch_name in result.stdout
        except subprocess.CalledProcessError:
            return False
    
    def create_git_branch(self, repo_dir: str) -> Optional[str]:
        """Create a new git branch for the update"""
        # Generate timestamp for unique branch name (format: YYYYMMDD-HHMMSS in GMT)
        timestamp = datetime.now(timezone.utc).strftime("%Y%m%d-%H%M%S")
        branch_name = f"autogenerated-sdk-updates-{timestamp}"
        base_branch = self.pr_base_branch
        
        try:
            # Checkout base branch
            print(f"Checking out {base_branch} branch...")
            subprocess.run(
                ["git", "checkout", base_branch],
                cwd=repo_dir,
                check=True,
                capture_output=True,
                text=True
            )
            print(f"Checked out {base_branch} branch")
            
            # Pull latest changes
            print("Pulling latest changes...")
            subprocess.run(
                ["git", "pull"],
                cwd=repo_dir,
                check=True,
                capture_output=True
            )
            print("Pulled latest changes")
            
            # Create and checkout new branch
            print(f"Creating new branch: {branch_name}...")
            subprocess.run(
                ["git", "checkout", "-b", branch_name],
                cwd=repo_dir,
                check=True,
                capture_output=True
            )
            print(f"Created and checked out branch: {branch_name}")
            return branch_name
        except subprocess.CalledProcessError as e:
            print(f"Error creating git branch: {e}")
            if e.stderr:
                error_msg = e.stderr if isinstance(e.stderr, str) else e.stderr.decode('utf-8', errors='ignore')
                print(f"   Details: {error_msg}")
            return None
    
    def commit_and_push(self, repo_dir: str, branch_name: str, updated_files: List[str]):
        """Commit changes and push to remote"""
        try:
            # Add all updated JSON files
            print(f"Staging {len(updated_files)} file(s)...")
            for json_file in updated_files:
                subprocess.run(
                    ["git", "add", json_file],
                    cwd=repo_dir,
                    check=True,
                    capture_output=True
                )
                print(f"  Staged {json_file}")
            
            # Commit with descriptive message
            commit_message = "Update SDK versions"
            print(f"Committing changes: {commit_message}...")
            subprocess.run(
                ["git", "commit", "-m", commit_message],
                cwd=repo_dir,
                check=True,
                capture_output=True
            )
            print(f"Committed changes: {commit_message}")
            
            # Push to remote
            print(f"Pushing to origin/{branch_name}...")
            subprocess.run(
                ["git", "push", "-u", "origin", branch_name],
                cwd=repo_dir,
                check=True,
                capture_output=True,
                text=True
            )
            print(f"Pushed to origin/{branch_name}")
            
        except subprocess.CalledProcessError as e:
            print(f"Error committing/pushing changes: {e}")
            if e.stderr:
                error_msg = e.stderr if isinstance(e.stderr, str) else e.stderr.decode('utf-8', errors='ignore')
                print(f"   Details: {error_msg}")
            print()
            print("Possible solutions:")
            print("   1. Ensure you have push access to the repository")
            print("   2. Check if you need to configure Git credentials")
            print("   3. Verify your GitHub token has 'repo' permissions")
            raise
    
    def create_pull_request(self, branch_name: str, updated_languages: List[Tuple[str, str]]) -> Optional[str]:
        """Create a pull request via GitHub API"""
        if not self.github_token:
            print("No GitHub token found. Skipping PR creation.")
            print(f"  Please create PR manually for branch: {branch_name}")
            return None
        
        owner = "CyberSource"
        repo = self.sdk_support_repo
        target_branch = self.pr_target_branch
        
        url = f"https://api.github.com/repos/{owner}/{repo}/pulls"
        
        headers = {
            "Authorization": f"token {self.github_token}",
            "Accept": "application/vnd.github.v3+json"
        }
        
        # Build PR body with all updated languages
        updates_list = "\n".join([f"- {lang}: {version}" for lang, version in updated_languages])
        
        pr_data = {
            "title": "Update SDK versions",
            "body": f"Automated SDK version updates:\n\n{updates_list}\n\n"
                    f"- Updated `latest_version` fields\n"
                    f"- Updated `last_updated` timestamps\n"
                    + ("- Added versions to history lists\n" if self.add_to_versions_list else ""),
            "head": branch_name,
            "base": target_branch
        }
        
        try:
            # Disable SSL verification for corporate proxy #kept import here to be removed later
            import urllib3
            urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
            
            response = requests.post(url, headers=headers, json=pr_data, verify=False)
            response.raise_for_status()
            pr_url = response.json().get("html_url")
            print(f"Pull request created: {pr_url}")
            return pr_url
        except requests.RequestException as e:
            print(f"Error creating pull request: {e}")
            if hasattr(e, 'response') and e.response is not None:
                print(f"   Response: {e.response.text}")
            return None
    
    def cleanup_workspace(self, workspace: str):
        """Clean up temporary workspace"""
        if not os.path.exists(workspace):
            return
        
        try:
            shutil.rmtree(workspace)
            print(f"Cleaned up workspace: {workspace}")
        except Exception as e:
            print(f"Warning: Could not delete workspace: {e}")
    
    def display_summary(self, updated_languages: List[Tuple[str, str]], branch_name: Optional[str], pr_url: Optional[str]):
        """Display operation summary"""
        print()
        print("=" * 70)
        print("OPERATION SUMMARY")
        print("=" * 70)
        print(f"Languages Updated:  {len(updated_languages)}")
        for lang, version in updated_languages:
            print(f"   - {lang}: {version}")
        if branch_name:
            print(f"Branch Created:     {branch_name}")
        if pr_url:
            print(f"Pull Request:       {pr_url}")
        elif branch_name and not pr_url:
            print("Pull Request:       Not created (check errors above)")
        else:
            print("Pull Request:       Skipped (--enable-pr not specified)")
        
        print("=" * 70)
    
    def run(self, create_pr: bool = True, use_isolated_clone: bool = True):
        """Main execution flow for all enabled languages"""
        print("=" * 70)
        print("CyberSource SDK Version Updater (Multi-Language)")
        print("=" * 70)
        print(f"Mode: {'Add to list' if self.add_to_versions_list else 'Update latest only'}")
        print(f"Clone: {'Isolated workspace' if use_isolated_clone else 'Current directory'}")
        print(f"Target Repo: {self.sdk_support_repo}")
        print()
        
        # Validate configuration
        if not self.validate_config():
            sys.exit(1)
        print()
        
        # Get enabled languages
        enabled_languages = [(name, config) for name, config in self.languages.items() 
                           if config.get("enabled", False)]
        
        print(f"Checking {len(enabled_languages)} language(s) for updates...")
        print()
        
        # PHASE 1: Check which languages have updates available
        print("=" * 70)
        print("PHASE 1: Fetching latest releases from GitHub")
        print("=" * 70)
        print()
        
        languages_to_update = []
        
        for lang_name, lang_config in enabled_languages:
            try:
                print(f"Fetching {lang_name}...", end=" ")
                
                # Fetch latest release
                release = self.get_latest_release(lang_config)
                if not release:
                    print("FAILED")
                    continue
                
                # Parse release data
                new_release = self.parse_release_data(release, lang_config)
                github_version = new_release["version"]
                
                print(f"GitHub version: {github_version}")
                languages_to_update.append((lang_name, lang_config, new_release))
                    
            except Exception as e:
                print(f"ERROR: {e}")
        
        print()
        print("=" * 70)
        print(f"Fetched {len(languages_to_update)} release(s) from GitHub")
        print("=" * 70)
        
        # If no releases fetched, exit early
        if not languages_to_update:
            print()
            print("Failed to fetch any releases from GitHub.")
            return
        
        # PHASE 2: Process languages that have updates
        print()
        print("=" * 70)
        print(f"PHASE 2: Processing {len(languages_to_update)} language(s) with updates")
        print("=" * 70)
        print()
        
        # Track results for final summary
        results = {
            "processed": [],
            "skipped": [],
            "failed": []
        }
        
        # Track updated languages for single PR
        updated_languages = []
        updated_files = []
        
        # Setup workspace and clone central repo once if creating PR
        workspace = None
        repo_dir = None
        branch_name = None
        
        try:
            if create_pr and use_isolated_clone:
                # Create isolated workspace and clone central repository once
                print("Setting up central workspace...")
                workspace = self.create_isolated_workspace()
                repo_dir = self.clone_repository(workspace)
                print()
                
                # Create git branch once for all updates
                print("Creating git branch for updates...")
                branch_name = self.create_git_branch(repo_dir)
                if not branch_name:
                    print("Failed to create git branch. Aborting.")
                    if workspace:
                        self.cleanup_workspace(workspace)
                    sys.exit(1)
                print()
            elif create_pr and not use_isolated_clone:
                # Use current directory
                repo_dir = os.getcwd()
                print("Creating git branch for updates...")
                branch_name = self.create_git_branch(repo_dir)
                if not branch_name:
                    print("Failed to create git branch. Aborting.")
                    sys.exit(1)
                print()
            
            # Process each enabled language
            for lang_name, lang_config, new_release in languages_to_update:
                print("=" * 70)
                print(f"Processing: {lang_name.upper()}")
                print("=" * 70)
                
                try:
                    # We already have the release data from Phase 1
                    print(f"Processing update: {new_release['version']}")
                    print()
                    
                    json_file = lang_config["json_file"]
                    
                    # Determine JSON path
                    if repo_dir:
                        json_path = os.path.join(repo_dir, json_file)
                    else:
                        json_path = json_file
                    
                    # Load current JSON from the repository
                    print(f"Loading {json_file}...")
                    current_data = self.load_json_file(json_path, lang_name, lang_config)
                    
                    if current_data is None:
                        # File not found, skip this language
                        results["skipped"].append(f"{lang_name}: JSON file not found")
                        print()
                        continue
                    
                    # Update JSON data
                    updated_data, has_changes = self.update_json_data(current_data, new_release)
                    print()
                    
                    if not has_changes:
                        print(f"No updates needed for {lang_name}.")
                        results["skipped"].append(f"{lang_name}: Already up-to-date ({new_release['version']})")
                        print()
                        continue
                    
                    # Save updated JSON
                    print("Saving changes...")
                    self.save_json_file(updated_data, json_path)
                    print()
                    
                    # Track successful updates
                    updated_languages.append((lang_name, new_release["version"]))
                    updated_files.append(json_file)
                    results["processed"].append(f"{lang_name}: {new_release['version']}")
                    
                    print(f"{lang_name} updated successfully")
                    print()
                    
                except Exception as e:
                    print(f"Error processing {lang_name}: {e}")
                    results["failed"].append(f"{lang_name}: {str(e)}")
                    print()
            
            # Create single PR with all updates if any languages were updated
            pr_url = None
            if create_pr and branch_name and updated_languages:
                print()
                print("=" * 70)
                print("Committing and pushing all changes...")
                print("=" * 70)
                
                try:
                    self.commit_and_push(repo_dir, branch_name, updated_files)
                    print()
                    
                    print("Creating pull request...")
                    pr_url = self.create_pull_request(branch_name, updated_languages)
                    print()
                except Exception as e:
                    print(f"Error creating PR: {e}")
                    results["failed"].append(f"PR Creation: {str(e)}")
            
            # Display summary
            if updated_languages:
                self.display_summary(updated_languages, branch_name, pr_url)
            
        finally:
            # Cleanup workspace if created
            if workspace:
                print()
                self.cleanup_workspace(workspace)
                print()
        
        # Display final summary for all languages
        print()
        print("=" * 70)
        print("FINAL SUMMARY")
        print("=" * 70)
        
        if results["processed"]:
            print(f"\nSuccessfully Processed ({len(results['processed'])}):")
            for item in results["processed"]:
                print(f"   - {item}")
        
        if results["skipped"]:
            print(f"\nSkipped ({len(results['skipped'])}):")
            for item in results["skipped"]:
                print(f"   - {item}")
        
        if results["failed"]:
            print(f"\nFailed ({len(results['failed'])}):")
            for item in results["failed"]:
                print(f"   - {item}")
        
        print()
        print("=" * 70)
        
        # Final action summary
        if updated_languages and pr_url:
            print()
            print("=" * 70)
            print(f"{len(updated_languages)} language(s) updated successfully!")
            print()
            print(f"Pull Request: {pr_url}")
            print()
            print("Please review, approve and merge this PR.")
            print("=" * 70)
        elif updated_languages and branch_name and not pr_url:
            print()
            print("=" * 70)
            print(f"{len(updated_languages)} language(s) updated successfully!")
            print()
            print(f"Branch: {branch_name}")
            print()
            print("PR creation failed. Please create PR manually.")
            print("=" * 70)
        elif not updated_languages and not results["failed"]:
            print()
            print("All SDKs are up to date. No updates needed.")


def main():
    parser = argparse.ArgumentParser(
        description="Update CyberSource SDK versions for multiple languages",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  
  # Create single PR for all enabled languages with isolated clone
  python update-sdk-versions-multi.py --add-to-versions-list --enable-pr --token YOUR_TOKEN
  
  # Update without creating PR
  python update-sdk-versions-multi.py --add-to-versions-list
  
        """
    )
    parser.add_argument("--add-to-versions-list", action="store_true", 
                       help="Add new version to versions array (default: only update latest)")
    parser.add_argument("--enable-pr", action="store_true",
                       help="Enable creating git branch and pull request")
    parser.add_argument("--no-clone", action="store_true",
                       help="Don't create isolated clone, use current directory")
    parser.add_argument("--token", type=str, default=None,
                       help="GitHub Personal Access Token for PR creation")
    parser.add_argument("--config", default="config.json",
                       help="Path to config file (default: config.json)")
    
    args = parser.parse_args()
    
    # Create updater instance with optional token
    updater = SDKVersionUpdater(config_path=args.config, github_token=args.token)
    
    # Override add_to_versions_list if specified via command line
    if args.add_to_versions_list:
        updater.add_to_versions_list = True
    
    # Run the updater
    updater.run(
        create_pr=args.enable_pr,
        use_isolated_clone=not args.no_clone
    )


if __name__ == "__main__":
    main()
