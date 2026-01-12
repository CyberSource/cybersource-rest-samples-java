#!/usr/bin/env python3
"""
CyberSource SDK Version Updater (Multi-Language)
Fetches latest releases from GitHub and updates SDK version JSON files for all configured languages
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
        self.add_to_list = self.config.get("add_to_list", False)
        self.languages = self.config.get("languages", {})
        
    def load_config(self, config_path: str) -> Dict:
        """Load configuration from JSON file"""
        if os.path.exists(config_path):
            with open(config_path, 'r') as f:
                return json.load(f)
        return {}
    
    def validate_config(self) -> bool:
        """Validate configuration before execution"""
        if not self.languages:
            print("❌ Error: No languages configured in config.json")
            return False
        
        enabled_languages = [lang for lang, cfg in self.languages.items() if cfg.get("enabled", False)]
        if not enabled_languages:
            print("❌ Error: No languages are enabled in config.json")
            return False
        
        print(f"→ Validating {len(enabled_languages)} enabled language(s): {', '.join(enabled_languages)}")
        
        # Validate each enabled language has required fields
        for lang_name, lang_config in self.languages.items():
            if not lang_config.get("enabled", False):
                continue
            
            required_fields = ["sdk_repo", "samples_repo", "json_file", "tag_pattern"]
            missing = [f for f in required_fields if not lang_config.get(f)]
            if missing:
                print(f"❌ Error: Language '{lang_name}' missing required fields: {', '.join(missing)}")
                return False
        
        print(f"✓ Configuration validated for {len(enabled_languages)} language(s)")
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
        tag_pattern = lang_config.get("tag_pattern", "bare_version")
        
        # Extract version from tag based on pattern
        if tag_pattern == "full_prefix":
            # e.g., "cybersource-rest-client-java-0.0.84" -> "0.0.84"
            prefix = lang_config.get("tag_prefix", "")
            version = tag_name.replace(prefix, "")
        elif tag_pattern == "v_prefix":
            # e.g., "v0.0.80" -> "0.0.80"
            version = tag_name.lstrip("v")
        else:  # bare_version
            # e.g., "0.0.72" -> "0.0.72"
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
            print(f"⚠️  File not found for {lang_name}: {os.path.basename(file_path)}")
            print(f"   Skipping {lang_name} SDK update")
            return None
        except json.JSONDecodeError as e:
            print(f"❌ Error: Invalid JSON in {os.path.basename(file_path)}: {e}")
            return None
    
    def save_json_file(self, data: Dict, file_path: str):
        """Save updated data to JSON file"""
        with open(file_path, 'w') as f:
            json.dump(data, f, indent=2)
        print(f"✓ Updated {os.path.basename(file_path)}")
    
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
        
        # If add_to_list is enabled, add to versions array
        if self.add_to_list:
            print(f"✓ Adding version {new_version} to versions list")
            if "versions" not in current_data:
                current_data["versions"] = []
            
            # Insert at the beginning of the array
            current_data["versions"].insert(0, new_release)
        else:
            print(f"✓ Updated latest_version only (add_to_list is disabled)")
        
        return current_data, True
    
    def create_isolated_workspace(self, version: str) -> str:
        """Create timestamped workspace for safe parallel execution"""
        timestamp = datetime.now(timezone.utc).strftime("%Y%m%d_%H%M%S")
        workspace = f"./workspace_sdk_update_{version}_{timestamp}"
        
        print(f"→ Creating isolated workspace: {workspace}")
        os.makedirs(workspace, exist_ok=True)
        print(f"✓ Created workspace: {workspace}")
        
        return workspace
    
    def clone_repository(self, workspace: str, lang_config: Dict) -> str:
        """Clone the target repository into workspace"""
        owner = "CyberSource"
        repo = lang_config["samples_repo"]
        base_branch = lang_config.get("pr_base_branch", "master")
        
        repo_url = f"https://github.com/{owner}/{repo}.git"
        clone_dir = os.path.join(workspace, repo)
        
        print(f"→ Cloning repository: {repo_url}")
        print(f"   Branch: {base_branch}")
        
        try:
            subprocess.run(
                ["git", "clone", "--branch", base_branch, "--single-branch", repo_url, clone_dir],
                check=True,
                capture_output=True,
                text=True
            )
            print(f"✓ Cloned repository to: {clone_dir}")
            return clone_dir
        except subprocess.CalledProcessError as e:
            print(f"❌ Error cloning repository: {e.stderr}")
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
    
    def create_git_branch(self, repo_dir: str, version: str, lang_name: str, lang_config: Dict) -> Optional[str]:
        """Create a new git branch for the update"""
        # Generate timestamp for unique branch name (format: YYYYMMDD-HHMMSS in GMT)
        timestamp = datetime.now(timezone.utc).strftime("%Y%m%d-%H%M%S")
        branch_name = f"autogenerated-{lang_name}-{version}-update-{timestamp}"
        base_branch = lang_config.get("pr_base_branch", "master")
        
        try:
            # Checkout base branch
            print(f"→ Checking out {base_branch} branch...")
            subprocess.run(
                ["git", "checkout", base_branch],
                cwd=repo_dir,
                check=True,
                capture_output=True,
                text=True
            )
            print(f"✓ Checked out {base_branch} branch")
            
            # Pull latest changes
            print("→ Pulling latest changes...")
            subprocess.run(
                ["git", "pull"],
                cwd=repo_dir,
                check=True,
                capture_output=True
            )
            print("✓ Pulled latest changes")
            
            # Create and checkout new branch
            print(f"→ Creating new branch: {branch_name}...")
            subprocess.run(
                ["git", "checkout", "-b", branch_name],
                cwd=repo_dir,
                check=True,
                capture_output=True
            )
            print(f"✓ Created and checked out branch: {branch_name}")
            return branch_name
        except subprocess.CalledProcessError as e:
            print(f"❌ Error creating git branch: {e}")
            if e.stderr:
                error_msg = e.stderr if isinstance(e.stderr, str) else e.stderr.decode('utf-8', errors='ignore')
                print(f"   Details: {error_msg}")
            return None
    
    def commit_and_push(self, repo_dir: str, version: str, branch_name: str, lang_name: str, json_file: str):
        """Commit changes and push to remote"""
        try:
            # Add the JSON file
            print(f"→ Staging {json_file}...")
            subprocess.run(
                ["git", "add", json_file],
                cwd=repo_dir,
                check=True,
                capture_output=True
            )
            print(f"✓ Staged {json_file}")
            
            # Commit with descriptive message
            lang_display = lang_name.upper() if lang_name in ["php", "node"] else lang_name.capitalize()
            commit_message = f"Update {lang_display} SDK to version {version}"
            print(f"→ Committing changes: {commit_message}...")
            subprocess.run(
                ["git", "commit", "-m", commit_message],
                cwd=repo_dir,
                check=True,
                capture_output=True
            )
            print(f"✓ Committed changes: {commit_message}")
            
            # Push to remote
            print(f"→ Pushing to origin/{branch_name}...")
            subprocess.run(
                ["git", "push", "-u", "origin", branch_name],
                cwd=repo_dir,
                check=True,
                capture_output=True,
                text=True
            )
            print(f"✓ Pushed to origin/{branch_name}")
            
        except subprocess.CalledProcessError as e:
            print(f"❌ Error committing/pushing changes: {e}")
            if e.stderr:
                error_msg = e.stderr if isinstance(e.stderr, str) else e.stderr.decode('utf-8', errors='ignore')
                print(f"   Details: {error_msg}")
            print()
            print("💡 Possible solutions:")
            print("   1. Ensure you have push access to the repository")
            print("   2. Check if you need to configure Git credentials")
            print("   3. Verify your GitHub token has 'repo' permissions")
            raise
    
    def create_pull_request(self, version: str, branch_name: str, lang_name: str, lang_config: Dict) -> Optional[str]:
        """Create a pull request via GitHub API"""
        if not self.github_token:
            print("⚠ No GitHub token found. Skipping PR creation.")
            print(f"  Please create PR manually for branch: {branch_name}")
            return None
        
        owner = "CyberSource"
        repo = lang_config["samples_repo"]
        target_branch = lang_config.get("pr_target_branch", "master")
        
        url = f"https://api.github.com/repos/{owner}/{repo}/pulls"
        
        headers = {
            "Authorization": f"token {self.github_token}",
            "Accept": "application/vnd.github.v3+json"
        }
        
        lang_display = lang_name.upper() if lang_name in ["php", "node"] else lang_name.capitalize()
        pr_data = {
            "title": f"Update {lang_display} SDK to version {version}",
            "body": f"Automated update: CyberSource {lang_display} REST Client SDK to version {version}\n\n"
                    f"- Updated `latest_version` to {version}\n"
                    f"- Updated `last_updated` timestamp\n"
                    + (f"- Added version {version} to versions list\n" if self.add_to_list else ""),
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
            print(f"✓ Pull request created: {pr_url}")
            return pr_url
        except requests.RequestException as e:
            print(f"❌ Error creating pull request: {e}")
            if hasattr(e, 'response') and e.response is not None:
                print(f"   Response: {e.response.text}")
            return None
    
    def cleanup_workspace(self, workspace: str, auto_cleanup: bool = False):
        """Clean up temporary workspace"""
        if not os.path.exists(workspace):
            return
        
        if not auto_cleanup:
            print()
            response = input(f"Delete workspace '{workspace}'? (y/n): ").strip().lower()
            if response != 'y':
                print(f"ℹ Workspace preserved at: {os.path.abspath(workspace)}")
                return
        
        try:
            shutil.rmtree(workspace)
            print(f"✓ Cleaned up workspace: {workspace}")
        except Exception as e:
            print(f"⚠ Warning: Could not delete workspace: {e}")
    
    def display_summary(self, lang_name: str, version: str, branch_name: Optional[str], pr_url: Optional[str], json_file: str):
        """Display operation summary"""
        print()
        print("=" * 70)
        print(f"📋 {lang_name.upper()} OPERATION SUMMARY")
        print("=" * 70)
        print(f"✅ SDK Version:        {version}")
        print(f"✅ JSON Updated:       {json_file}")
        if branch_name:
            print(f"✅ Branch Created:     {branch_name}")
        if pr_url:
            print(f"✅ Pull Request:       {pr_url}")
        elif branch_name and not pr_url:
            print("⚠️  Pull Request:       Not created (check errors above)")
        else:
            print("ℹ️  Pull Request:       Skipped (--enable-pr not specified)")
        
        print("=" * 70)
    
    def run(self, create_pr: bool = True, use_isolated_clone: bool = True, auto_cleanup: bool = False):
        """Main execution flow for all enabled languages"""
        print("=" * 70)
        print("🚀 CyberSource SDK Version Updater (Multi-Language)")
        print("=" * 70)
        print(f"Mode: {'Add to list' if self.add_to_list else 'Update latest only'}")
        print(f"Clone: {'Isolated workspace' if use_isolated_clone else 'Current directory'}")
        print()
        
        # Validate configuration
        if not self.validate_config():
            sys.exit(1)
        print()
        
        # Get enabled languages
        enabled_languages = [(name, config) for name, config in self.languages.items() 
                           if config.get("enabled", False)]
        
        print(f"Processing {len(enabled_languages)} language(s)")
        print()
        
        # Track results for final summary
        results = {
            "processed": [],
            "skipped": [],
            "failed": []
        }
        
        # Process each enabled language
        for lang_name, lang_config in enabled_languages:
            print("=" * 70)
            print(f"🔹 Processing: {lang_name.upper()}")
            print("=" * 70)
            
            try:
                # Fetch latest release
                print(f"📡 Fetching latest release for {lang_name}...")
                release = self.get_latest_release(lang_config)
                if not release:
                    print(f"❌ Failed to fetch release data for {lang_name}")
                    results["failed"].append(f"{lang_name}: Failed to fetch release")
                    print()
                    continue
                
                # Parse release data
                new_release = self.parse_release_data(release, lang_config)
                print(f"✓ Latest GitHub release: {new_release['version']}")
                print()
                
                repo_dir = None
                branch_name = None
                pr_url = None
                workspace = None
                json_file = lang_config["json_file"]
                
                try:
                    if use_isolated_clone:
                        # Create isolated workspace and clone repository
                        print("🔧 Setting up isolated workspace...")
                        workspace = self.create_isolated_workspace(f"{lang_name}-{new_release['version']}")
                        repo_dir = self.clone_repository(workspace, lang_config)
                        json_path = os.path.join(repo_dir, json_file)
                        print()
                    else:
                        # Use current directory
                        repo_dir = os.getcwd()
                        json_path = json_file
                    
                    # Load current JSON from the repository
                    print(f"📄 Loading {json_file}...")
                    current_data = self.load_json_file(json_path, lang_name, lang_config)
                    
                    if current_data is None:
                        # File not found, skip this language
                        results["skipped"].append(f"{lang_name}: JSON file not found")
                        if workspace:
                            self.cleanup_workspace(workspace, auto_cleanup=True)
                        print()
                        continue
                    
                    # Update JSON data
                    updated_data, has_changes = self.update_json_data(current_data, new_release)
                    print()
                    
                    if not has_changes:
                        print(f"ℹ️  No updates needed for {lang_name}.")
                        results["skipped"].append(f"{lang_name}: Already up-to-date ({new_release['version']})")
                        if workspace:
                            self.cleanup_workspace(workspace, auto_cleanup=True)
                        print()
                        continue
                    
                    # Git operations (if enabled)
                    if create_pr:
                        print("🌿 Creating git branch and preparing PR...")
                        branch_name = self.create_git_branch(repo_dir, new_release["version"], lang_name, lang_config)
                        
                        if not branch_name:
                            print()
                            print(f"⚠️  Warning: Could not create git branch for {lang_name}. Skipping.")
                            results["failed"].append(f"{lang_name}: Failed to create branch")
                            if workspace:
                                self.cleanup_workspace(workspace, auto_cleanup=True)
                            print()
                            continue
                    
                    # Save updated JSON
                    print("💾 Saving changes...")
                    self.save_json_file(updated_data, json_path)
                    print()
                    
                    # Commit and push if branch was created successfully
                    if create_pr and branch_name:
                        print("📤 Committing and pushing changes...")
                        self.commit_and_push(repo_dir, new_release["version"], branch_name, lang_name, json_file)
                        print()
                        
                        print("🔀 Creating pull request...")
                        pr_url = self.create_pull_request(new_release["version"], branch_name, lang_name, lang_config)
                        print()
                    
                    # Display summary for this language
                    self.display_summary(lang_name, new_release["version"], branch_name, pr_url, json_file)
                    
                    results["processed"].append(f"{lang_name}: {new_release['version']}")
                    
                except Exception as e:
                    print(f"❌ Error processing {lang_name}: {e}")
                    results["failed"].append(f"{lang_name}: {str(e)}")
                
                finally:
                    # Cleanup workspace if created
                    if workspace:
                        self.cleanup_workspace(workspace, auto_cleanup=auto_cleanup)
                
                print()
            
            except Exception as e:
                print(f"❌ Unexpected error for {lang_name}: {e}")
                results["failed"].append(f"{lang_name}: {str(e)}")
                print()
        
        # Display final summary for all languages
        print()
        print("=" * 70)
        print("📊 FINAL SUMMARY - ALL LANGUAGES")
        print("=" * 70)
        
        if results["processed"]:
            print(f"\n✅ Successfully Processed ({len(results['processed'])}):")
            for item in results["processed"]:
                print(f"   • {item}")
        
        if results["skipped"]:
            print(f"\n⏭️  Skipped ({len(results['skipped'])}):")
            for item in results["skipped"]:
                print(f"   • {item}")
        
        if results["failed"]:
            print(f"\n❌ Failed ({len(results['failed'])}):")
            for item in results["failed"]:
                print(f"   • {item}")
        
        print()
        print("=" * 70)


def main():
    parser = argparse.ArgumentParser(
        description="Update CyberSource SDK versions for multiple languages",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  
  # Create PRs for all enabled languages with isolated clones
  python update-sdk-versions.py --add-to-list --enable-pr --token YOUR_TOKEN
  
        """
    )
    parser.add_argument("--add-to-list", action="store_true", 
                       help="Add new version to versions array (default: only update latest)")
    parser.add_argument("--enable-pr", action="store_true",
                       help="Enable creating git branch and pull request")
    parser.add_argument("--no-clone", action="store_true",
                       help="Don't create isolated clone, use current directory")
    parser.add_argument("--auto-cleanup", action="store_true",
                       help="Automatically cleanup workspace without prompting")
    parser.add_argument("--token", type=str, default=None,
                       help="GitHub Personal Access Token for PR creation")
    parser.add_argument("--config", default="config.json",
                       help="Path to config file (default: config.json)")
    
    args = parser.parse_args()
    
    # Create updater instance with optional token
    updater = SDKVersionUpdater(config_path=args.config, github_token=args.token)
    
    # Override add_to_list if specified via command line
    if args.add_to_list:
        updater.add_to_list = True
    
    # Run the updater
    updater.run(
        create_pr=args.enable_pr,
        use_isolated_clone=not args.no_clone,
        auto_cleanup=args.auto_cleanup
    )


if __name__ == "__main__":
    main()
