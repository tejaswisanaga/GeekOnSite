# GeekOnSites API Testing Guide

All APIs are available at `http://localhost:3000/api`

## Authentication Endpoints

### 1. Register New User
```bash
# Register Customer
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890",
    "password": "password123",
    "role": "customer",
    "address": "123 Main St, New York, NY",
    "latitude": 40.7128,
    "longitude": -74.0060
  }'

# Register Technician
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane@example.com",
    "phone": "+1234567891",
    "password": "password123",
    "role": "technician",
    "address": "456 Oak Ave, New York, NY",
    "latitude": 40.7580,
    "longitude": -73.9855
  }'

# Register Admin
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Admin User",
    "email": "admin@example.com",
    "phone": "+1234567892",
    "password": "admin123",
    "role": "admin",
    "address": "789 Admin St, New York, NY"
  }'
```

### 2. Login User
```bash
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'

# Response will contain token, save it for subsequent requests
# Example response:
# {
#   "message": "Login successful",
#   "user": { "id": "123456", "name": "John Doe", "email": "john@example.com", "role": "customer" },
#   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
# }
```

### 3. Verify Email
```bash
curl -X POST http://localhost:3000/api/auth/verify-email \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "code": "123456"
  }'
```

### 4. Resend Verification Code
```bash
curl -X POST http://localhost:3000/api/auth/resend-verification \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com"
  }'
```

### 5. Get Current User (Protected)
```bash
curl -X GET http://localhost:3000/api/auth/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## User Management Endpoints

### 6. Get User Profile
```bash
curl -X GET http://localhost:3000/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 7. Update User Profile
```bash
curl -X PUT http://localhost:3000/api/users/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "phone": "+1234567899",
    "address": "123 New Address, New York, NY",
    "latitude": 40.7128,
    "longitude": -74.0060
  }'
```

### 8. Submit Assessor ID (Customer Only)
```bash
curl -X POST http://localhost:3000/api/users/assessor-id \
  -H "Authorization: Bearer CUSTOMER_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "assessorId": "123-45-6789",
    "idType": "SSN"
  }'
```

### 9. Get All Users (Admin Only)
```bash
curl -X GET "http://localhost:3000/api/users/?role=customer&page=1&limit=10" \
  -H "Authorization: Bearer ADMIN_JWT_TOKEN"
```

## Technician Endpoints

### 10. Get Nearby Technicians (Customer Only)
```bash
curl -X GET "http://localhost:3000/api/technicians/nearby?latitude=40.7128&longitude=-74.0060&radius=50" \
  -H "Authorization: Bearer CUSTOMER_JWT_TOKEN"
```

### 11. Update Technician Location
```bash
curl -X PUT http://localhost:3000/api/technicians/location \
  -H "Authorization: Bearer TECHNICIAN_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "latitude": 40.7580,
    "longitude": -73.9855,
    "address": "456 Oak Ave, New York, NY"
  }'
```

### 12. Get All Technicians (Admin Only)
```bash
curl -X GET "http://localhost:3000/api/technicians/?verified=true&page=1&limit=10" \
  -H "Authorization: Bearer ADMIN_JWT_TOKEN"
```

## Service Endpoints

### 13. Get All Services
```bash
curl -X GET "http://localhost:3000/api/services/?category=plumbing&active=true"
```

### 14. Create Service Request (Customer Only)
```bash
curl -X POST http://localhost:3000/api/services/request \
  -H "Authorization: Bearer CUSTOMER_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "serviceId": "SERVICE_ID_FROM_ZOHO",
    "notes": "Need urgent plumbing repair",
    "scheduledDate": "2024-01-15T10:00:00Z"
  }'
```

### 15. Get Customer's Service Requests
```bash
curl -X GET "http://localhost:3000/api/services/my-requests?status=pending&page=1&limit=10" \
  -H "Authorization: Bearer CUSTOMER_JWT_TOKEN"
```

### 16. Get Technician's Assigned Requests
```bash
curl -X GET "http://localhost:3000/api/services/assigned-requests?status=assigned&page=1&limit=10" \
  -H "Authorization: Bearer TECHNICIAN_JWT_TOKEN"
```

### 17. Update Service Request Status (Technician Only)
```bash
curl -X PUT http://localhost:3000/api/services/request/REQUEST_ID/status \
  -H "Authorization: Bearer TECHNICIAN_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "in_progress",
    "notes": "Started working on the repair"
  }'
```

## Dashboard Endpoints

### 18. Customer Dashboard
```bash
curl -X GET http://localhost:3000/api/dashboard/customer \
  -H "Authorization: Bearer CUSTOMER_JWT_TOKEN"
```

### 19. Technician Dashboard
```bash
curl -X GET http://localhost:3000/api/dashboard/technician \
  -H "Authorization: Bearer TECHNICIAN_JWT_TOKEN"
```

### 20. Admin Dashboard
```bash
curl -X GET http://localhost:3000/api/dashboard/admin \
  -H "Authorization: Bearer ADMIN_JWT_TOKEN"
```

## Health Check

### 21. API Health Check
```bash
curl -X GET http://localhost:3000/api/health
```

## Testing Workflow

### Step 1: Start the Server
```bash
cd c:/Users/HP/Downloads/GOSAPP/GOSbackend
npm install
npm run dev
```

### Step 2: Test Registration and Login
```bash
# Register users (run each command separately)
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Customer",
    "email": "customer@test.com",
    "phone": "+1234567890",
    "password": "password123",
    "role": "customer",
    "address": "123 Main St, New York, NY",
    "latitude": 40.7128,
    "longitude": -74.0060
  }'

curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Technician",
    "email": "tech@test.com",
    "phone": "+1234567891",
    "password": "password123",
    "role": "technician",
    "address": "456 Oak Ave, New York, NY",
    "latitude": 40.7580,
    "longitude": -73.9855
  }'
```

### Step 3: Login and Get Tokens
```bash
# Login as customer
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "customer@test.com",
    "password": "password123"
  }'

# Login as technician
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "tech@test.com",
    "password": "password123"
  }'
```

### Step 4: Test Protected Endpoints
Replace `YOUR_JWT_TOKEN` with the actual token from login response.

## Error Response Format
All errors follow this format:
```json
{
  "error": "Error message",
  "details": "Additional error details (if available)"
}
```

## Common Status Codes
- `200` - Success
- `201` - Created
- `400` - Bad Request (Validation error)
- `401` - Unauthorized (Authentication required)
- `403` - Forbidden (Insufficient permissions)
- `404` - Not Found
- `500` - Internal Server Error

## Testing Tips

1. **Save Tokens**: Store JWT tokens from login responses for testing protected endpoints
2. **Check Zoho Creator**: Ensure your Zoho Creator forms match the field names in `config/database.js`
3. **Environment Variables**: Make sure all Zoho credentials are properly set in `.env`
4. **Use Postman**: For easier testing, import these endpoints into Postman
5. **Check Logs**: Monitor console logs for debugging API issues

## Postman Collection

You can import these endpoints into Postman by creating a new collection and adding each endpoint with the appropriate method, headers, and body parameters.
