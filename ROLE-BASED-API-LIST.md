# 🎯 **ROLE-BASED API ENDPOINTS - GeekOnSites Backend**

## 👨‍💼 **ADMIN APIS** (Full Access)

### **Authentication**
- `POST /api/auth/register` - Register any user
- `POST /api/auth/login` - Admin login
- `GET /api/auth/me` - Get admin profile
- `POST /api/auth/resend-verification` - Resend verification

### **User Management**
- `GET /api/users/all` - Get all users
- `PUT /api/users/:id/status` - Update user status (activate/deactivate)
- `GET /api/users/profile` - Get any user profile
- `PUT /api/users/profile` - Update any user profile

### **Service Management**
- `GET /api/services` - Get all services
- `GET /api/services/:id` - Get service details
- `POST /api/services` - Create new service
- `PUT /api/services/:id` - Update service
- `DELETE /api/services/:id` - Delete service

### **Service Request Management**
- `GET /api/services/requests/all` - Get all service requests
- `PUT /api/services/requests/:id/status` - Update request status
- `GET /api/services/requests` - Get specific request
- `POST /api/services/request` - Create service request

### **Dashboard & Analytics**
- `GET /api/dashboard/admin/stats` - Admin dashboard statistics
- `GET /api/dashboard/stats` - General dashboard stats
- `GET /api/dashboard/recent-requests` - Recent requests
- `GET /api/analytics/revenue` - Revenue analytics
- `GET /api/analytics/services` - Service analytics

### **Sync Management**
- `GET /api/sync/status` - Get sync status
- `POST /api/sync/sync/users` - Sync users to Zoho
- `POST /api/sync/sync/services` - Sync services to Zoho
- `POST /api/sync/sync/requests` - Sync requests to Zoho

### **Search & Verification**
- `GET /api/search/services` - Search services
- `GET /api/search/users` - Search users
- `POST /api/verification/send` - Send verification
- `POST /api/verification/verify` - Verify code

### **System Health**
- `GET /api/health` - System health
- `GET /api/health/database` - Database health
- `GET /api/health/zoho` - Zoho API health

---

## 🛠️ **TECHNICIAN APIS** (Service Provider Access)

### **Authentication**
- `POST /api/auth/login` - Technician login
- `GET /api/auth/me` - Get technician profile
- `POST /api/auth/verify-email` - Verify email
- `POST /api/auth/resend-verification` - Resend verification

### **Profile Management**
- `GET /api/users/profile` - Get technician profile
- `PUT /api/users/profile` - Update technician profile

### **Service Management**
- `GET /api/services` - Get all services
- `GET /api/services/:id` - Get service details

### **Service Request Management**
- `GET /api/services/requests` - Get assigned service requests
- `PUT /api/services/requests/:id/status` - Update request status
- `GET /api/services/requests/:id` - Get request details

### **Dashboard**
- `GET /api/dashboard/stats` - Technician dashboard stats
- `GET /api/dashboard/recent-requests` - Recent assigned requests

### **Search**
- `GET /api/search/services` - Search services

### **Health**
- `GET /api/health` - System health

---

## 👤 **CUSTOMER APIS** (Service User Access)

### **Authentication**
- `POST /api/auth/register` - Customer registration
- `POST /api/auth/login` - Customer login
- `GET /api/auth/me` - Get customer profile
- `POST /api/auth/verify-email` - Verify email
- `POST /api/auth/resend-verification` - Resend verification

### **Profile Management**
- `GET /api/users/profile` - Get customer profile
- `PUT /api/users/profile` - Update customer profile

### **Service Browsing**
- `GET /api/services` - Get all available services
- `GET /api/services/:id` - Get service details
- `GET /api/search/services` - Search services

### **Service Request Management**
- `POST /api/services/request` - Create service request
- `GET /api/services/requests` - Get customer's requests
- `GET /api/services/requests/:id` - Get request details

### **Dashboard**
- `GET /api/dashboard/stats` - Customer dashboard
- `GET /api/dashboard/recent-requests` - Customer's recent requests

### **Verification**
- `POST /api/verification/send` - Send verification code
- `POST /api/verification/verify` - Verify email code

### **Health**
- `GET /api/health` - System health

---

## 🔐 **ROLE-BASED ACCESS CONTROL**

### **Admin Role Permissions**
```javascript
{
  "role": "admin",
  "permissions": [
    "user:read_all",
    "user:write_all",
    "service:read_all",
    "service:write_all",
    "request:read_all", 
    "request:write_all",
    "analytics:read",
    "sync:manage",
    "system:admin"
  ]
}
```

### **Technician Role Permissions**
```javascript
{
  "role": "technician",
  "permissions": [
    "user:read_own",
    "user:write_own",
    "service:read",
    "request:read_assigned",
    "request:write_status",
    "dashboard:technician"
  ]
}
```

### **Customer Role Permissions**
```javascript
{
  "role": "customer",
  "permissions": [
    "user:read_own",
    "user:write_own",
    "service:read",
    "request:read_own",
    "request:write_own",
    "dashboard:customer"
  ]
}
```

---

## 📋 **API ENDPOINTS BY ROLE SUMMARY**

### **Admin APIs (25+ endpoints)**
- Full system access
- User management
- Service management
- Analytics and reporting
- System administration

### **Technician APIs (12+ endpoints)**
- Service request management
- Profile management
- Service browsing
- Status updates

### **Customer APIs (15+ endpoints)**
- Service browsing and booking
- Profile management
- Request tracking
- Dashboard access

---

## 🚀 **USAGE EXAMPLES**

### **Admin Usage**
```bash
# Create new service
POST /api/services
Authorization: Bearer <admin_token>
{
  "name": "Electrical Repair",
  "basePrice": 200,
  "category": "Electrical"
}

# Get all users
GET /api/users/all
Authorization: Bearer <admin_token>
```

### **Technician Usage**
```bash
# Update request status
PUT /api/services/requests/123/status
Authorization: Bearer <technician_token>
{
  "status": "in_progress",
  "notes": "On the way to location"
}
```

### **Customer Usage**
```bash
# Create service request
POST /api/services/request
Authorization: Bearer <customer_token>
{
  "serviceId": "service_id",
  "notes": "Need plumbing help",
  "serviceLocation": {
    "address": "123 Main St"
  }
}
```

---

## 🎯 **ROLE IMPLEMENTATION**

### **Middleware for Role-Based Access**
```javascript
// Admin middleware
const requireAdmin = (req, res, next) => {
  if (req.user.role !== 'admin') {
    return res.status(403).json({ message: 'Admin access required' });
  }
  next();
};

// Technician middleware
const requireTechnician = (req, res, next) => {
  if (!['admin', 'technician'].includes(req.user.role)) {
    return res.status(403).json({ message: 'Technician access required' });
  }
  next();
};
```

**All role-based APIs are fully implemented and functional!** 🎯
