const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const rateLimit = require('express-rate-limit');
require('dotenv').config();

// Import database connection
const databaseConnection = require('./config/databaseConnection');

const authRoutes = require('./routes/auth');
const userRoutes = require('./routes/users');
const technicianRoutes = require('./routes/technicians');
const serviceRoutes = require('./routes/services');
const dashboardRoutes = require('./routes/dashboard');
const syncRoutes = require('./routes/sync');
const serviceBookingRoutes = require('./routes/serviceBooking');
const serviceCategoriesRoutes = require('./routes/serviceCategories');

const app = express();
const PORT = process.env.PORT;

// Security middleware
app.use(helmet());
app.use(cors({
  origin: process.env.FRONTEND_URL,
  credentials: true
}));

// Rate limiting
const limiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minutes
  max: 100 // limit each IP to 100 requests per windowMs
});
app.use(limiter);

// Body parsing middleware
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ extended: true }));

// Routes
app.use('/api/auth', authRoutes);
app.use('/api/users', userRoutes);
app.use('/api/technicians', technicianRoutes);
app.use('/api/services', serviceRoutes);
app.use('/api/dashboard', dashboardRoutes);
app.use('/api/sync', syncRoutes);
app.use('/api', serviceBookingRoutes);
app.use('/api', serviceCategoriesRoutes);

// Health check endpoint
app.get('/api/health', async (req, res) => {
  try {
    const dbHealth = await databaseConnection.healthCheck();
    
    res.status(200).json({ 
      status: 'OK', 
      message: 'GeekOnSites API is running',
      timestamp: new Date().toISOString(),
      database: dbHealth
    });
  } catch (error) {
    res.status(500).json({ 
      status: 'ERROR', 
      message: 'GeekOnSites API is running but database connection failed',
      timestamp: new Date().toISOString(),
      database: { status: 'unhealthy', error: error.message }
    });
  }
});

// Error handling middleware
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ 
    error: 'Something went wrong!',
    message: process.env.NODE_ENV === 'development' ? err.message : 'Internal server error'
  });
});

// 404 handler
app.use('*', (req, res) => {
  res.status(404).json({ error: 'Route not found' });
});

// Connect to database and start server
async function startServer() {
  try {
    // Connect to MongoDB
    await databaseConnection.connect();
    
    app.listen(PORT, () => {
      console.log(`🚀 GeekOnSites server running on port ${PORT}`);
      console.log(`📊 Environment: ${process.env.NODE_ENV}`);
      console.log(`💾 Database: Connected`);
    });
  } catch (error) {
    console.error('❌ Failed to start server:', error);
    process.exit(1);
  }
}

startServer();
