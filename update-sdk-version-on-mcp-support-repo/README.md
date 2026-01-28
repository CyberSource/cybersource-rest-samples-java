# CyberSource SDK Version Updater (Multi-Language)

An automation tool that manages SDK version updates across multiple programming languages for CyberSource SDKs. This script fetches the latest releases from GitHub, updates version files, and creates pull requests automatically.

## Purpose

This script automates the process of keeping CyberSource SDK version information up-to-date across multiple languages. It:

- **Eliminates manual version tracking** - Automatically fetches latest releases from GitHub
- **Reduces human error** - Standardizes the update process across all SDKs
- **Saves time** - Updates multiple SDK versions in a single operation
- **Maintains version history** - Optionally tracks version changes over time
- **Automates Git workflow** - Creates branches, commits, and pull requests automatically

## Quick Start

### How to Use the Script

1. **Install dependencies**:
```bash
pip install requests
```

2. **Run the script**:
```bash
python update-sdk-versions-multi.py --enable-pr --token YOUR_GITHUB_TOKEN
```

This will:
- Fetch latest SDK versions from GitHub for all enabled languages
- Update version JSON files
- Create a new branch with timestamped name
- Commit changes and push to remote
- Create a pull request automatically

**Note**: Replace `YOUR_GITHUB_TOKEN` with your CyberSource Public Repo GitHub Personal Access Fine Grained Token. See [Configuration](#github-token-setup) section for details.

## Features

### Multi-Language Support
Supports 7 programming languages:
- Java
- Python
- PHP
- Node.js
- Ruby
- .NET
- .NET Standard

### Two Operating Modes

1. **Update Latest Only** (Default)
   - Updates `latest_version` field
   - Updates `last_updated` timestamp
   - Best for tracking only current versions

2. **Add to History Mode** (`--add-to-versions-list`)
   - All features from "Update Latest Only"
   - Adds version to `versions` array
   - Maintains complete version history

### Git Automation
- Creates timestamped branches
- Commits all changes
- Pushes to remote repository
- Creates pull requests via GitHub API

### Flexible Configuration
- Enable/disable specific languages
- Configure tag format per language
- Set custom repository and branch settings
- Support for GitHub tokens

## Prerequisites

### Required
- Python 3.6 or higher
- Git installed and configured
- Internet connection (for GitHub API access)

### Optional
- GitHub Personal Access Token (for PR creation)
- Write access to the target repository

### Python Dependencies
```bash
pip install requests
```

## Installation

1. Clone or download this repository:
```bash
git clone <repository-url>
cd <repository-directory>
```

2. Install required Python packages:
```bash
pip install requests
```

3. Configure your settings in `config.json` (see Configuration section)

## Configuration

### config.json Structure

```json
{
    "github_token": "",
    "add_to_versions_list": false,
    "sdk_support_repo": "cybersource-mcp-sdk-support-files",
    "pr_base_branch": "test-all-sdk",
    "pr_target_branch": "test-all-sdk",
    "languages": {
        "java": {
            "enabled": true,
            "sdk_repo": "cybersource-rest-client-java",
            "json_file": "java-sdk-versions.json",
            "tag_format": "cybersource-rest-client-java-{version}"
        }
        // ... other languages
    }
}
```

### Configuration Options

#### Global Settings
- **`github_token`**: GitHub Personal Access Token (optional, can be passed via CLI or environment variable)
- **`add_to_versions_list`**: Default mode for version history (true/false)
- **`sdk_support_repo`**: Repository containing version JSON files
- **`pr_base_branch`**: Branch to create PR from
- **`pr_target_branch`**: Branch to merge PR into

#### Language Settings
Each language configuration includes:
- **`enabled`**: Whether to process this language (true/false)
- **`sdk_repo`**: GitHub repository name for this SDK
- **`json_file`**: Path to version JSON file in support repo
- **`tag_format`**: GitHub tag format (use `{version}` as placeholder)

### GitHub Token Setup

1. Generate a Personal Access Token:
   - Go to GitHub → Settings → Developer settings → Personal access tokens
   - Generate new token with `repo` scope

2. Provide token via (priority order):
   - Command line: `--token YOUR_TOKEN`
   - Environment variable: `export GITHUB_TOKEN=YOUR_TOKEN`
   - Config file: Set `"github_token": "YOUR_TOKEN"`

## Usage

### Basic Usage

#### Update Latest Versions Only
```bash
python update-sdk-versions-multi.py --enable-pr --token YOUR_GITHUB_TOKEN
```

#### Update with Version History
```bash
python update-sdk-versions-multi.py --add-to-versions-list --enable-pr --token YOUR_GITHUB_TOKEN
```

#### Dry Run (No PR Creation)
```bash
python update-sdk-versions-multi.py --add-to-versions-list
```

### Command-Line Options

| Option | Description | Default |
|--------|-------------|---------|
| `--add-to-versions-list` | Add versions to history array | False (from config) |
| `--enable-pr` | Create git branch and pull request | False |
| `--no-clone` | Use current directory instead of isolated clone | False (uses isolated clone) |
| `--token TOKEN` | GitHub Personal Access Token | None (checks env/config) |
| `--config FILE` | Path to configuration file | config.json |

### Examples

#### Example 1: Update All Enabled Languages with PR
```bash
python update-sdk-versions-multi.py \
    --add-to-versions-list \
    --enable-pr \
    --token ghp_xxxxxxxxxxxx
```

#### Example 2: Update Without Creating PR
```bash
python update-sdk-versions-multi.py --add-to-versions-list
```

#### Example 3: Use Environment Variable for Token
```bash
export GITHUB_TOKEN=ghp_xxxxxxxxxxxx
python update-sdk-versions-multi.py --enable-pr
```

#### Example 4: Use Current Directory (No Isolated Clone)
```bash
python update-sdk-versions-multi.py \
    --enable-pr \
    --no-clone \
    --token ghp_xxxxxxxxxxxx
```

## How It Works

### Workflow Overview

```
1. CONFIGURATION VALIDATION
   ├─ Load config.json
   ├─ Validate enabled languages
   └─ Check required fields

2. PHASE 1: FETCH LATEST RELEASES
   ├─ Query GitHub API for each enabled language
   ├─ Parse release information (version, date, download URL)
   └─ Collect languages with available updates

3. PHASE 2: PROCESS UPDATES
   ├─ Create isolated workspace (if --enable-pr)
   ├─ Clone central repository
   ├─ Create timestamped branch
   ├─ For each language:
   │  ├─ Load current version JSON
   │  ├─ Compare with GitHub version
   │  ├─ Update JSON if newer version found
   │  └─ Save changes
   └─ Track all updates

4. GIT AUTOMATION (if --enable-pr)
   ├─ Stage all updated JSON files
   ├─ Commit with descriptive message
   ├─ Push to remote repository
   └─ Create pull request via GitHub API

5. CLEANUP
   ├─ Remove isolated workspace
   └─ Display operation summary
```

### Branch Naming Convention
Branches are created with timestamp: `autogenerated-sdk-updates-YYYYMMDD-HHMMSS`

Example: `autogenerated-sdk-updates-20260128-091530`

### JSON File Updates

#### Update Latest Only Mode
```json
{
  "latest_version": "0.0.82",
  "last_updated": "2026-01-28T09:15:30Z",
  "versions": [...]  // Not modified
}
```

#### Add to History Mode
```json
{
  "latest_version": "0.0.82",
  "last_updated": "2026-01-28T09:15:30Z",
  "versions": [
    {
      "version": "0.0.82",
      "release_date": "2026-01-28",
      "tag_name": "cybersource-rest-client-java-0.0.82",
      "download_url": "https://github.com/CyberSource/cybersource-rest-client-java/archive/refs/tags/cybersource-rest-client-java-0.0.82.zip"
    },
    // ... previous versions
  ]
}
```

## Output Examples

### Successful Execution
```
======================================================================
CyberSource SDK Version Updater (Multi-Language)
======================================================================
Mode: Add to list
Clone: Isolated workspace
Target Repo: cybersource-mcp-sdk-support-files

Checking 7 language(s) for updates...

======================================================================
PHASE 1: Fetching latest releases from GitHub
======================================================================

Fetching java... GitHub version: 0.0.82
Fetching python... GitHub version: 0.0.65
Fetching php... GitHub version: 0.0.32
...

======================================================================
Processing: JAVA
======================================================================
New release found: 0.0.82 (current: 0.0.81)
Adding version 0.0.82 to versions list
Updated java-sdk-versions.json

======================================================================
OPERATION SUMMARY
======================================================================
Languages Updated:  3
   - java: 0.0.82
   - python: 0.0.65
   - ruby: 0.0.24
Branch Created:     autogenerated-sdk-updates-20260128-091530
Pull Request:       https://github.com/CyberSource/cybersource-mcp-sdk-support-files/pull/123
======================================================================
```

### No Updates Available
```
All SDKs are up to date. No updates needed.
```

## Troubleshooting

### Common Issues

#### 1. GitHub API Rate Limit Exceeded
**Problem**: Too many API requests without authentication

**Solution**: Use a GitHub token
```bash
python update-sdk-versions-multi.py --token YOUR_TOKEN
```

#### 2. JSON File Not Found
**Problem**: Script can't find version JSON file

**Solution**: 
- Verify `json_file` path in config.json
- Ensure repository is cloned correctly
- Check file exists in the target repository

#### 3. Git Push Failed
**Problem**: No push access to repository

**Solutions**:
- Verify GitHub token has `repo` permissions
- Check if you have write access to the repository
- Configure Git credentials properly

#### 4. SSL Certificate Error (Corporate Proxy)
**Problem**: SSL verification fails behind corporate proxy

**Note**: Script includes SSL verification bypass (line 88, 258)
```python
verify=False  # Bypasses SSL verification
```

#### 5. Branch Already Exists
**Problem**: Branch with same timestamp already exists

**Solution**: Wait a minute and retry (timestamps are down to the second)

#### 6. Workspace Cleanup Error on Windows
**Problem**: Warning about unable to delete workspace (Windows readonly files)

**Solution**: Fixed in latest version - script now automatically handles Windows file permissions
- The script uses force delete with permission correction for Git readonly files
- If automatic cleanup still fails, the script will display the workspace path for manual deletion
- This is a Windows-specific issue where Git creates readonly files in `.git/objects`

**Workaround if needed**: Manually delete the workspace folder after script completion

### Enable Specific Languages Only

To update only certain languages, modify `config.json`:

```json
"languages": {
    "java": {
        "enabled": true,  // Will be updated
        ...
    },
    "python": {
        "enabled": false,  // Will be skipped
        ...
    }
}
```

## Best Practices

1. **Always use `--enable-pr` for production** - Creates proper audit trail
2. **Test without `--enable-pr` first** - Verify updates before creating PR
3. **Use GitHub tokens** - Avoid rate limits and enable PR creation
4. **Keep token secure** - Use environment variables, not config file
5. **Review PRs before merging** - Verify all version updates are correct
6. **Run regularly** - Schedule via cron/Task Scheduler for automation

## Security Notes

- **Never commit GitHub tokens** to version control
- Store tokens in environment variables or secure credential managers
- The script includes SSL verification bypass for corporate environments
- Tokens should have minimal required permissions (`repo` scope only)

## Exit Codes

- **0**: Success - All operations completed
- **1**: Error - Configuration validation failed or critical error occurred

## Support

For issues or questions:
1. Check the troubleshooting section
2. Review GitHub API documentation
3. Verify configuration file syntax
4. Check GitHub token permissions

## License

This script is provided as-is for CyberSource SDK version management.
