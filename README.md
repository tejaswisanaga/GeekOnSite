# GeekOnSites Backend

Backend API for GeekOnSites application built with Node.js and Zoho Creator integration.

## Features

- **Multi-role Authentication**: Customer, Technician, and Admin roles
- **User Registration & Login**: JWT-based authentication
- **Email Verification**: One-time verification for new users
- **Assessor ID Verification**: Customer identity verification
- **Location Services**: Technician location tracking and nearby search
- **Service Management**: Service requests and status tracking
- **Role-based Dashboards**: Different dashboards for each user type

## Tech Stack

- Node.js with Express.js
- Zoho Creator API for database
- JWT for authentication
- bcryptjs for password hashing
- Express-validator for input validation

## Installation

1. Install dependencies:
```bash
npm install
```

2. Set up environment variables:
```bash
cp .env.example .env
```

3. Configure your Zoho Creator credentials in `.env`:
```
ZOHO_ACCESS_TOKEN=your_access_token
ZOHO_REFRESH_TOKEN=your_refresh_token
ZOHO_CLIENT_ID=your_client_id
ZOHO_CLIENT_SECRET=your_client_secret
ZOHO_OWNER=your_email@domain.com
ZOHO_APP=YourAppName
ZOHO_API_DOMAIN=https://www.zohoapis.com
JWT_SECRET=your_jwt_secret
JWT_EXPIRES_IN=7d
PORT=3000
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login
- `POST /api/auth/verify-email` - Verify email with code
- `POST /api/auth/resend-verification` - Resend verification code
- `GET /api/auth/me` - Get current user info

### Users
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile
- `POST /api/users/assessor-id` - Submit assessor ID (customers only)
- `GET /api/users/` - Get all users (admin only)

### Technicians
- `GET /api/technicians/nearby` - Get nearby technicians (customers only)
- `PUT /api/technicians/location` - Update technician location
- `GET /api/technicians/` - Get all technicians (admin only)

### Services
- `GET /api/services/` - Get all services
- `POST /api/services/request` - Create service request (customers only)
- `GET /api/services/my-requests` - Get customer's requests
- `GET /api/services/assigned-requests` - Get technician's assigned requests
- `PUT /api/services/request/:requestId/status` - Update request status (technicians only)

### Dashboard
- `GET /api/dashboard/customer` - Customer dashboard
- `GET /api/dashboard/technician` - Technician dashboard
- `GET /api/dashboard/admin` - Admin dashboard

## User Roles

### Customer
- Register and verify email
- Submit assessor ID for verification
- Search for nearby technicians
- Create and track service requests
- View service history

### Technician
- Update location and availability
- View and manage assigned requests
- Update service request status
- Track earnings and ratings

### Admin
- Manage all users
- View system statistics
- Monitor service requests
- Manage services

## Zoho Creator Forms

The application uses the following Zoho Creator forms:

1. **Users** - Main user data with roles and verification status
2. **Services** - Available services with pricing
3. **Service_Requests** - Customer service requests
4. **Locations** - User location data
5. **Verifications** - Email and ID verification records

## Development

Start the development server:
```bash
npm run dev
```

Start production server:
```bash
npm start
```

## Security Features

- Rate limiting on all endpoints
- Input validation and sanitization
- JWT token authentication
- Password hashing with bcrypt
- Role-based access control
- CORS configuration

## Error Handling

All endpoints return consistent error responses:
```json
{
  "error": "Error message",
  "details": "Additional error details (if available)"
}
```

## Health Check

Check API status:
```bash
GET /api/health
```

## License

MIT
