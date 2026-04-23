# Hostinger Deployment Guide for GeekOnSites

## Problem
Admin and Agent portals don't work on Hostinger/WordPress because they depend on Spring Boot API.

## Solution Options

### Option 1: Deploy Backend API to Hostinger (Recommended)

#### Step 1: Deploy Spring Boot Backend
1. Export your Spring Boot project as JAR file
2. Upload to Hostinger VPS or cloud hosting
3. Install Java runtime on Hostinger server
4. Run backend service: `java -jar geekonsite-backend.jar`
5. Configure domain and SSL

#### Step 2: Update Frontend API URLs
Change in all HTML files:
```javascript
const API_BASE_URL = 'https://your-hostinger-domain.com/api';
```

#### Step 3: Configure Database
- Set up MongoDB on Hostinger
- Update database connection in application.properties
- Migrate existing data from Render

### Option 2: Use Render for Backend + Hostinger for Frontend

#### Step 1: Keep Backend on Render
- Spring Boot API stays on Render
- Update CORS to allow Hostinger domain

#### Step 2: Update Frontend API URLs
```javascript
const API_BASE_URL = 'https://geekonsite-2.onrender.com/api';
```

#### Step 3: Configure CORS in Backend
Add to WebSecurityConfig.java:
```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
            .allowedOrigins("https://your-hostinger-domain.com")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowCredentials(true);
}
```

### Option 3: Static WordPress Integration

#### Step 1: Create Static Pages
- Convert admin/agent portals to static HTML
- Remove API dependencies
- Add contact forms for manual processing

#### Step 2: WordPress Integration
- Use WordPress for marketing pages
- Link to Render for functional features
- Add "Login" buttons that redirect to Render

## Recommended Approach

**Best Solution:** Option 1 - Deploy complete stack to Hostinger
- Full control over application
- Better performance
- Single domain management
- Complete functionality

**Quick Fix:** Option 2 - Hybrid approach
- Fast deployment
- Uses existing backend
- Minimal changes needed

## Files to Update

After deployment, update these files with new API URL:
- admin-panel.html
- agent-login.html  
- agent-dashboard.html
- agent-portal.html
- agent-register-enhanced.html
- customer-register.html

## Testing Checklist

1. Backend API health check: `https://your-domain.com/api/health`
2. Admin login functionality
3. Agent dashboard loading
4. Ticket creation and assignment
5. Customer registration

## Support

If you need help with deployment, contact:
- Hostinger support for server setup
- Developer for backend deployment
- Database migration assistance
