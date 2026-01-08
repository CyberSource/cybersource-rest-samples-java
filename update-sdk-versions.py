#!/usr/bin/env python3
"""
CyberSource SDK Version Updater
Fetches latest releases from GitHub and updates java-sdk-versions.json
"""

import json
import os
import sys
import argparse
from datetime import datetime, timezone
from typing import Dict, Optional
import requests
import subprocess


class SDKVersionUpdater:
    def __init__(self, config_path: str = "config.json", github_token: Optional[str] = None):
        """Initialize the updater with configuration"""
        self.config = self.load_config(config_path)
        # Priority: CLI argument > environment variable > config file
        self.github_token = github_token or os.environ.get("GITHUB_TOKEN") or self.config.get("github_token", "")
        self.json_file = self.config.get("json_file", "java-sdk-versions.json")
        self.add_to_list = self.config.get("add_to_list", False)
        
    def load_config(self, config_path: str) -> Dict:
        """Load configuration from JSON file"""
        if os.path.exists(config_path):
            with open(config_path, 'r') as f:
                return json.load(f)
        return {}
    
    def get_latest_release(self) -> Optional[Dict]:
        """Fetch the latest release from GitHub API"""
        owner = "CyberSource"
        repo = "cybersource-rest-client-java"
        url = f"https://api.github.com/repos/{owner}/{repo}/releases/latest"
        
        headers = {}
        if self.github_token:
            headers["Authorization"] = f"token {self.github_token}"
        
        try:
            # Disable SSL verification for corporate proxy
            import urllib3
            urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
            
            response = requests.get(url, headers=headers, verify=False)
            #disabled ssl verify above
            response.raise_for_status()
            return response.json()
        except requests.RequestException as e:
            print(f"Error fetching release from GitHub: {e}")
            return None
    
    def parse_release_data(self, release: Dict) -> Dict:
        """Parse GitHub release data into our format"""
        tag_name = release.get("tag_name", "")
        
        # Extract version from tag (e.g., "cybersource-rest-client-java-0.0.84" -> "0.0.84")
        version = tag_name.replace("cybersource-rest-client-java-", "")
        
        # Get release date
        published_at = release.get("published_at", "")
        release_date = published_at.split("T")[0] if published_at else datetime.now().strftime("%Y-%m-%d")
        
        # Construct download URL
        download_url = f"https://github.com/CyberSource/cybersource-rest-client-java/archive/refs/tags/{tag_name}.zip"
        
        return {
            "version": version,
            "release_date": release_date,
            "tag_name": tag_name,
            "download_url": download_url
        }
    
    def load_json_file(self) -> Dict:
        """Load the current JSON file"""
        try:
            with open(self.json_file, 'r') as f:
                return json.load(f)
        except FileNotFoundError:
            print(f"Error: {self.json_file} not found")
            sys.exit(1)
    
    def save_json_file(self, data: Dict):
        """Save updated data to JSON file"""
        with open(self.json_file, 'w') as f:
            json.dump(data, f, indent=2)
        print(f"✓ Updated {self.json_file}")
    
    def update_json_data(self, current_data: Dict, new_release: Dict) -> tuple[Dict, bool]:
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
    
    def create_git_branch(self, version: str) -> str:
        """Create a new git branch for the update"""
        branch_name = f"update3-java-sdk-v{version}"
        
        try:
            # Checkout master branch
            print("→ Checking out master branch...")
            subprocess.run(["git", "checkout", "master"], check=True, capture_output=True, text=True)
            print("✓ Checked out master branch")
            
            # Pull latest changes
            print("→ Pulling latest changes...")
            subprocess.run(["git", "pull"], check=True, capture_output=True)
            print("✓ Pulled latest changes")
            
            # Create and checkout new branch
            print(f"→ Creating new branch: {branch_name}...")
            subprocess.run(["git", "checkout", "-b", branch_name], check=True, capture_output=True)
            print(f"✓ Created and checked out branch: {branch_name}")
            return branch_name
        except subprocess.CalledProcessError as e:
            # Check if error is due to uncommitted changes
            if e.returncode == 1 and e.stderr:
                error_msg = e.stderr if isinstance(e.stderr, str) else e.stderr.decode('utf-8', errors='ignore')
                if 'would be overwritten' in error_msg or 'pathspec' not in error_msg:
                    print("❌ Error: You have uncommitted changes on your current branch.")
                    print("   Git cannot switch to master with uncommitted changes.")
                    print()
                    print("   💡 Solution: Stash your changes first:")
                    print("      git stash")
                    print(f"      python update_sdk_versions.py --add-to-list --enable-pr --token \"YOUR_TOKEN\"")
                    print("      git stash pop")
                    print()
                    return None
            
            print(f"Error creating git branch: {e}")
            return None
    
    def commit_and_push(self, version: str, branch_name: str):
        """Commit changes and push to remote"""
        try:
            # Add the JSON file
            print(f"→ Staging {self.json_file}...")
            subprocess.run(["git", "add", self.json_file], check=True, capture_output=True)
            print(f"✓ Staged {self.json_file}")
            
            # Commit with descriptive message
            commit_message = f"Update Java SDK to version {version}"
            print(f"→ Committing changes: {commit_message}...")
            subprocess.run(["git", "commit", "-m", commit_message], check=True, capture_output=True)
            print(f"✓ Committed changes: {commit_message}")
            
            # Push to remote
            print(f"→ Pushing to origin/{branch_name}...")
            subprocess.run(["git", "push", "-u", "origin", branch_name], check=True, capture_output=True)
            print(f"✓ Pushed to origin/{branch_name}")
            
        except subprocess.CalledProcessError as e:
            print(f"Error committing/pushing changes: {e}")
            raise
    
    def create_pull_request(self, version: str, branch_name: str):
        """Create a pull request via GitHub API"""
        if not self.github_token:
            print("⚠ No GitHub token found. Skipping PR creation.")
            print(f"  Please create PR manually for branch: {branch_name}")
            return
        
        owner = self.config.get("pr_repo_owner", "CyberSource")
        repo = self.config.get("pr_repo_name", "cybersource-rest-samples-java")
        base_branch = self.config.get("pr_base_branch", "master")
        
        url = f"https://api.github.com/repos/{owner}/{repo}/pulls"
        
        headers = {
            "Authorization": f"token {self.github_token}",
            "Accept": "application/vnd.github.v3+json"
        }
        
        pr_data = {
            "title": f"Update Java SDK to version {version}",
            "body": f"Automated update: CyberSource Java REST Client SDK to version {version}\n\n"
                    f"- Updated `latest_version` to {version}\n"
                    f"- Updated `last_updated` timestamp\n"
                    + (f"- Added version {version} to versions list\n" if self.add_to_list else ""),
            "head": branch_name,
            "base": base_branch
        }
        
        try:
            # Disable SSL verification for corporate proxy
            import urllib3
            urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
            
            response = requests.post(url, headers=headers, json=pr_data, verify=False)
            #passed ssl false above
            response.raise_for_status()
            pr_url = response.json().get("html_url")
            print(f"✓ Pull request created: {pr_url}")
        except requests.RequestException as e:
            print(f"Error creating pull request: {e}")
            if hasattr(e, 'response') and e.response is not None:
                print(f"  Response: {e.response.text}")
    
    def run(self, create_pr: bool = True):
        """Main execution flow"""
        print("=" * 60)
        print("CyberSource SDK Version Updater")
        print("=" * 60)
        print(f"Mode: {'Add to list' if self.add_to_list else 'Update latest only'}")
        print()
        
        # Fetch latest release
        print("Fetching latest release from GitHub...")
        release = self.get_latest_release()
        if not release:
            print("Failed to fetch release data")
            sys.exit(1)
        
        # Parse release data
        new_release = self.parse_release_data(release)
        print(f"Latest GitHub release: {new_release['version']}")
        print()
        
        # Load current JSON
        current_data = self.load_json_file()
        
        # Update JSON data
        updated_data, has_changes = self.update_json_data(current_data, new_release)
        
        if not has_changes:
            print("\nNo updates needed.")
            sys.exit(0)
        
        # Git operations (if enabled) - must be done BEFORE saving the file
        branch_name = None
        if create_pr:
            print("Creating git branch and PR...")
            branch_name = self.create_git_branch(new_release["version"])
            
            if not branch_name:
                print("\n⚠ Warning: Could not create git branch. Saving changes locally only.")
                print()
        
        # Save updated JSON (after branch creation but before commit)
        self.save_json_file(updated_data)
        print()
        
        # Commit and push if branch was created successfully
        if create_pr and branch_name:
            self.commit_and_push(new_release["version"], branch_name)
            self.create_pull_request(new_release["version"], branch_name)
        
        print()
        print("=" * 60)
        print("✓ Update completed successfully!")
        print("=" * 60)


def main():
    parser = argparse.ArgumentParser(description="Update CyberSource SDK versions")
    parser.add_argument("--add-to-list", action="store_true", 
                       help="Add new version to versions array (default: only update latest)")
    parser.add_argument("--enable-pr", action="store_true",
                       help="Enable creating git branch and pull request")
    parser.add_argument("--token", type=str, default=None,
                       help="GitHub Personal Access Token (fine-grained or classic) for PR creation")
    parser.add_argument("--config", default="config.json",
                       help="Path to config file (default: config.json)")
    
    args = parser.parse_args()
    
    # Create updater instance with optional token
    updater = SDKVersionUpdater(config_path=args.config, github_token=args.token)
    
    # Override add_to_list if specified via command line
    if args.add_to_list:
        updater.add_to_list = True
    
    # Run the updater (only create PR if --enable-pr is set)
    updater.run(create_pr=args.enable_pr)


if __name__ == "__main__":
    main()
