# Windows PowerShell Commands to Pull and Deploy Sell-now Plugin

## Getting Started on Your Local Windows Machine

This guide provides the PowerShell commands to clone/pull this repository to your local Windows machine and deploy it to your existing RuneLite project.

---

## Step 1: Clone or Pull the Repository

### Option A: First Time (Clone)

Open PowerShell and navigate to where you want the code:

```powershell
# Navigate to your projects directory
cd ~\Documents\GitHub  # or wherever you keep your projects

# Clone the repository
git clone https://github.com/codyplummer91-cpu/Sell-now.git

# Navigate into the project
cd Sell-now
```

### Option B: Update Existing (Pull)

If you already have the repo cloned:

```powershell
# Navigate to your existing Sell-now directory
cd ~\Documents\GitHub\Sell-now  # adjust path as needed

# Pull the latest changes
git pull origin copilot/add-item-highlighting-plugin
```

---

## Step 2: Deploy to Your Local RuneLite Project

### Automated Deployment (Recommended)

Run the PowerShell deployment script:

```powershell
# Make sure you're in the Sell-now directory
cd Sell-now  # if not already there

# Run the deployment script
.\deploy-to-runelite.ps1
```

The script will:
- ✅ Copy all Java source files to your RuneLite plugins directory
- ✅ Copy any resources needed
- ✅ Create the necessary directory structure
- ✅ Show you the next steps

### Manual Deployment

If you prefer to copy files manually:

```powershell
# Set variables (update paths if needed)
$RUNELITE = "C:\Users\cplum\IdeaProjects\runelite"
$DEST = "$RUNELITE\runelite-client\src\main\java\net\runelite\client\plugins\sellnow"

# Create destination directory
New-Item -ItemType Directory -Path $DEST -Force

# Copy Java files
Copy-Item -Path ".\src\main\java\com\sellnow\*.java" -Destination $DEST -Force

# Verify files were copied
Get-ChildItem $DEST
```

---

## Step 3: Build and Run in IntelliJ IDEA

### In IntelliJ IDEA:

1. **Open Your RuneLite Project**
   - File → Open → `C:\Users\cplum\IdeaProjects\runelite`

2. **Refresh Gradle**
   - View → Tool Windows → Gradle
   - Click the refresh/reload button 🔄
   - Or: File → Invalidate Caches / Restart → Invalidate and Restart

3. **Build the Project**
   - Build → Build Project (`Ctrl+F9`)
   - Or in Gradle window: runelite → Tasks → build → build

4. **Run RuneLite**
   - Find or create a run configuration for `net.runelite.client.RuneLite`
   - Click the green play button ▶️ or press `Shift+F10`

5. **Enable the Plugin**
   - In RuneLite, click the wrench icon (Configuration)
   - Find "Sell Now" in the plugin list
   - Toggle it ON

6. **Configure Plugin Settings**
   - Click the gear icon next to "Sell Now"
   - Adjust colors, update intervals, etc.

---

## Step 4: Test the Plugin

1. **Log into OSRS**
2. **Wait for Price Data** (about 5 minutes for initial load)
3. **Open Inventory or Bank** - Items at all-time high will have colored borders!

---

## Troubleshooting

### Plugin Not Showing Up

```powershell
# Verify files were copied correctly
Get-ChildItem "C:\Users\cplum\IdeaProjects\runelite\runelite-client\src\main\java\net\runelite\client\plugins\sellnow"

# Should show:
# - GEPriceService.java
# - ItemPriceData.java
# - SellNowConfig.java
# - SellNowOverlay.java
# - SellNowPlugin.java
```

### Compilation Errors in IntelliJ

1. Make sure you're using Java 11 for the project
2. Rebuild the project: Build → Rebuild Project
3. Check that Lombok plugin is installed in IntelliJ
4. Verify Gradle sync completed successfully

### Need to Update Your Copy

```powershell
# Pull latest changes
cd ~\Documents\GitHub\Sell-now
git pull origin copilot/add-item-highlighting-plugin

# Redeploy
.\deploy-to-runelite.ps1

# Then rebuild in IntelliJ
```

---

## File Structure After Deployment

Your RuneLite project should have these new files:

```
C:\Users\cplum\IdeaProjects\runelite\
└── runelite-client\
    └── src\
        └── main\
            └── java\
                └── net\
                    └── runelite\
                        └── client\
                            └── plugins\
                                └── sellnow\
                                    ├── GEPriceService.java
                                    ├── ItemPriceData.java
                                    ├── SellNowConfig.java
                                    ├── SellNowOverlay.java
                                    └── SellNowPlugin.java
```

---

## Quick Reference Commands

```powershell
# Update from GitHub
cd ~\Documents\GitHub\Sell-now
git pull

# Deploy to RuneLite
.\deploy-to-runelite.ps1

# Check what files are in your plugin directory
Get-ChildItem "C:\Users\cplum\IdeaProjects\runelite\runelite-client\src\main\java\net\runelite\client\plugins\sellnow"

# View git status
git status

# View recent changes
git log --oneline -5
```

---

## Additional Notes

### Package Name Change

Note that when you copy files to RuneLite's plugin directory, you're changing the package structure from:
- `com.sellnow.*` (standalone plugin)

to:
- `net.runelite.client.plugins.sellnow.*` (integrated into RuneLite)

The deployment script handles this automatically by placing files in the correct location within RuneLite's plugin structure.

### Updating the Plugin

Whenever you make changes to the plugin code in GitHub Codespaces or elsewhere:

1. Commit and push changes
2. Pull changes on your local machine
3. Run the deployment script again
4. Rebuild in IntelliJ

### Development Workflow

For active development, you might want to:
1. Make changes directly in your RuneLite project in IntelliJ
2. Test them in RuneLite
3. Copy the working files back to your Sell-now repo
4. Commit and push to GitHub

---

## Need Help?

If you encounter issues:
1. Check the "Troubleshooting" section above
2. Verify all file paths are correct
3. Ensure RuneLite builds successfully without the plugin first
4. Check IntelliJ's Event Log for error messages
