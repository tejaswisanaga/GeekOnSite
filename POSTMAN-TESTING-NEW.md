# 🧪 **POSTMAN TESTING GUIDE - Database & Zoho Integration**

## 🎯 **TESTING OBJECTIVES**
1. ✅ Test API endpoints in Postman
2. ✅ Verify data storage in MongoDB
3. ✅ Check Zoho Creator forms integration
4. ✅ Validate automatic data insertion

---

## 📋 **POSTMAN COLLECTION SETUP**

### **Base URL**: http://localhost:3000
### **Content-Type**: application/json
### **Authorization**: Bearer Token (JWT)

---

## 🔍 **TEST 1: DATABASE STORAGE VERIFICATION**

### **Step 1: Create Customer**
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "Postman Test Customer",
  "email": "postmantest@example.com",
  "phone": "+1234567890",
  "password": "password123",
  "role": "customer",
  "address": "123 Postman Street",
  "latitude": 40.7128,
  "longitude": -74.0060
}
```

### **Expected Response**:
```json
{
  "success": true,
  "message": "User registered successfully",
  "user": {
    "id": "user_id",
    "name": "Postman Test Customer",
    "email": "postmantest@example.com",
    "role": "customer"
  }
}
```

### **Step 2: Login Customer**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "postmantest@example.com",
  "password": "password123"
}
```

### **Step 3: Create Service Booking**
```http
POST /api/bookings/create
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "serviceId": "service_id",
  "category": "laptop_computer",
  "scheduledDate": "2024-04-02T10:00:00Z",
  "serviceLocation": {
    "address": "123 Postman Street",
    "latitude": 40.7128,
    "longitude": -74.0060,
    "notes": "Please call when arriving"
  },
  "issueDescription": "Laptop is running very slow and making strange noises",
  "urgency": "medium"
}
```

---

## 🗄️ **TEST 2: MONGODB VERIFICATION**

### **Check MongoDB Data**
After creating the booking, run this script to verify MongoDB storage:

```bash
node scripts/comprehensive-data-check.js
```

### **Expected MongoDB Results**:
- ✅ **Users**: Should show Postman Test Customer
- ✅ **Service Requests**: Should show new booking
- ✅ **Sync Status**: Should show pending for Zoho

---

## 📊 **TEST 3: ZOHO CREATOR FORMS CHECK**

### **Step 1: Check Zoho API Status**
```http
GET /api/health/zoho
```

### **Step 2: Manual Zoho Creator Check**
1. **Go to**: https://creator.zoho.com
2. **Login**: with your credentials
3. **Navigate**: Applications → GeekOnSites
4. **Check Forms**:
   - Users form
   - Service_Requests form
   - Services form

### **Expected Zoho Results**:
- ✅ **Users form**: Should contain Postman Test Customer
- ✅ **Service_Requests form**: Should contain booking data
- ✅ **Services form**: Should contain available services

---

## 🔧 **TEST 4: AUTOMATIC DATA INSERTION**

### **Test Auto-Insert Process**
```http
POST /api/sync/sync/users
Authorization: Bearer <admin_token>
```

```http
POST /api/sync/sync/requests
Authorization: Bearer <admin_token>
```

---

## 📋 **POSTMAN TEST SEQUENCE**

### **1. Environment Setup**
- **Base URL**: http://localhost:3000
- **Variables**: token, user_id, booking_id

### **2. Test Flow**
1. **Register Customer** → Get user_id
2. **Login Customer** → Get token
3. **Browse Services** → Get service_id
4. **Create Booking** → Get booking_id
5. **Check Dashboard** → Verify booking
6. **Check Database** → Verify MongoDB
7. **Check Zoho** → Verify forms

---

## 🎯 **EXPECTED RESULTS**

### **✅ Success Scenario**:
1. **Postman Tests**: All APIs return 200/201
2. **MongoDB**: Data stored in collections
3. **Zoho Forms**: Data visible in Creator
4. **Auto-Insert**: Data flows automatically

### **⚠️ Alternative Scenario**:
1. **Postman Tests**: APIs work fine
2. **MongoDB**: Data stored correctly
3. **Zoho Forms**: Data not visible (API access issue)
4. **Auto-Insert**: Queued for later sync

---

## 🚀 **QUICK START**

### **1. Import Collection**
```json
{
  "info": {
    "name": "GeekOnSites API Test",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:3000"
    },
    {
      "key": "token",
      "value": ""
    }
  ]
}
```

### **2. Run Tests**
1. **Start with**: Health check
2. **Register**: Test customer
3. **Login**: Get token
4. **Book**: Create service request
5. **Verify**: Check database and Zoho

---

## 📊 **TESTING CHECKLIST**

- [ ] Server is running on localhost:3000
- [ ] Database connection is healthy
- [ ] Customer registration works
- [ ] Customer login works
- [ ] Service booking works
- [ ] MongoDB data is stored
- [ ] Zoho Creator forms are checked
- [ ] Auto-insert process is verified

**Ready to test in Postman!** 🚀
