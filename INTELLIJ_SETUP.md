# IntelliJ IDEA Setup Guide for RuneLite Development

## Setup Complete! ✓

Your Sell-now plugin is now configured to run with RuneLite's development client.

## What Was Done

1. **Cloned RuneLite Repository** → `/workspaces/runelite`
2. **Added Plugin to RuneLite** → Modified `/workspaces/runelite/runelite-client/settings.gradle.kts`
3. **Added Dependency** → Modified `/workspaces/runelite/runelite-client/build.gradle.kts`

## Next Steps in IntelliJ IDEA

### 1. Open RuneLite in IntelliJ

```bash
# Option A: Open via command line
idea /workspaces/runelite

# Option B: In IntelliJ
File → Open → Select /workspaces/runelite
```

### 2. Let Gradle Sync

- IntelliJ will detect the Gradle project automatically
- Wait for the Gradle sync to complete (may take 5-10 minutes first time)
- Check the "Build" tab at the bottom for progress

### 3. Build Your Plugin First

Before running RuneLite, build your plugin:

```bash
cd /workspaces/Sell-now
./gradlew build
```

Or in IntelliJ:
- Open the Gradle tool window (right sidebar)
- Navigate to: Sell-now → Tasks → build → build
- Double-click to run

### 4. Create Run Configuration

#### Option A: Using Gradle (Recommended)

1. Open Gradle tool window (View → Tool Windows → Gradle)
2. Navigate to: runelite → runelite-client → Tasks → application → run
3. Right-click "run" → "Modify Run Configuration"
4. Click OK to save
5. Run it!

#### Option B: Manual Configuration

1. **Run → Edit Configurations**
2. Click **+** → **Application**
3. Configure:
   - **Name**: `RuneLite with Sell-now`
   - **Module**: `client.main`
   - **Main class**: `net.runelite.client.RuneLite`
   - **VM options**: 
     ```
     -ea
     -Xmx2g
     -Drunelite.launcher.nojvm=true
     ```
   - **Working directory**: `/workspaces/runelite/runelite-client`
   - **Use classpath of module**: `client.main`
4. Click **Apply** then **OK**

### 5. Run RuneLite

- Click the green play button ▶️
- RuneLite will start with your Sell-now plugin loaded!
- The plugin should appear in the plugin list automatically

### 6. Enable Your Plugin

Once RuneLite starts:
1. Click the wrench icon (Configuration)
2. Find "Sell Now" in the plugin list
3. Toggle it ON
4. Configure settings if needed

### 7. Test the Plugin

1. Log into OSRS
2. Wait 5 minutes for price data to load
3. Open your inventory or bank
4. Items at all-time high will have colored borders!

## Troubleshooting

### Plugin Not Showing Up

Make sure you built your plugin first:
```bash
cd /workspaces/Sell-now
./gradlew clean build
```

Then rebuild RuneLite:
```bash
cd /workspaces/runelite
./gradlew clean build
```

### Gradle Sync Issues

Try invalidating caches:
1. File → Invalidate Caches
2. Check "Clear file system cache and Local History"
3. Click "Invalidate and Restart"

### Module Not Found

Ensure Java 11 SDK is configured:
1. File → Project Structure → Project
2. SDK: Java 11
3. Language level: 11

## Development Workflow

### Making Changes

1. Edit files in `/workspaces/Sell-now/src/`
2. Build your plugin: `cd /workspaces/Sell-now && ./gradlew build`
3. In IntelliJ: Build → Rebuild Project
4. Restart RuneLite run configuration

### Hot Reload (Advanced)

For faster iteration, you can use RuneLite's plugin development mode which allows some changes without full restart.

### Debugging

1. Set breakpoints in your plugin code
2. Use the Debug button (🐛) instead of Run
3. RuneLite will stop at your breakpoints

## Directory Structure

```
/workspaces/
├── Sell-now/              # Your plugin
│   ├── src/
│   ├── build.gradle
│   └── ...
└── runelite/              # RuneLite development client
    ├── runelite-client/   # Main client code
    ├── runelite-api/      # API your plugin uses
    └── ...
```

## Additional Resources

- [RuneLite Plugin Hub](https://github.com/runelite/runelite/wiki/Building-with-IntelliJ-IDEA)
- [RuneLite API Docs](https://static.runelite.net/api/runelite-api/)
- [Plugin Development Guide](https://github.com/runelite/runelite/wiki/Plugin-Development-Guide)

## Quick Commands Reference

```bash
# Build your plugin
cd /workspaces/Sell-now && ./gradlew build

# Build RuneLite
cd /workspaces/runelite && ./gradlew build

# Run RuneLite from terminal
cd /workspaces/runelite/runelite-client && ../gradlew run

# Clean everything
cd /workspaces/Sell-now && ./gradlew clean
cd /workspaces/runelite && ./gradlew clean
```
