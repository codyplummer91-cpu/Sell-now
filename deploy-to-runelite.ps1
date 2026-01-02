# PowerShell Script to Deploy Sell-now Plugin to Local RuneLite
# This script copies the plugin source files to your local RuneLite project

# Configuration - Update these paths if needed
$RUNELITE_PATH = "C:\Users\cplum\IdeaProjects\runelite"
$PLUGIN_JAVA_DEST = "$RUNELITE_PATH\runelite-client\src\main\java\net\runelite\client\plugins\sellnow"
$PLUGIN_RESOURCES_DEST = "$RUNELITE_PATH\runelite-client\src\main\resources"

# Source paths (current directory)
$PLUGIN_JAVA_SRC = ".\src\main\java\com\sellnow"
$PLUGIN_RESOURCES_SRC = ".\src\main\resources"

Write-Host "==================================" -ForegroundColor Cyan
Write-Host "Sell-now Plugin Deployment Script" -ForegroundColor Cyan
Write-Host "==================================" -ForegroundColor Cyan
Write-Host ""

# Check if RuneLite path exists
if (-Not (Test-Path $RUNELITE_PATH)) {
    Write-Host "ERROR: RuneLite path not found: $RUNELITE_PATH" -ForegroundColor Red
    Write-Host "Please update the `$RUNELITE_PATH variable in this script." -ForegroundColor Yellow
    exit 1
}

Write-Host "Found RuneLite project at: $RUNELITE_PATH" -ForegroundColor Green
Write-Host ""

# Create destination directory if it doesn't exist
if (-Not (Test-Path $PLUGIN_JAVA_DEST)) {
    Write-Host "Creating plugin directory: $PLUGIN_JAVA_DEST" -ForegroundColor Yellow
    New-Item -ItemType Directory -Path $PLUGIN_JAVA_DEST -Force | Out-Null
}

# Copy Java source files
Write-Host "Copying Java source files..." -ForegroundColor Cyan
Get-ChildItem -Path $PLUGIN_JAVA_SRC -Filter "*.java" | ForEach-Object {
    $destFile = Join-Path $PLUGIN_JAVA_DEST $_.Name
    Copy-Item $_.FullName -Destination $destFile -Force
    Write-Host "  - Copied: $($_.Name)" -ForegroundColor Gray
}

# Copy resources if they exist
if (Test-Path $PLUGIN_RESOURCES_SRC) {
    Write-Host ""
    Write-Host "Copying resource files..." -ForegroundColor Cyan
    Copy-Item -Path "$PLUGIN_RESOURCES_SRC\*" -Destination $PLUGIN_RESOURCES_DEST -Recurse -Force
    Write-Host "  - Resources copied" -ForegroundColor Gray
} else {
    Write-Host ""
    Write-Host "No resource files to copy" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "==================================" -ForegroundColor Green
Write-Host "Deployment Complete!" -ForegroundColor Green
Write-Host "==================================" -ForegroundColor Green
Write-Host ""
Write-Host "Files copied to:" -ForegroundColor Cyan
Write-Host "  $PLUGIN_JAVA_DEST" -ForegroundColor White
Write-Host ""
Write-Host "Next Steps:" -ForegroundColor Yellow
Write-Host "  1. Open IntelliJ IDEA with your RuneLite project" -ForegroundColor White
Write-Host "  2. Refresh/reimport the Gradle project" -ForegroundColor White
Write-Host "  3. Build the project (Ctrl+F9 or Build > Build Project)" -ForegroundColor White
Write-Host "  4. Run RuneLite (Shift+F10)" -ForegroundColor White
Write-Host "  5. Enable 'Sell Now' plugin in RuneLite's plugin panel" -ForegroundColor White
Write-Host ""
