# 🔧 API Endpoint Troubleshooting Guide

## 🚨 "Route not found" Error Solutions

### ✅ Available API Endpoints

#### 🏥 Health Check (Working)
```bash
GET http://localhost:3000/api/health
```

#### 👤 Authentication Endpoints
```bash
POST http://localhost:3000/api/auth/register     # Register user
POST http://localhost:3000/api/auth/login        # User login
POST http://localhost:3000/api/auth/verify-email # Verify email
POST http://localhost:3000/api/auth/resend-verification # Resend code
GET  http://localhost:3000/api/auth/me           # Get current user (requires auth)
```

#### 👥 User Management
```bash
GET  http://localhost:3000/api/users/profile     # Get user profile (requires auth)
PUT  http://localhost:3000/api/users/profile     # Update profile (requires auth)
POST http://localhost:3000/api/users/assessor-id # Add assessor ID (requires auth)
GET  http://localhost:3000/api/users             # Get all users (admin only)
```

#### 🔧 Services
```bash
GET  http://localhost:3000/api/services          # Get all services
POST http://localhost:3000/api/services/request  # Create service request (requires auth)
GET  http://localhost:3000/api/services/my-requests # Get my requests (requires auth)
```

#### 🔧 Technicians
```bash
GET  http://localhost:3000/api/technicians        # Get all technicians
GET  http://localhost:3000/api/technicians/nearby # Get nearby technicians
PUT  http://localhost:3000/api/technicians/location # Update location (requires auth)
```

#### 📊 Dashboard
```bash
GET http://localhost:3000/api/dashboard/customer  # Customer dashboard (requires auth)
GET http://localhost:3000/api/dashboard/technician # Technician dashboard (requires auth)
GET http://localhost:3000/api/dashboard/admin     # Admin dashboard (requires auth)
```

#### 🔄 Synchronization (Admin Only)
```bash
GET  http://localhost:3000/api/sync/status      # Get sync status
POST http://localhost:3000/api/sync/full-sync    # Full sync
POST http://localhost:3000/api/sync/sync/:entity # Sync specific entity
POST http://localhost:3000/api/sync/cleanup      # Cleanup expired
```

## 🧪 Working Test Examples

### ✅ Test User Registration
```bash
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "phone": "+1234567890",
    "password": "testpassword123",
    "role": "customer"
  }'
```

### ✅ Test User Login
```bash
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "testpassword123"
  }'
```

### ✅ Test Health Check
```bash
curl http://localhost:3000/api/health
```

### ✅ Test Get Services
```bash
curl http://localhost:3000/api/services
```

## ❌ Common "Route not found" Causes

### 1. Wrong HTTP Method
```bash
# ❌ WRONG (using GET instead of POST)
curl http://localhost:3000/api/auth/register

# ✅ CORRECT (using POST)
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@example.com","password":"test123","role":"customer"}'
```

### 2. Missing Authentication
```bash
# ❌ WRONG (no token for protected route)
curl http://localhost:3000/api/users/profile

# ✅ CORRECT (with token)
curl http://localhost:3000/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 3. Wrong URL Path
```bash
# ❌ WRONG (missing /api)
curl http://localhost:3000/auth/register

# ✅ CORRECT (with /api)
curl -X POST http://localhost:3000/api/auth/register
```

### 4. Missing Request Body
```bash
# ❌ WRONG (no data for POST)
curl -X POST http://localhost:3000/api/auth/register

# ✅ CORRECT (with JSON data)
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@example.com","password":"test123","role":"customer"}'
```

## 🔍 Debugging Steps

### 1. Check Server Status
```bash
curl http://localhost:3000/api/health
```

### 2. Check Available Routes
The server logs show which routes are mounted:
- `/api/auth` → Authentication routes
- `/api/users` → User management
- `/api/services` → Service management
- `/api/technicians` → Technician management
- `/api/dashboard` → Dashboard endpoints
- `/api/sync` → Synchronization endpoints

### 3. Check Method and URL
Make sure you're using:
- ✅ Correct HTTP method (GET, POST, PUT, DELETE)
- ✅ Correct URL path (starts with `/api/`)
- ✅ Proper headers (Content-Type, Authorization)
- ✅ Valid JSON body for POST/PUT requests

## 🚀 Quick Test

Try this simple test to verify the server is working:

```bash
# 1. Health check
curl http://localhost:3000/api/health

# 2. Get services (no auth required)
curl http://localhost:3000/api/services

# 3. Register a user
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test123@example.com","password":"test123","role":"customer"}'
```

If these work, your server is running correctly! The "Route not found" error is likely due to an incorrect URL, method, or missing authentication.
