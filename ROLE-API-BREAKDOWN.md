# 🎯 **ROLE-BASED API ENDPOINTS WITH ACCESS LEVELS**

| Role | Endpoints | Access Level | Specific APIs |
|------|-----------|--------------|---------------|
| **Admin** | 25+ | Full system access | Complete system control |
| **Technician** | 12+ | Service management | Service request management |
| **Customer** | 15+ | Service booking | Service browsing and booking |

---

## 👨‍💼 **ADMIN - 25+ ENDPOINTS (Full System Access)**

### **Authentication & User Management (7)**
- `POST /api/auth/register` - Register any user
- `POST /api/auth/login` - Admin login
- `GET /api/auth/me` - Get admin profile
- `POST /api/auth/verify-email` - Verify email
- `POST /api/auth/resend-verification` - Resend verification
- `GET /api/users/all` - Get all users
- `PUT /api/users/:id/status` - Update user status

### **Service Management (5)**
- `GET /api/services` - Get all services
- `GET /api/services/:id` - Get service details
- `POST /api/services` - Create new service
- `PUT /api/services/:id` - Update service
- `DELETE /api/services/:id` - Delete service

### **Service Request Management (4)**
- `GET /api/services/requests/all` - Get all requests
- `PUT /api/services/requests/:id/status` - Update status
- `GET /api/services/requests` - Get specific request
- `POST /api/services/request` - Create service request

### **Dashboard & Analytics (5)**
- `GET /api/dashboard/admin/stats` - Admin dashboard stats
- `GET /api/dashboard/stats` - General dashboard stats
- `GET /api/dashboard/recent-requests` - Recent requests
- `GET /api/analytics/revenue` - Revenue analytics
- `GET /api/analytics/services` - Service analytics

### **System Management (4)**
- `GET /api/sync/status` - Get sync status
- `POST /api/sync/sync/users` - Sync users to Zoho
- `POST /api/sync/sync/services` - Sync services to Zoho
- `POST /api/sync/sync/requests` - Sync requests to Zoho

---

## 🛠️ **TECHNICIAN - 12+ ENDPOINTS (Service Management)**

### **Authentication & Profile (4)**
- `POST /api/auth/login` - Technician login
- `GET /api/auth/me` - Get technician profile
- `POST /api/auth/verify-email` - Verify email
- `POST /api/auth/resend-verification` - Resend verification

### **Profile Management (2)**
- `GET /api/users/profile` - Get technician profile
- `PUT /api/users/profile` - Update technician profile

### **Service Operations (2)**
- `GET /api/services` - Get all services
- `GET /api/services/:id` - Get service details

### **Service Request Management (3)**
- `GET /api/services/requests` - Get assigned requests
- `PUT /api/services/requests/:id/status` - Update request status
- `GET /api/services/requests/:id` - Get request details

### **Dashboard (1)**
- `GET /api/dashboard/stats` - Technician dashboard stats

---

## 👤 **CUSTOMER - 15+ ENDPOINTS (Service Booking)**

### **Authentication & Profile (5)**
- `POST /api/auth/register` - Customer registration
- `POST /api/auth/login` - Customer login
- `GET /api/auth/me` - Get customer profile
- `POST /api/auth/verify-email` - Verify email
- `POST /api/auth/resend-verification` - Resend verification

### **Profile Management (2)**
- `GET /api/users/profile` - Get customer profile
- `PUT /api/users/profile` - Update customer profile

### **Service Browsing (3)**
- `GET /api/services` - Get all available services
- `GET /api/services/:id` - Get service details
- `GET /api/search/services` - Search services

### **Service Request Management (3)**
- `POST /api/services/request` - Create service request
- `GET /api/services/requests` - Get customer's requests
- `GET /api/services/requests/:id` - Get request details

### **Dashboard & Verification (2)**
- `GET /api/dashboard/stats` - Customer dashboard
- `POST /api/verification/send` - Send verification code

---

## 🔐 **ROLE PERMISSIONS MATRIX**

| Feature | Admin | Technician | Customer |
|---------|-------|------------|----------|
| **User Management** | ✅ Full | ❌ Limited | ❌ Own only |
| **Service Management** | ✅ Full | ✅ Read only | ✅ Read only |
| **Request Management** | ✅ All | ✅ Assigned | ✅ Own only |
| **Analytics** | ✅ Full | ❌ Limited | ❌ Basic |
| **System Admin** | ✅ Full | ❌ No | ❌ No |
| **Sync Management** | ✅ Full | ❌ No | ❌ No |

---

## 📊 **ENDPOINT BREAKDOWN BY CATEGORY**

### **Authentication APIs**
- Admin: 5 endpoints
- Technician: 4 endpoints  
- Customer: 5 endpoints

### **User Management APIs**
- Admin: 2 endpoints
- Technician: 2 endpoints
- Customer: 2 endpoints

### **Service APIs**
- Admin: 5 endpoints
- Technician: 2 endpoints
- Customer: 3 endpoints

### **Request APIs**
- Admin: 4 endpoints
- Technician: 3 endpoints
- Customer: 3 endpoints

### **Dashboard APIs**
- Admin: 5 endpoints
- Technician: 1 endpoint
- Customer: 1 endpoint

### **System APIs**
- Admin: 4 endpoints
- Technician: 0 endpoints
- Customer: 0 endpoints

---

## 🎯 **ACCESS LEVEL DEFINITIONS**

### **Full System Access (Admin)**
- Complete CRUD operations on all entities
- User management and permissions
- System configuration and settings
- Analytics and reporting
- Database and sync management

### **Service Management (Technician)**
- Read access to services
- Manage assigned service requests
- Update request status and notes
- Profile management
- Basic dashboard access

### **Service Booking (Customer)**
- Browse and search services
- Create and manage own requests
- Profile management
- Basic dashboard access
- Email verification

---

## 🚀 **USAGE EXAMPLES BY ROLE**

### **Admin Example**
```bash
# Create new service
POST /api/services
Authorization: Bearer <admin_token>

# Get all users
GET /api/users/all
Authorization: Bearer <admin_token>

# View analytics
GET /api/analytics/revenue
Authorization: Bearer <admin_token>
```

### **Technician Example**
```bash
# Get assigned requests
GET /api/services/requests
Authorization: Bearer <technician_token>

# Update request status
PUT /api/services/requests/123/status
Authorization: Bearer <technician_token>
```

### **Customer Example**
```bash
# Browse services
GET /api/services
Authorization: Bearer <customer_token>

# Create service request
POST /api/services/request
Authorization: Bearer <customer_token>
```

**All role-based APIs are fully implemented and functional!** 🎯
