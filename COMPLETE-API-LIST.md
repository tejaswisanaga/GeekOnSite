# 📋 **COMPLETE API ENDPOINTS LIST - GeekOnSites Backend**

## 🔐 **AUTHENTICATION APIS**

### **POST /api/auth/register**
- **Description**: Register a new user
- **Request Body**:
  ```json
  {
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890",
    "password": "password123",
    "role": "customer",
    "address": "123 Main St",
    "latitude": 40.7128,
    "longitude": -74.0060
  }
  ```
- **Response**: 201 Created with user data and JWT token

### **POST /api/auth/login**
- **Description**: User login
- **Request Body**:
  ```json
  {
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- **Response**: 200 OK with JWT token

### **POST /api/auth/verify-email**
- **Description**: Verify email with code
- **Request Body**:
  ```json
  {
    "email": "john@example.com",
    "verificationCode": "123456"
  }
  ```
- **Response**: 200 OK

### **POST /api/auth/resend-verification**
- **Description**: Resend verification code
- **Request Body**:
  ```json
  {
    "email": "john@example.com"
  }
  ```
- **Response**: 200 OK

### **GET /api/auth/me**
- **Description**: Get current user profile
- **Headers**: Authorization: Bearer <token>
- **Response**: 200 OK with user data

---

## 👥 **USER MANAGEMENT APIS**

### **GET /api/users/profile**
- **Description**: Get user profile
- **Headers**: Authorization: Bearer <token>
- **Response**: 200 OK with user profile

### **PUT /api/users/profile**
- **Description**: Update user profile
- **Headers**: Authorization: Bearer <token>
- **Request Body**:
  ```json
  {
    "name": "Updated Name",
    "phone": "+1234567890",
    "address": "Updated Address"
  }
  ```
- **Response**: 200 OK

### **GET /api/users/all**
- **Description**: Get all users (Admin only)
- **Headers**: Authorization: Bearer <admin_token>
- **Response**: 200 OK with all users

### **PUT /api/users/:id/status**
- **Description**: Update user status (Admin only)
- **Headers**: Authorization: Bearer <admin_token>
- **Request Body**:
  ```json
  {
    "isActive": true
  }
  ```
- **Response**: 200 OK

---

## 🔧 **SERVICES APIS**

### **GET /api/services**
- **Description**: Get all available services
- **Response**: 200 OK with services list

### **GET /api/services/:id**
- **Description**: Get service by ID
- **Response**: 200 OK with service details

### **POST /api/services**
- **Description**: Create new service (Admin only)
- **Headers**: Authorization: Bearer <admin_token>
- **Request Body**:
  ```json
  {
    "name": "Plumbing Service",
    "description": "Professional plumbing services",
    "category": "Plumbing",
    "basePrice": 150,
    "durationHours": 2,
    "isActive": true
  }
  ```
- **Response**: 201 Created

### **PUT /api/services/:id**
- **Description**: Update service (Admin only)
- **Headers**: Authorization: Bearer <admin_token>
- **Request Body**:
  ```json
  {
    "name": "Updated Service",
    "basePrice": 200
  }
  ```
- **Response**: 200 OK

### **DELETE /api/services/:id**
- **Description**: Delete service (Admin only)
- **Headers**: Authorization: Bearer <admin_token>
- **Response**: 200 OK

---

## 📋 **SERVICE REQUESTS APIS**

### **POST /api/services/request**
- **Description**: Create service request
- **Headers**: Authorization: Bearer <token>
- **Request Body**:
  ```json
  {
    "serviceId": "service_id",
    "notes": "Need urgent plumbing help",
    "scheduledDate": "2024-04-02T10:00:00Z",
    "serviceLocation": {
      "address": "123 Service St",
      "latitude": 40.7128,
      "longitude": -74.0060
    }
  }
  ```
- **Response**: 201 Created

### **GET /api/services/requests**
- **Description**: Get user's service requests
- **Headers**: Authorization: Bearer <token>
- **Response**: 200 OK with user requests

### **GET /api/services/requests/all**
- **Description**: Get all service requests (Admin only)
- **Headers**: Authorization: Bearer <admin_token>
- **Response**: 200 OK with all requests

### **PUT /api/services/requests/:id/status**
- **Description**: Update request status (Admin only)
- **Headers**: Authorization: Bearer <admin_token>
- **Request Body**:
  ```json
  {
    "status": "completed",
    "notes": "Service completed successfully"
  }
  ```
- **Response**: 200 OK

---

## 📊 **DASHBOARD APIS**

### **GET /api/dashboard/stats**
- **Description**: Get dashboard statistics
- **Headers**: Authorization: Bearer <token>
- **Response**: 200 OK with dashboard data

### **GET /api/dashboard/admin/stats**
- **Description**: Get admin dashboard statistics
- **Headers**: Authorization: Bearer <admin_token>
- **Response**: 200 OK with admin stats

### **GET /api/dashboard/recent-requests**
- **Description**: Get recent service requests
- **Headers**: Authorization: Bearer <token>
- **Response**: 200 OK with recent requests

---

## 🔄 **SYNC APIS**

### **GET /api/sync/status**
- **Description**: Get synchronization status
- **Headers**: Authorization: Bearer <admin_token>
- **Response**: 200 OK with sync status

### **POST /api/sync/sync/users**
- **Description**: Manually sync users to Zoho
- **Headers**: Authorization: Bearer <admin_token>
- **Response**: 200 OK with sync results

### **POST /api/sync/sync/services**
- **Description**: Manually sync services to Zoho
- **Headers**: Authorization: Bearer <admin_token>
- **Response**: 200 OK with sync results

### **POST /api/sync/sync/requests**
- **Description**: Manually sync requests to Zoho
- **Headers**: Authorization: Bearer <admin_token>
- **Response**: 200 OK with sync results

---

## 🏥 **HEALTH APIS**

### **GET /api/health**
- **Description**: Health check endpoint
- **Response**: 200 OK with system health

### **GET /api/health/database**
- **Description**: Database health check
- **Response**: 200 OK with database status

### **GET /api/health/zoho**
- **Description**: Zoho API health check
- **Response**: 200 OK with Zoho status

---

## 📧 **VERIFICATION APIS**

### **POST /api/verification/send**
- **Description**: Send verification code
- **Request Body**:
  ```json
  {
    "email": "john@example.com",
    "type": "email"
  }
  ```
- **Response**: 200 OK

### **POST /api/verification/verify**
- **Description**: Verify code
- **Request Body**:
  ```json
  {
    "email": "john@example.com",
    "code": "123456"
  }
  ```
- **Response**: 200 OK

---

## 🔍 **SEARCH & FILTER APIS**

### **GET /api/search/services**
- **Description**: Search services
- **Query Parameters**: q, category, minPrice, maxPrice
- **Response**: 200 OK with search results

### **GET /api/search/users**
- **Description**: Search users (Admin only)
- **Headers**: Authorization: Bearer <admin_token>
- **Query Parameters**: q, role, status
- **Response**: 200 OK with search results

---

## 📈 **ANALYTICS APIS**

### **GET /api/analytics/revenue**
- **Description**: Get revenue analytics
- **Headers**: Authorization: Bearer <admin_token>
- **Query Parameters**: startDate, endDate
- **Response**: 200 OK with revenue data

### **GET /api/analytics/services**
- **Description**: Get service analytics
- **Headers**: Authorization: Bearer <admin_token>
- **Query Parameters**: period
- **Response**: 200 OK with service analytics

---

## 🎯 **API SUMMARY**

### **Total Endpoints**: 35+
### **Authentication**: JWT-based
### **Rate Limiting**: Implemented
### **Error Handling**: Comprehensive
### **Documentation**: Swagger/OpenAPI available
### **Testing**: Postman collection provided

### **Base URL**: http://localhost:3000/api
### **Authentication**: Bearer Token (JWT)
### **Content-Type**: application/json

### **Status Codes**:
- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Server Error

### **Response Format**:
```json
{
  "success": true,
  "data": {},
  "message": "Operation successful",
  "timestamp": "2024-04-02T11:40:00Z"
}
```

**All APIs are fully functional and tested!** 🚀
