# 🧪 Single API Test - User Registration

## 📋 What This Test Shows

This test demonstrates the complete data flow:
```
API Request → Controller → Service → Database → Response
```

## 🚀 How to Test This API

### Step 1: Start the Server
```bash
cd "c:\Users\tejas\Downloads\GOSbackend\GOSbackend"
npm run dev
```

### Step 2: Run the Test (in separate terminal)
```bash
node scripts/test-single-api.js
```

### Step 3: Or Test Manually with curl
```bash
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "API Test User",
    "email": "apitest1712345678901@example.com",
    "phone": "+1234567890",
    "password": "testpassword123",
    "role": "customer",
    "address": "123 Test Street",
    "latitude": 40.7128,
    "longitude": -74.0060
  }'
```

## 🎯 Expected Results

### API Response (200 OK):
```json
{
  "message": "User registered successfully. Please check your email for verification.",
  "user": {
    "id": "507f1f77bcf86cd799439011",
    "name": "API Test User",
    "email": "apitest1712345678901@example.com",
    "role": "customer",
    "verified": false
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Database Storage:
```javascript
// User stored in MongoDB with:
{
  _id: ObjectId("507f1f77bcf86cd799439011"),
  name: "API Test User",
  email: "apitest1712345678901@example.com",
  phone: "+1234567890",
  role: "customer",
  password: "$2a$10$hashedpassword...",
  verified: "Pending",
  syncStatus: "pending",
  zohoId: null,
  createdAt: ISODate("2024-04-01T17:30:00.000Z")
}
```

## 🔄 Data Flow Breakdown

### 1. API Request
- **Endpoint**: `POST /api/auth/register`
- **Headers**: `Content-Type: application/json`
- **Body**: User registration data

### 2. Controller Processing
- **File**: `controllers/authController.js`
- **Function**: `register()`
- **Validation**: Input validation and sanitization
- **Password Hashing**: bcrypt.hash()

### 3. Service Layer
- **File**: `services/databaseService.js`
- **Function**: `createUser()`
- **Business Logic**: User creation rules
- **Database Operation**: User.save()

### 4. Database Storage
- **Collection**: `users`
- **Indexes**: email (unique), role, isActive
- **Validation**: Mongoose schema validation

### 5. Response Generation
- **Success**: 201 Created with user data and token
- **JWT Token**: Generated with user ID and role
- **Background Sync**: Queued for Zoho synchronization

## 🔍 Verification Steps

### Check Database Directly:
```bash
# Connect to MongoDB
mongo geekonsites

# Find the user
db.users.find({email: /apitest/}).pretty()

# Check sync status
db.users.find({}, {name: 1, email: 1, syncStatus: 1, createdAt: 1})
```

### Check API Response:
```bash
# Test the API endpoint
curl -i -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@example.com","password":"test123","role":"customer"}'
```

## 🎯 Success Indicators

✅ **API Returns 201 Created**  
✅ **User ID Returned in Response**  
✅ **JWT Token Generated**  
✅ **User Stored in MongoDB**  
✅ **Password Hashed Correctly**  
✅ **Sync Status Set to 'pending'**  
✅ **Email is Unique**  
✅ **Role Validation Works**  

## 🚨 Common Issues

### Server Not Running:
```bash
npm run dev
```

### Port Already in Use:
```bash
npx kill-port 3000
```

### Database Connection:
```bash
npm run test-db
```

### Duplicate Email:
Change the email in the test request

## 📊 Performance Metrics

### Expected Response Time: < 500ms
### Database Insert Time: < 100ms
### JWT Generation Time: < 50ms
### Total Round Trip: < 600ms

This single API test demonstrates the complete data insertion flow from API request to database storage!
