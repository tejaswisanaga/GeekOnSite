# 🧪 API Testing Commands for GeekOnSites Backend

## 📋 Prerequisites
1. Make sure MongoDB is running
2. Start the API server: `npm run dev`
3. Run these commands in a separate terminal

## 🚀 Step-by-Step API Tests

### 1. Health Check
```bash
curl -X GET http://localhost:3000/api/health
```

### 2. User Registration
```bash
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "API Test User",
    "email": "apitest@example.com",
    "phone": "+1234567890",
    "password": "testpassword123",
    "role": "customer"
  }'
```

### 3. User Login
```bash
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "apitest@example.com",
    "password": "testpassword123"
  }'
```

### 4. Get User Profile (Replace TOKEN with actual token from login)
```bash
curl -X GET http://localhost:3000/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 5. Create Service Request (Replace TOKEN and SERVICE_ID)
```bash
curl -X POST http://localhost:3000/api/services/request \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "serviceId": "SERVICE_ID_FROM_DATABASE",
    "notes": "API test service request",
    "scheduledDate": "2024-04-02T10:00:00Z",
    "serviceLocation": {
      "address": "123 API Test Street",
      "latitude": 40.7128,
      "longitude": -74.0060
    }
  }'
```

### 6. Check Sync Status (Admin only)
```bash
curl -X GET http://localhost:3000/api/sync/status \
  -H "Authorization: Bearer ADMIN_JWT_TOKEN"
```

## 🔍 Database Verification

After running the API tests, check the database:

```bash
# Connect to MongoDB
mongo geekonsites

# Check users collection
db.users.find().pretty()

# Check service requests
db.servicerequests.find().pretty()

# Check sync status
db.users.find({}, {name: 1, email: 1, syncStatus: 1, zohoId: 1})
```

## 📊 Expected Results

### Successful Response Examples:

#### Health Check:
```json
{
  "status": "OK",
  "message": "GeekOnSites API is running",
  "database": {
    "status": "healthy",
    "state": "connected"
  }
}
```

#### User Registration:
```json
{
  "message": "User registered successfully",
  "user": {
    "id": "507f1f77bcf86cd799439011",
    "name": "API Test User",
    "email": "apitest@example.com",
    "role": "customer"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Service Request:
```json
{
  "_id": "507f1f77bcf86cd799439012",
  "customerId": "507f1f77bcf86cd799439011",
  "serviceId": "507f1f77bcf86cd799439013",
  "status": "pending",
  "totalAmount": 150,
  "notes": "API test service request"
}
```

## 🎯 Success Indicators

✅ **API Working**: Health check returns 200 OK
✅ **User Creation**: Registration returns user ID and token
✅ **Authentication**: Login works and returns token
✅ **Database Storage**: Data appears in MongoDB collections
✅ **Sync Tracking**: Records have syncStatus field

## 🔧 Troubleshooting

### Server Not Running:
```bash
npm run dev
```

### MongoDB Not Running:
```bash
mongod
```

### Port Already in Use:
```bash
# Kill process on port 3000
npx kill-port 3000
```

### Database Connection Issues:
```bash
npm run test-db
```

## 📈 Performance Check

Monitor API response times:
```bash
# Time the health check
time curl -X GET http://localhost:3000/api/health
```

## 🔄 Testing Workflow

1. **Start Server**: `npm run dev`
2. **Health Check**: Verify server is running
3. **Register User**: Create test user
4. **Login**: Get authentication token
5. **Create Request**: Test data insertion
6. **Verify Database**: Check MongoDB
7. **Check Sync**: Verify sync status

This comprehensive test will verify that data is being properly inserted through the API into the database!
