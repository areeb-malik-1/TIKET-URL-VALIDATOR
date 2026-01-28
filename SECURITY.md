# Security Policy

## Credential Management

### Never Commit Sensitive Data
- **DO NOT** commit credentials, passwords, API keys, or tokens to the repository
- **DO NOT** hardcode session cookies or authentication tokens in source code
- **DO NOT** commit `.env` files or configuration files containing secrets

### Proper Credential Handling
- Use system properties for sensitive configuration: `-Dusername=... -Dsecret=...`
- Store credentials in environment variables or secure credential stores
- Use CI/CD secret management for automated tests

## Security Review Findings

### Fixed Issues
1. **Hardcoded Session Cookies** - Removed hardcoded Cookie header from LoginPage.java
   - Impact: Exposed session tokens could allow unauthorized access
   - Resolution: Rely on username/password authentication flow

### Best Practices

#### HTTP Client Configuration
- Timeout settings: 10 seconds (configured in VerifyUrls.java)
- Redirect handling: Manual handling with validation
- TLS/SSL: Using default Java HTTPS configuration

#### URL Validation
- URLs are validated before making requests
- Redirects are followed with safety checks
- Ignored URLs can be configured to skip validation

#### Logging
- Sensitive data should not be logged
- Use appropriate log levels (DEBUG for verbose, INFO for normal, ERROR for failures)
- Avoid logging full request/response bodies in production

## Dependency Security

All dependencies are regularly scanned for known vulnerabilities:
- Jackson: 2.15.2
- Log4j: 2.25.3 (addresses Log4Shell vulnerabilities)
- TestNG: 7.8.0
- Commons Lang3: 3.14.0

Run security checks before adding new dependencies:
```bash
mvn dependency:analyze
mvn versions:display-dependency-updates
```

## Reporting Security Issues

If you discover a security vulnerability, please:
1. **DO NOT** create a public GitHub issue
2. Contact the security team directly
3. Provide detailed information about the vulnerability
4. Allow time for the issue to be addressed before public disclosure

## Security Checklist for Contributors

- [ ] No hardcoded credentials or sensitive data
- [ ] No debugging code left in production
- [ ] No commented-out security-related code
- [ ] Proper error handling (no stack traces exposed to users)
- [ ] Input validation for all external data
- [ ] Dependencies are up-to-date and scanned for vulnerabilities
- [ ] Secrets are passed via environment variables or system properties
