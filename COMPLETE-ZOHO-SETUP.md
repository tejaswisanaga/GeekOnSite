# 🔧 **COMPLETE ZOHO CREATOR SETUP & TOKEN REGENERATION**

## 🎯 **Step-by-Step Zoho Creator Configuration**

### **Step 1: Regenerate Zoho OAuth Token**

#### **A. Revoke Existing Tokens**
1. **Go to**: https://accounts.zoho.com/developerconsole
2. **Navigate**: OAuth → Client Secrets
3. **Select**: Your application (GeekOnSites)
4. **Revoke**: All existing access tokens
5. **Generate**: New client secret

#### **B. Generate New Access Token**
```bash
# Use this script to generate fresh tokens
node scripts/regenerate-zoho-tokens.js
```

### **Step 2: Verify App Link Name**

#### **A. Check Application Details**
1. **Go to**: https://creator.zoho.com
2. **Navigate**: Applications → GeekOnSites
3. **Check**: Application Link Name
4. **Expected**: `geekonsites` (lowercase, no spaces)
5. **Update**: If different, note the exact link name

#### **B. Verify in Code**
```javascript
// Should match exactly from Zoho Creator
const appName = process.env.ZOHO_APP; // Should be "geekonsites"
```

### **Step 3: Verify Form Link Names**

#### **A. Check Form Names in Zoho Creator**
1. **Go to**: https://creator.zoho.com
2. **Navigate**: Applications → GeekOnSites → Forms
3. **Check**: Exact form link names
4. **Expected Forms**:
   - Users (link name: `Users`)
   - Services (link name: `Services`)
   - Service_Requests (link name: `Service_Requests`)
   - Verifications (link name: `Verifications`)

#### **B. Verify in Code**
```javascript
// Update these to match exact Zoho Creator form names
const FORM_NAMES = {
  USERS: 'Users',
  SERVICES: 'Services', 
  SERVICE_REQUESTS: 'Service_Requests',
  VERIFICATIONS: 'Verifications'
};
```

### **Step 4: Confirm API URL Region**

#### **A. Check Your Zoho Account Region**
1. **Go to**: https://accounts.zoho.com/home
2. **Check**: Account profile
3. **Note**: Your data center region
4. **Options**:
   - US: `.com` (most common)
   - India: `.in`
   - Europe: `.eu`
   - Japan: `.jp`
   - Australia: `.com.au`

#### **B. Update Environment Variables**
```bash
# Update .env with correct region
ZOHO_API_DOMAIN=https://www.zohoapis.com  # For US
# OR
ZOHO_API_DOMAIN=https://www.zohoapis.in  # For India
# OR
ZOHO_API_DOMAIN=https://www.zohoapis.eu  # For Europe
```

### **Step 5: Enable API Permission in Zoho Creator**

#### **A. Enable API Access**
1. **Go to**: https://creator.zoho.com
2. **Navigate**: Applications → GeekOnSites
3. **Click**: Settings
4. **Find**: API Access section
5. **Enable**: "Allow API access" checkbox
6. **Save**: Changes

#### **B. Configure Form Permissions**
1. **In Application**: Click on each form
2. **Go to**: Form Properties
3. **Check**: "Allow API access"
4. **Set**: Permissions (Read/Write)
5. **Save**: For each form

#### **C. Publish Application**
1. **Go to**: Applications → GeekOnSites
2. **Click**: Publish
3. **Select**: "Publish to all users"
4. **Confirm**: Publication
5. **Note**: Published URL and API details

### **Step 6: Test with Fresh Configuration**

#### **A. Test Token Generation**
```bash
# Run token regeneration script
node scripts/regenerate-zoho-tokens.js
```

#### **B. Test API Connection**
```bash
# Test with new configuration
node scripts/test-zoho-fixed.js
```

#### **C. Verify in Postman**
```bash
# Start server
npm run dev

# Test in Postman with collection
# Use: GeekOnSites-API-Collection.postman_collection.json
```

## 🔧 **Implementation Scripts**

### **Token Regeneration Script**
```javascript
// scripts/regenerate-zoho-tokens.js
require('dotenv').config();
const axios = require('axios');

async function regenerateZohoTokens() {
  console.log('🔄 Regenerating Zoho OAuth Tokens...\n');
  
  try {
    const baseURL = process.env.ZOHO_API_DOMAIN;
    const clientId = process.env.ZOHO_CLIENT_ID;
    const clientSecret = process.env.ZOHO_CLIENT_SECRET;
    const refreshToken = process.env.ZOHO_REFRESH_TOKEN;
    
    console.log('1️⃣ Testing current refresh token...');
    
    const response = await axios.post(`${baseURL}/oauth/v2/token`, null, {
      params: {
        refresh_token: refreshToken,
        client_id: clientId,
        client_secret: clientSecret,
        grant_type: 'refresh_token'
      }
    });
    
    console.log('✅ New access token generated successfully!');
    console.log('Access Token:', response.data.access_token.substring(0, 50) + '...');
    console.log('Expires in:', response.data.expires_in + ' seconds');
    console.log('Scope:', response.data.scope);
    
    console.log('\n2️⃣ Testing application access...');
    
    const appResponse = await axios.get(`${baseURL}/creator/v2/meta/applications`, {
      headers: {
        'Authorization': `Zoho-oauthtoken ${response.data.access_token}`
      }
    });
    
    console.log('✅ Application access confirmed!');
    console.log('Applications:', appResponse.data.applications.length);
    
    const app = appResponse.data.applications.find(a => a.link_name === process.env.ZOHO_APP);
    if (app) {
      console.log(`✅ Found application: ${app.application_name}`);
      console.log(`   Link Name: ${app.link_name}`);
      console.log(`   Workspace: ${app.workspace_name}`);
      console.log(`   Created: ${app.creation_date}`);
    } else {
      console.log('❌ Application not found with link name:', process.env.ZOHO_APP);
    }
    
  } catch (error) {
    console.error('❌ Token regeneration failed:', error.response?.data || error.message);
  }
}

regenerateZohoTokens();
```

### **Fixed Zoho Service**
```javascript
// services/zohoService-fixed.js
const axios = require('axios');

class ZohoService {
  constructor() {
    this.baseURL = process.env.ZOHO_API_DOMAIN;
    this.accessToken = process.env.ZOHO_ACCESS_TOKEN;
    this.refreshToken = process.env.ZOHO_REFRESH_TOKEN;
    this.clientId = process.env.ZOHO_CLIENT_ID;
    this.clientSecret = process.env.ZOHO_CLIENT_SECRET;
    this.appName = process.env.ZOHO_APP;
    this.owner = process.env.ZOHO_WORKSPACE || 'ceo_asi-techinc'; // Use workspace name
    
    console.log('ZohoService initialized with:');
    console.log('Base URL:', this.baseURL);
    console.log('App Name:', this.appName);
    console.log('Owner:', this.owner);
  }

  async refreshAccessToken() {
    try {
      const response = await axios.post(`${this.baseURL}/oauth/v2/token`, null, {
        params: {
          refresh_token: this.refreshToken,
          client_id: this.clientId,
          client_secret: this.clientSecret,
          grant_type: 'refresh_token'
        }
      });

      this.accessToken = response.data.access_token;
      return this.accessToken;
    } catch (error) {
      console.error('Error refreshing access token:', error.response?.data || error.message);
      throw error;
    }
  }

  async makeRequest(method, endpoint, data = null, params = {}) {
    try {
      const cleanEndpoint = endpoint.startsWith('/') ? endpoint : '/' + endpoint;
      
      // Try different API endpoints
      const endpoints = [
        `${this.baseURL}/creator/v2${cleanEndpoint}`,
        `https://creator.zoho.com/api/v2${cleanEndpoint}`,
        `https://creator.zoho.com/creator/v2.1${cleanEndpoint}`
      ];
      
      for (let i = 0; i < endpoints.length; i++) {
        try {
          const fullUrl = endpoints[i];
          console.log(`Zoho API Request ${i + 1}: ${method} ${fullUrl}`);
          
          const config = {
            method,
            url: fullUrl,
            headers: {
              'Authorization': `Zoho-oauthtoken ${this.accessToken}`,
              'Content-Type': 'application/json'
            },
            params
          };

          if (data) {
            config.data = data;
          }

          const response = await axios(config);
          console.log('✅ Success with endpoint:', endpoints[i]);
          console.log('Zoho API Response:', JSON.stringify(response.data, null, 2));
          return response.data;
          
        } catch (error) {
          console.log(`❌ Endpoint ${i + 1} failed:`, error.response?.data?.message || error.message);
          if (i === endpoints.length - 1) {
            throw error;
          }
        }
      }
    } catch (error) {
      if (error.response?.status === 401) {
        await this.refreshAccessToken();
        return this.makeRequest(method, endpoint, data, params);
      }
      console.error('Zoho API Error:', error.response?.data || error.message);
      throw error;
    }
  }

  async createRecord(formName, data) {
    const requestData = {
      data: [data]
    };
    return this.makeRequest('POST', `/data/${this.owner}/${this.appName}/form/${formName}`, requestData);
  }

  async getRecords(formName, criteria = {}) {
    return this.makeRequest('GET', `/data/${this.owner}/${this.appName}/report/${formName}`, null, { 
      criteria: criteria 
    });
  }
}

module.exports = new ZohoService();
```

## 🎯 **Execution Plan**

### **Step 1: Run Token Regeneration**
```bash
cd "c:\Users\tejas\Downloads\GOSbackend\GOSbackend"
node scripts/regenerate-zoho-tokens.js
```

### **Step 2: Update Environment**
```bash
# Update .env with correct values
ZOHO_WORKSPACE=ceo_asi-techinc
ZOHO_APP=geekonsites
ZOHO_API_DOMAIN=https://www.zohoapis.com
```

### **Step 3: Apply Fixed Service**
```bash
cp services/zohoService-fixed.js services/zohoService.js
```

### **Step 4: Test All Endpoints**
```bash
node scripts/test-zoho-fixed.js
```

### **Step 5: Verify in Postman**
```bash
npm run dev
# Test with Postman collection
```

## 🎉 **Expected Results**

After completing all steps:
- ✅ **Fresh OAuth tokens** generated
- ✅ **Correct app link name** verified
- ✅ **Correct form names** verified
- ✅ **Proper API region** confirmed
- ✅ **API permissions** enabled
- ✅ **Application published** and accessible
- ✅ **Data flowing** to Zoho Creator

**This comprehensive setup will resolve all Zoho Creator API issues!** 🚀
