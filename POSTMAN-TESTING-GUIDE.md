# 🧪 Postman Testing Guide for GeekOnSites API

## 📋 Setup Instructions

### 1. Import the Collection
1. Open Postman
2. Click **Import** in the top left
3. Select **File** → **Upload Files**
4. Choose `GeekOnSites-API-Collection.postman_collection.json`
5. Click **Import**

### 2. Set Environment Variables
The collection uses variables for tokens and IDs. You'll need to set these after authentication:

- `adminToken` - JWT token for admin user
- `customerToken` - JWT token for customer user  
- `technicianToken` - JWT token for technician user
- `serviceId` - ID of a service for testing requests
- `requestId` - ID of a service request
- `userId` - ID of a user for testing
- `timestamp` - Auto-generated for unique emails

## 🚀 Testing Workflow

### Step 1: Health Check
```
🏥 Health & Status → Health Check
```
**Expected**: 200 OK with database status

### Step 2: Create Test Users

#### 2.1 Register Customer
```
👤 Authentication → Register Customer
```
**Expected**: 201 Created with user ID and token
**Action**: Copy the token and set as `customerToken` variable

#### 2.2 Register Technician  
```
👤 Authentication → Register Technician
```
**Expected**: 201 Created with user ID and token
**Action**: Copy the token and set as `technicianToken` variable

#### 2.3 Login Users
```
👤 Authentication → Login (for each user type)
```
**Expected**: 200 OK with user data and token

### Step 3: Test User Management

#### 3.1 Get User Profile
```
👥 User Management → Get User Profile
```
**Expected**: 200 OK with user profile data

#### 3.2 Update User Profile
```
👥 User Management → Update User Profile
```
**Expected**: 200 OK with updated profile

### Step 4: Test Services

#### 4.1 Get All Services
```
🔧 Services → Get All Services
```
**Expected**: 200 OK with array of services
**Action**: Copy a service ID and set as `serviceId` variable

#### 4.2 Create Service Request
```
🔧 Services → Create Service Request
```
**Expected**: 201 Created with request ID
**Action**: Copy the request ID and set as `requestId` variable

#### 4.3 Get My Requests
```
🔧 Services → Get My Service Requests
```
**Expected**: 200 OK with user's requests

### Step 5: Test Technician Features

#### 5.1 Get Nearby Technicians
```
🔧 Technicians → Get Nearby Technicians
```
**Expected**: 200 OK with nearby technicians

#### 5.2 Update Technician Location
```
🔧 Technicians → Update Technician Location
```
**Expected**: 200 OK with updated location

### Step 6: Test Dashboards

#### 6.1 Customer Dashboard
```
📊 Dashboard → Customer Dashboard
```
**Expected**: 200 OK with customer statistics

#### 6.2 Technician Dashboard
```
📊 Dashboard → Technician Dashboard
```
**Expected**: 200 OK with technician statistics

### Step 7: Test Synchronization (Admin Only)

#### 7.1 Sync Status
```
🔄 Synchronization → Sync Status
```
**Expected**: 200 OK with sync statistics

#### 7.2 Full Sync
```
🔄 Synchronization → Full Sync
```
**Expected**: 200 OK with sync results

## 🎯 Key Tests for Data Insertion

### ✅ User Registration → Database
1. **Request**: `POST /api/auth/register`
2. **Check**: User appears in database
3. **Verify**: `syncStatus` field exists

### ✅ Service Request → Database  
1. **Request**: `POST /api/services/request`
2. **Check**: Request appears in database
3. **Verify**: Relationships are correct

### ✅ Profile Update → Database
1. **Request**: `PUT /api/users/profile`
2. **Check**: Updates reflected in database
3. **Verify**: Sync status updated

## 📊 Expected Response Examples

### Health Check Response:
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

### User Registration Response:
```json
{
  "message": "User registered successfully",
  "user": {
    "id": "507f1f77bcf86cd799439011",
    "name": "John Customer",
    "email": "customer1712345678901@example.com",
    "role": "customer"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Service Request Response:
```json
{
  "_id": "507f1f77bcf86cd799439012",
  "customerId": "507f1f77bcf86cd799439011",
  "serviceId": "507f1f77bcf86cd799439013",
  "status": "pending",
  "totalAmount": 150,
  "notes": "Need urgent plumbing repair",
  "serviceLocation": {
    "address": "456 Oak Ave, New York, NY",
    "latitude": 40.7580,
    "longitude": -73.9855
  }
}
```

## 🔍 Database Verification

After running API tests, verify data in MongoDB:

```bash
# Connect to MongoDB
mongo geekonsites

# Check users
db.users.find({email: /customer/}).pretty()

# Check service requests  
db.servicerequests.find({notes: /urgent/}).pretty()

# Check sync status
db.users.find({}, {name: 1, email: 1, syncStatus: 1, zohoId: 1})

# Check relationships
db.servicerequests.find().populate('customerId serviceId')
```

## 🚨 Troubleshooting

### Server Not Running
```bash
npm run dev
```

### Authentication Issues
- Check token is set correctly in variables
- Verify token hasn't expired
- Ensure correct user role for endpoints

### Database Connection Issues
```bash
npm run test-db
```

### Sync Issues
- Check Zoho API credentials in .env
- Verify network connectivity
- Check sync status endpoint

## 📈 Performance Testing

### Response Time Testing
1. Use Postman's **Performance** tab
2. Monitor response times for each endpoint
3. Check database query performance

### Load Testing
1. Use Postman's **Runner**
2. Create iterations with different data
3. Monitor database performance under load

## 🎉 Success Criteria

✅ **All endpoints return 200/201 status codes**  
✅ **Data appears in MongoDB collections**  
✅ **Relationships are maintained correctly**  
✅ **Authentication works for all user types**  
✅ **Sync status is tracked properly**  
✅ **Error handling works correctly**  

## 📝 Notes

- The collection automatically generates unique emails using timestamp
- Variables need to be set manually after authentication
- Some endpoints require specific user roles
- Sync to Zoho may show errors due to API URL format issues
- All data is safely stored in MongoDB regardless of Zoho sync status

This comprehensive Postman collection will help you thoroughly test all API functionality and verify data insertion into the database! 🚀
