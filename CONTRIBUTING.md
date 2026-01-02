# Contributing to Sell Now Plugin

Thank you for your interest in contributing to the Sell Now RuneLite plugin! This document provides guidelines and instructions for contributing.

## Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [Getting Started](#getting-started)
3. [How to Contribute](#how-to-contribute)
4. [Development Setup](#development-setup)
5. [Coding Standards](#coding-standards)
6. [Testing Guidelines](#testing-guidelines)
7. [Pull Request Process](#pull-request-process)
8. [Issue Reporting](#issue-reporting)

## Code of Conduct

### Our Pledge

We are committed to providing a welcoming and inspiring community for all. Please be respectful and constructive in all interactions.

### Expected Behavior

- Be respectful and inclusive
- Welcome newcomers
- Focus on constructive feedback
- Be patient and helpful
- Respect different viewpoints

### Unacceptable Behavior

- Harassment or discrimination
- Trolling or insulting comments
- Personal or political attacks
- Publishing others' private information
- Spam or off-topic content

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Gradle build tool
- RuneLite development environment
- Git for version control
- IDE (IntelliJ IDEA recommended)

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/Sell-now.git
   cd Sell-now
   ```
3. Add upstream remote:
   ```bash
   git remote add upstream https://github.com/codyplummer91-cpu/Sell-now.git
   ```

## How to Contribute

### Types of Contributions

We welcome various types of contributions:

1. **Bug Fixes**: Fix issues reported in GitHub Issues
2. **Features**: Implement new features from the roadmap
3. **Documentation**: Improve or add documentation
4. **Testing**: Add or improve test coverage
5. **Performance**: Optimize code performance
6. **Refactoring**: Improve code quality and structure

### Areas for Contribution

- **API Integration**: Add support for additional price APIs
- **UI/UX**: Improve visual design and user experience
- **Performance**: Optimize rendering and caching
- **Features**: Implement planned features from CHANGELOG.md
- **Documentation**: Improve guides and examples
- **Testing**: Add unit and integration tests

## Development Setup

### Setting Up RuneLite Development Environment

1. **Clone RuneLite**:
   ```bash
   git clone https://github.com/runelite/runelite.git
   cd runelite
   ```

2. **Build RuneLite**:
   ```bash
   ./gradlew build
   ```

3. **Link Plugin**:
   - Copy or symlink plugin source to RuneLite's external plugins directory
   - Or build plugin JAR and place in plugins folder

### Building the Plugin

```bash
cd Sell-now
gradle build
```

### Running Tests

```bash
gradle test
```

### IDE Setup

**IntelliJ IDEA**:
1. Import project as Gradle project
2. Set JDK to version 11+
3. Enable Lombok annotation processing
4. Configure code style (see below)

## Coding Standards

### Java Style Guide

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use 4 spaces for indentation (no tabs)
- Maximum line length: 120 characters
- Use meaningful variable and method names

### Code Organization

```java
// 1. Package declaration
package com.sellnow;

// 2. Imports (organized)
import java.util.*;
import javax.inject.*;
import net.runelite.api.*;
import net.runelite.client.*;

// 3. Class documentation
/**
 * Description of the class
 */
@Annotations
public class ClassName {
    
    // 4. Constants
    private static final String CONSTANT = "value";
    
    // 5. Instance variables
    private final DependencyType dependency;
    
    // 6. Constructor
    @Inject
    public ClassName(DependencyType dependency) {
        this.dependency = dependency;
    }
    
    // 7. Public methods
    public void publicMethod() {
        // Implementation
    }
    
    // 8. Private methods
    private void privateMethod() {
        // Implementation
    }
}
```

### Documentation

- Add Javadoc comments for all public classes and methods
- Use inline comments for complex logic
- Keep comments up-to-date with code changes
- Document parameters, return values, and exceptions

### Example Documentation

```java
/**
 * Fetches the current price for an item from the API
 * 
 * @param itemId The unique identifier of the item
 * @return ItemPriceData containing price information, or null if not found
 * @throws IOException if the API request fails
 */
public ItemPriceData fetchPrice(int itemId) throws IOException {
    // Implementation
}
```

## Testing Guidelines

### Writing Tests

- Write unit tests for all new functionality
- Aim for 80%+ code coverage
- Use descriptive test names
- Follow Arrange-Act-Assert pattern

### Test Structure

```java
@Test
public void testMethodName_Scenario_ExpectedResult() {
    // Arrange
    GEPriceService service = new GEPriceService(mockClient);
    
    // Act
    ItemPriceData result = service.getItemPriceData(123);
    
    // Assert
    assertNotNull(result);
    assertEquals(123, result.getItemId());
}
```

### Integration Tests

- Test API integration with mocked responses
- Verify overlay rendering logic
- Test configuration changes

## Pull Request Process

### Before Submitting

1. **Update your fork**:
   ```bash
   git fetch upstream
   git checkout main
   git merge upstream/main
   ```

2. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**:
   - Write clean, documented code
   - Add tests for new functionality
   - Update documentation as needed

4. **Test thoroughly**:
   - Run all tests: `gradle test`
   - Test manually in RuneLite
   - Check for regressions

5. **Commit changes**:
   ```bash
   git add .
   git commit -m "Brief description of changes"
   ```

### Commit Message Format

Use conventional commit format:

```
type(scope): Brief description

Detailed explanation of changes (if needed)

Fixes #issue_number
```

**Types**:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

**Examples**:
```
feat(overlay): Add support for trade window highlighting

fix(api): Handle null response from OSRS Wiki API
Fixes #42

docs(readme): Update installation instructions
```

### Submitting Pull Request

1. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

2. **Create PR on GitHub**:
   - Go to original repository
   - Click "New Pull Request"
   - Select your branch
   - Fill out PR template

3. **PR Description should include**:
   - Clear description of changes
   - Related issue numbers
   - Screenshots (for UI changes)
   - Testing performed
   - Breaking changes (if any)

### PR Review Process

1. **Automated checks**: Wait for CI/CD to pass
2. **Code review**: Address reviewer feedback
3. **Updates**: Push additional commits if needed
4. **Approval**: Wait for maintainer approval
5. **Merge**: Maintainer will merge when ready

## Issue Reporting

### Before Creating an Issue

1. Search existing issues to avoid duplicates
2. Gather relevant information:
   - RuneLite version
   - Plugin version
   - Operating system
   - Steps to reproduce
   - Expected vs actual behavior
   - Screenshots/logs

### Issue Templates

**Bug Report**:
```markdown
**Description**
Clear description of the bug

**Steps to Reproduce**
1. Step one
2. Step two
3. ...

**Expected Behavior**
What should happen

**Actual Behavior**
What actually happens

**Environment**
- RuneLite version: 
- Plugin version: 
- OS: 

**Screenshots/Logs**
[Attach if applicable]
```

**Feature Request**:
```markdown
**Feature Description**
Clear description of the feature

**Use Case**
Why this feature would be useful

**Proposed Solution**
How you envision it working

**Alternatives Considered**
Other approaches you've thought of
```

## Development Tips

### Useful Commands

```bash
# Build plugin
gradle build

# Clean build
gradle clean build

# Run tests
gradle test

# Check code style
gradle checkstyle

# Generate documentation
gradle javadoc
```

### Debugging

- Use RuneLite's developer tools
- Enable verbose logging in config
- Use breakpoints in IDE
- Monitor RuneLite console output

### Performance Considerations

- Minimize overlay rendering overhead
- Use efficient data structures
- Cache expensive operations
- Profile before optimizing

## Getting Help

- **Questions**: Open a GitHub Discussion
- **Bugs**: Create an Issue with bug template
- **Features**: Create an Issue with feature template
- **Chat**: Join RuneLite Discord (if available)

## Recognition

Contributors will be recognized in:
- README.md contributors section
- Release notes
- GitHub contributors page

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

## Thank You!

Your contributions make this plugin better for everyone. Thank you for taking the time to contribute! 🎉
