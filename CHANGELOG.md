# Changelog

All notable changes to the Sell Now RuneLite plugin will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-01-02

### Added
- Initial release of Sell Now plugin
- Real-time GE price tracking from OSRS Wiki API
- All-time high detection for all tradeable items
- Color-coded highlighting system with 7 price tiers:
  - Gray: < 10k gp
  - White: 10k - 100k gp
  - Green: 100k - 1m gp
  - Blue: 1m - 10m gp
  - Purple: 10m - 100m gp
  - Orange: 100m - 1b gp
  - Red: >= 1b gp
- Visual overlays for inventory items
- Visual overlays for bank items
- Visual overlays for Grand Exchange items
- Price tooltips on hover
- Configurable color customization
- Configurable highlight thickness
- Configurable update interval (5-60 minutes)
- Enable/disable toggle for highlighting
- Enable/disable toggle for tooltips
- Comprehensive documentation:
  - README.md - Project overview
  - QUICKSTART.md - User guide
  - TECHNICAL.md - Architecture documentation
  - CONFIG_GUIDE.md - Configuration reference
  - CHANGELOG.md - Version history
  - CONTRIBUTING.md - Contribution guidelines

### Technical Details
- Uses OSRS Wiki Price API for real-time data
- Implements thread-safe price caching
- Scheduled price updates every 5 minutes (default)
- Support for 20,000+ tradeable items
- Minimal performance overhead
- Compatible with RuneLite 1.10.28+

## [Unreleased]

### Planned Features
- Support for additional price APIs (GE Tracker, Official RuneScape)
- Price change notifications
- Historical price graphs
- Persistent all-time high storage
- Trade window highlighting
- Loot tracking integration
- Price alerts/notifications
- Export functionality for price data
- Mini price display directly on items
- Customizable highlight styles (border, glow, etc.)
- Sound notifications for high-value ATH items
- Statistics panel with profit tracking
- Comparison with market average
- Trend indicators (rising/falling)
- Discord webhook integration for alerts

### Known Issues
- Plugin requires RuneLite environment to build
- External API dependency (OSRS Wiki must be accessible)
- All-time high data resets on plugin restart
- No offline mode

### Future Improvements
- Add persistent storage for ATH data
- Implement fallback API sources
- Optimize memory usage for large item sets
- Add unit tests for core functionality
- Improve error handling for API failures
- Add rate limiting protection
- Implement caching strategies
- Add performance metrics
- Support for ironman mode (different price contexts)
- Mobile RuneLite compatibility

## Version History

### Version Numbering
- **Major version** (X.0.0): Breaking changes, major feature additions
- **Minor version** (0.X.0): New features, backwards compatible
- **Patch version** (0.0.X): Bug fixes, minor improvements

### Release Notes Format
Each release includes:
- **Added**: New features
- **Changed**: Changes to existing functionality
- **Deprecated**: Features that will be removed
- **Removed**: Features that have been removed
- **Fixed**: Bug fixes
- **Security**: Security-related changes

## Support

For questions about specific versions:
- Current version issues: [GitHub Issues](https://github.com/codyplummer91-cpu/Sell-now/issues)
- General support: See README.md
- Configuration help: See CONFIG_GUIDE.md
- Technical details: See TECHNICAL.md

## Contributing

See CONTRIBUTING.md for how to contribute to this project.

## License

This project is licensed under the MIT License - see LICENSE file for details.
