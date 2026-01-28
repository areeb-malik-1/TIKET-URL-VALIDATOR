# TIKET URL Validator

A comprehensive URL validation testing framework for the Tiket.com platform. This project validates URLs and endpoints across various services including flights, hotels, tours, events, and other travel-related modules.

## Overview

This automated testing framework:
- Validates image URLs (iconUrl, imageUrl, etc.) from API responses
- Validates clickable endpoints (clickUrl, actionUrl, etc.)
- Handles redirects and verifies final destinations
- Generates detailed HTML reports using ExtentReports
- Supports parallel test execution
- Sends test results to Slack

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Valid Tiket.com credentials

## Configuration

The following system properties are required to run the tests:

| Property | Description | Required | Example |
|----------|-------------|----------|---------|
| `env` | Target environment (PROD, PREPROD, GK) | Yes | `PROD` |
| `username` | Tiket.com account email | Yes | `user@example.com` |
| `secret` | Tiket.com account password | Yes | `yourpassword` |
| `platform` | Platform type | Yes | `ANDROID` |
| `SLACK_URL` | Slack webhook URL for notifications | No | `https://hooks.slack.com/...` |

## Building the Project

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test -Denv=PROD -Dusername=your@email.com -Dsecret=yourpassword -Dplatform=ANDROID
```

## Project Structure

```
src/
├── main/java/com/tiket/
│   ├── annotation/       # Custom annotations for test metadata
│   ├── core/            # Core utilities (Slack formatting)
│   ├── io/              # I/O operations (Slack integration)
│   ├── logging/         # Logging framework
│   ├── model/           # Data models
│   ├── page/            # Page objects for authentication
│   ├── report/          # ExtentReports configuration
│   ├── service/         # Service layer (base URLs, etc.)
│   ├── testbase/        # Base classes for tests and APIs
│   ├── testng/          # TestNG listeners
│   └── verify/          # URL verification logic
└── test/java/com/tiket/
    ├── api/             # API client implementations
    └── test/            # Test classes organized by module
```

## Features

### URL Verification
- Validates full URLs (images, resources)
- Validates relative endpoints
- Handles HTTP redirects (301, 302, 307, 308)
- Supports both GET and HEAD requests
- Configurable timeout (10 seconds)

### Reporting
- HTML reports generated in `./extent-reports/`
- Real-time test progress logging
- Test categorization by module and vertical
- Pass/Fail/Skip statistics
- Slack notifications (optional)

### Test Execution
- Parallel test execution (configurable thread count: 20)
- Data-driven testing with TestNG DataProviders
- Test categorization with custom annotations
- Comprehensive logging with Log4j2

## Test Organization

Tests are organized by service modules:
- **Flight**: Flight search and page modules
- **Hotel**: Hotel search and page modules
- **Home**: Home page and navigation
- **Villas & Apartments**: Accommodation services
- **Train & Woosh**: Transportation services
- **Airport Transfer**: Transfer services
- **Bus & Shuttle**: Bus transportation
- **Car Rental**: Vehicle rental services
- **Events**: Event ticketing
- **Tours**: Tour packages
- **TTD/Todo**: Things to do activities

## URL Mapping Configuration

URL and endpoint keys are configured in `Mapping.java`. Each test class is mapped to specific JSON keys to extract from API responses.

Example:
```java
Map.entry(TestFlightPageModule.class.getName(), new Data(
    new String[]{"icon", "url", "mobileUrl", "airlineIcon", "imageUrl"},  // URL keys
    new String[]{"clickUrl", "actionUrl"}  // Endpoint keys
))
```

## Ignored URLs

URLs that should be skipped during validation can be added to `Ignored.java`:
```java
private static List<String> list = List.of(
    "https://www.tiket.com"
);
```

## Reports

After test execution:
- HTML report: `./extent-reports/extent-report.html`
- Logs: `./log/test-automation.log`

## Customization

### Adding New Test Modules

1. Create API client in `src/main/java/com/tiket/api/<module>/`
2. Implement `BaseApi` interface
3. Create test class in `src/test/java/com/tiket/test/<module>/`
4. Extend `BaseTest`
5. Add URL mapping in `Mapping.java`

### Modifying Thread Count

Edit `pom.xml`:
```xml
<threadCount>20</threadCount>
```

## Dependencies

- TestNG 7.8.0 - Testing framework
- Jackson 2.15.2 - JSON processing
- Log4j 2.25.3 - Logging
- ExtentReports 5.0.9 - HTML reporting
- Commons Lang3 3.14.0 - Utility functions
- Lombok 1.18.32 - Code generation

## License

This project is proprietary to Tiket.com.

## Support

For questions or issues, please contact the Tiket.com QA team.
