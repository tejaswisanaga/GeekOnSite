# 🖥️ **LAPTOP & COMPUTER SERVICES API SYSTEM**

## 🎯 **SERVICE CATEGORIES**
- **Laptop and Computer Services**
- **Printer and Document Solutions**
- **Network and Wi-Fi Solutions**
- **Security and CCTV Solutions**
- **Software and Support Solutions**
- **Smart Home and Appliances**

---

## 👤 **CUSTOMER LOGIN & DASHBOARD APIS**

### **POST /api/auth/customer/login**
- **Description**: Customer login for service booking
- **Request Body**:
  ```json
  {
    "email": "customer@example.com",
    "password": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "success": true,
    "data": {
      "user": {
        "id": "user_id",
        "name": "John Doe",
        "email": "customer@example.com",
        "role": "customer",
        "phone": "+1234567890",
        "address": "123 Main St"
      },
      "token": "jwt_token_here"
    },
    "message": "Login successful"
  }
  ```

### **GET /api/customer/dashboard**
- **Description**: Customer dashboard with service bookings
- **Headers**: Authorization: Bearer <customer_token>
- **Response**:
  ```json
  {
    "success": true,
    "data": {
      "profile": {
        "name": "John Doe",
        "email": "customer@example.com",
        "phone": "+1234567890",
        "totalBookings": 5,
        "activeBookings": 2
      },
      "recentBookings": [
        {
          "id": "booking_id",
          "service": "Laptop Repair",
          "category": "Laptop and Computer Services",
          "status": "pending",
          "scheduledDate": "2024-04-02T10:00:00Z",
          "technician": "Technician Name",
          "amount": 150
        }
      ],
      "availableServices": [
        {
          "id": "service_id",
          "name": "Laptop Repair",
          "category": "Laptop and Computer Services",
          "basePrice": 150,
          "duration": "2 hours",
          "description": "Professional laptop repair services"
        }
      ]
    }
  }
  ```

---

## 🛠️ **TECHNICIAN LOGIN & DASHBOARD APIS**

### **POST /api/auth/technician/login**
- **Description**: Technician login for service management
- **Request Body**:
  ```json
  {
    "email": "technician@example.com",
    "password": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "success": true,
    "data": {
      "user": {
        "id": "tech_id",
        "name": "Tech Expert",
        "email": "technician@example.com",
        "role": "technician",
        "specialization": ["Laptop Repair", "Network Setup"],
        "rating": 4.8
      },
      "token": "jwt_token_here"
    },
    "message": "Login successful"
  }
  ```

### **GET /api/technician/dashboard**
- **Description**: Technician dashboard with assigned jobs
- **Headers**: Authorization: Bearer <technician_token>
- **Response**:
  ```json
  {
    "success": true,
    "data": {
      "profile": {
        "name": "Tech Expert",
        "specialization": ["Laptop Repair", "Network Setup"],
        "rating": 4.8,
        "completedJobs": 45,
        "activeJobs": 3
      },
      "assignedJobs": [
        {
          "id": "job_id",
          "customer": "John Doe",
          "service": "Laptop Repair",
          "category": "Laptop and Computer Services",
          "status": "assigned",
          "scheduledDate": "2024-04-02T10:00:00Z",
          "location": "123 Main St",
          "amount": 150,
          "notes": "Customer reports slow performance"
        }
      ],
      "todaySchedule": [
        {
          "time": "10:00 AM",
          "customer": "John Doe",
          "service": "Laptop Repair",
          "location": "123 Main St"
        }
      ]
    }
  }
  ```

---

## 👨‍💼 **ADMIN LOGIN & USER MANAGEMENT APIS**

### **POST /api/auth/admin/login**
- **Description**: Admin login for system management
- **Request Body**:
  ```json
  {
    "email": "admin@example.com",
    "password": "adminpassword"
  }
  ```
- **Response**:
  ```json
  {
    "success": true,
    "data": {
      "user": {
        "id": "admin_id",
        "name": "System Admin",
        "email": "admin@example.com",
        "role": "admin",
        "permissions": ["full_access"]
      },
      "token": "jwt_token_here"
    },
    "message": "Login successful"
  }
  ```

### **GET /api/admin/users**
- **Description**: Get all users (customers and technicians)
- **Headers**: Authorization: Bearer <admin_token>
- **Response**:
  ```json
  {
    "success": true,
    "data": {
      "users": [
        {
          "id": "user_id",
          "name": "John Doe",
          "email": "customer@example.com",
          "role": "customer",
          "status": "active",
          "totalBookings": 5,
          "joinedDate": "2024-03-01T00:00:00Z"
        },
        {
          "id": "tech_id",
          "name": "Tech Expert",
          "email": "technician@example.com",
          "role": "technician",
          "status": "active",
          "specialization": ["Laptop Repair"],
          "rating": 4.8,
          "completedJobs": 45
        }
      ],
      "pagination": {
        "page": 1,
        "limit": 10,
        "total": 25
      }
    }
  }
  ```

### **POST /api/admin/users**
- **Description**: Create new user (customer or technician)
- **Headers**: Authorization: Bearer <admin_token>
- **Request Body**:
  ```json
  {
    "name": "New User",
    "email": "newuser@example.com",
    "password": "password123",
    "role": "technician",
    "phone": "+1234567890",
    "specialization": ["Laptop Repair", "Network Setup"]
  }
  ```

---

## 📋 **SERVICE BOOKING APIS**

### **GET /api/services/categories**
- **Description**: Get all service categories
- **Response**:
  ```json
  {
    "success": true,
    "data": {
      "categories": [
        {
          "id": "laptop_computer",
          "name": "Laptop and Computer Services",
          "description": "Professional laptop and computer repair services",
          "services": [
            {
              "id": "laptop_repair",
              "name": "Laptop Repair",
              "basePrice": 150,
              "duration": "2 hours",
              "description": "Diagnose and fix laptop issues"
            },
            {
              "id": "computer_setup",
              "name": "Computer Setup",
              "basePrice": 100,
              "duration": "1.5 hours",
              "description": "Setup and configure new computers"
            }
          ]
        },
        {
          "id": "printer_document",
          "name": "Printer and Document Solutions",
          "services": [
            {
              "id": "printer_repair",
              "name": "Printer Repair",
              "basePrice": 80,
              "duration": "1 hour"
            }
          ]
        }
      ]
    }
  }
  ```

### **POST /api/bookings/create**
- **Description**: Customer creates service booking
- **Headers**: Authorization: Bearer <customer_token>
- **Request Body**:
  ```json
  {
    "serviceId": "laptop_repair",
    "category": "laptop_computer",
    "scheduledDate": "2024-04-02T10:00:00Z",
    "serviceLocation": {
      "address": "123 Main St, Apt 4B",
      "latitude": 40.7128,
      "longitude": -74.0060,
      "notes": "Please call when arriving"
    },
    "issueDescription": "Laptop is running very slow and making strange noises",
    "urgency": "medium",
    "preferredTechnician": "tech_id" // optional
  }
  ```
- **Response**:
  ```json
  {
    "success": true,
    "data": {
      "booking": {
        "id": "booking_id",
        "service": "Laptop Repair",
        "category": "Laptop and Computer Services",
        "status": "pending",
        "scheduledDate": "2024-04-02T10:00:00Z",
        "estimatedDuration": "2 hours",
        "amount": 150,
        "location": "123 Main St, Apt 4B",
        "confirmationCode": "BK123456"
      }
    },
    "message": "Service booking created successfully"
  }
  ```

### **GET /api/bookings/customer/:customerId**
- **Description**: Get customer's booking history
- **Headers**: Authorization: Bearer <customer_token>
- **Response**:
  ```json
  {
    "success": true,
    "data": {
      "bookings": [
        {
          "id": "booking_id",
          "service": "Laptop Repair",
          "category": "Laptop and Computer Services",
          "status": "completed",
          "scheduledDate": "2024-03-15T10:00:00Z",
          "completedDate": "2024-03-15T12:00:00Z",
          "technician": {
            "id": "tech_id",
            "name": "Tech Expert",
            "rating": 4.8
          },
          "amount": 150,
          "rating": 5,
          "feedback": "Excellent service!"
        }
      ]
    }
  }
  ```

### **PUT /api/bookings/:bookingId/status**
- **Description**: Update booking status (technician/admin)
- **Headers**: Authorization: Bearer <technician_token/admin_token>
- **Request Body**:
  ```json
  {
    "status": "in_progress",
    "notes": "Technician is on the way",
    "estimatedArrival": "2024-04-02T09:45:00Z"
  }
  ```

---

## 🔄 **AUTOMATIC DATA INSERTION SYSTEM**

### **POST /api/bookings/auto-insert**
- **Description**: Automatically insert booking data when customer books service
- **Headers**: Authorization: Bearer <system_token>
- **Internal Process**:
  ```json
  {
    "action": "auto_insert",
    "data": {
      "bookingId": "booking_id",
      "customerId": "customer_id",
      "serviceId": "service_id",
      "timestamp": "2024-04-02T10:00:00Z",
      "ipAddress": "customer_ip",
      "userAgent": "browser_info",
      "location": "service_location"
    },
    "insertInto": [
      "mongodb_collection",
      "zoho_creator_form",
      "analytics_database"
    ]
  }
  ```

---

## 📊 **SERVICE-SPECIFIC APIS**

### **Laptop & Computer Services**
- `GET /api/services/laptop-computer` - Get laptop services
- `POST /api/bookings/laptop-repair` - Book laptop repair

### **Printer & Document Solutions**
- `GET /api/services/printer-document` - Get printer services
- `POST /api/bookings/printer-setup` - Book printer setup

### **Network & Wi-Fi Solutions**
- `GET /api/services/network-wifi` - Get network services
- `POST /api/bookings/network-setup` - Book network setup

### **Security & CCTV Solutions**
- `GET /api/services/security-cctv` - Get security services
- `POST /api/bookings/cctv-installation` - Book CCTV installation

### **Software & Support Solutions**
- `GET /api/services/software-support` - Get software services
- `POST /api/bookings/software-installation` - Book software installation

### **Smart Home & Appliances**
- `GET /api/services/smart-home` - Get smart home services
- `POST /api/bookings/smart-home-setup` - Book smart home setup

---

## 🎯 **IMPLEMENTATION EXAMPLE**

### **Customer Booking Flow**
```bash
# 1. Customer Login
POST /api/auth/customer/login

# 2. Browse Services
GET /api/services/categories

# 3. Book Service
POST /api/bookings/create

# 4. View Dashboard
GET /api/customer/dashboard

# 5. Track Booking
GET /api/bookings/customer/:customerId
```

### **Technician Workflow**
```bash
# 1. Technician Login
POST /api/auth/technician/login

# 2. View Dashboard
GET /api/technician/dashboard

# 3. Update Job Status
PUT /api/bookings/:bookingId/status
```

### **Admin Management**
```bash
# 1. Admin Login
POST /api/auth/admin/login

# 2. Manage Users
GET /api/admin/users
POST /api/admin/users

# 3. View System Status
GET /api/admin/dashboard
```

**All APIs are designed for automatic data insertion and seamless service booking!** 🚀
