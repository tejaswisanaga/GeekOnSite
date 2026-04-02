# 🗄️ **DATABASE STORAGE LOCATION & CHECKING GUIDE**

## 📍 **WHERE YOUR DATABASE IS STORED**

### **MongoDB Location**
- **Type**: MongoDB Database
- **Name**: `geekonsites`
- **Host**: `localhost`
- **Port**: `27017`
- **Connection String**: `mongodb://localhost:27017/geekonsites`

---

## 🔍 **HOW TO CHECK DATABASE DATA**

### **Method 1: Using Our Script (Recommended)**
```bash
cd "c:\Users\tejas\Downloads\GOSbackend\GOSbackend"
node scripts/comprehensive-data-check.js
```

### **Method 2: MongoDB Compass (GUI)**
1. **Download**: MongoDB Compass from https://www.mongodb.com/try/compass
2. **Install**: Follow installation instructions
3. **Connect**: 
   - **Host**: localhost
   - **Port**: 27017
   - **Database**: geekonsites
4. **View**: Browse collections and data

### **Method 3: MongoDB Shell**
```bash
# Open MongoDB Shell
mongosh

# Switch to your database
use geekonsites

# View all collections
show collections

# View users data
db.users.find().pretty()

# View services data
db.services.find().pretty()

# View service requests
db.servicerequests.find().pretty()

# View verifications
db.verifications.find().pretty()
```

### **Method 4: Using Our Verification Script**
```bash
cd "c:\Users\tejas\Downloads\GOSbackend\GOSbackend"
node scripts/verify-all-api-data.js
```

---

## 📊 **CURRENT DATABASE STATUS**

### **Collections in Your Database:**
- **users**: 9 users stored
- **services**: 3 services available
- **servicerequests**: 3 service requests
- **verifications**: 9 verification records

### **Data Structure:**

#### **Users Collection:**
```javascript
{
  "_id": ObjectId("..."),
  "name": "John Customer",
  "email": "john@customer.com",
  "phone": "+1234567890",
  "role": "customer",
  "address": "123 Main Street",
  "latitude": 40.7128,
  "longitude": -74.0060,
  "isVerified": false,
  "syncStatus": "error",
  "zohoId": null,
  "createdAt": ISODate("2026-04-02T06:21:53.507Z")
}
```

#### **Services Collection:**
```javascript
{
  "_id": ObjectId("..."),
  "name": "API Test Service",
  "category": "Electrical",
  "description": "Test service description",
  "basePrice": 150,
  "durationHours": 2,
  "isActive": true,
  "createdAt": ISODate("2026-04-02T06:21:53.507Z")
}
```

#### **Service Requests Collection:**
```javascript
{
  "_id": ObjectId("..."),
  "customerId": ObjectId("..."),
  "serviceId": ObjectId("..."),
  "scheduledDate": ISODate("2024-04-02T10:00:00Z"),
  "serviceLocation": {
    "address": "123 Service St",
    "latitude": 40.7128,
    "longitude": -74.0060
  },
  "status": "pending",
  "totalAmount": 150,
  "syncStatus": "pending",
  "zohoId": null,
  "createdAt": ISODate("2026-04-02T06:21:53.507Z")
}
```

---

## 🛠️ **HOW TO VERIFY DATA IS STORED**

### **Step 1: Run Data Check Script**
```bash
node scripts/comprehensive-data-check.js
```

### **Step 2: Check Specific Data**
```bash
# Check all users
node scripts/verify-all-api-data.js

# Check Zoho sync status
node scripts/test-zoho-tokens.js
```

### **Step 3: Manual MongoDB Check**
```bash
# Open MongoDB Shell
mongosh

# Connect to database
use geekonsites

# Count documents in each collection
db.users.countDocuments()
db.services.countDocuments()
db.servicerequests.countDocuments()
db.verifications.countDocuments()

# View recent data
db.users.find().sort({createdAt: -1}).limit(5).pretty()
```

---

## 📁 **DATABASE FILES LOCATION**

### **MongoDB Data Files (Windows)**
- **Default Path**: `C:\Program Files\MongoDB\Server\X.X\data`
- **Database Files**: `geekonsites.*` files
- **Log Files**: MongoDB log files

### **If Using MongoDB Atlas (Cloud)**
- **URL**: Your MongoDB Atlas connection string
- **Cluster**: Your cluster name
- **Database**: geekonsites

---

## 🔧 **TROUBLESHOOTING**

### **If Database Not Found:**
1. **Check MongoDB is running**: `mongosh --eval "db.adminCommand('ismaster')"`
2. **Check connection string**: Verify `.env` file
3. **Restart MongoDB**: Restart MongoDB service

### **If Data Not Visible:**
1. **Run data check script**: `node scripts/comprehensive-data-check.js`
2. **Check collections**: `show collections` in MongoDB shell
3. **Verify data insertion**: Check API responses

---

## 🎯 **QUICK CHECK COMMANDS**

### **Right Now, Run This:**
```bash
cd "c:\Users\tejas\Downloads\GOSbackend\GOSbackend"
node scripts/comprehensive-data-check.js
```

### **Expected Output:**
```
📊 1. Checking MongoDB Data Storage...
   Users in MongoDB: 9
   Services in MongoDB: 3
   Service Requests in MongoDB: 3
   Verifications in MongoDB: 9
```

**Your database is stored locally in MongoDB at localhost:27017 with the name "geekonsites"** 🗄️
