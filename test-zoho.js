require('dotenv').config();
const axios = require('axios');

async function refreshAccessToken() {
  try {
    const refreshToken = process.env.ZOHO_REFRESH_TOKEN;
    const clientId = process.env.ZOHO_CLIENT_ID;
    const clientSecret = process.env.ZOHO_CLIENT_SECRET;
    
    console.log('Refreshing access token...');
    console.log('Refresh token (first 20):', refreshToken?.substring(0, 20) + '...');
    console.log('Client ID:', clientId);
    
    const response = await axios.post(`https://accounts.zoho.com/oauth/v2/token`, null, {
      params: {
        refresh_token: refreshToken,
        client_id: clientId,
        client_secret: clientSecret,
        grant_type: 'refresh_token'
      }
    });
    
    console.log('Token refresh response:', JSON.stringify(response.data, null, 2));
    console.log('New access token:', response.data.access_token?.substring(0, 20) + '...');
    return response.data.access_token;
  } catch (error) {
    console.error('Error refreshing token:', error.response?.data || error.message);
    console.error('Full error details:', error.response?.status, error.response?.data);
    throw error;
  }
}

async function testZohoAPI() {
  try {
    console.log('Testing Zoho API connection...');
    
    const baseURL = process.env.ZOHO_API_DOMAIN;
    const owner = 'ceo_asi-techinc'; // Use workspace name
    const appName = process.env.ZOHO_APP;
    let accessToken = process.env.ZOHO_ACCESS_TOKEN;
    
    console.log('Environment variables:');
    console.log('Base URL:', baseURL);
    console.log('Owner:', owner);
    console.log('App Name:', appName);
    console.log('Access Token (first 20 chars):', accessToken?.substring(0, 20) + '...');
    
    // Test meta API to get applications
    const metaUrl = `${baseURL}/creator/v2/meta/applications`;
    console.log('Meta API URL:', metaUrl);
    
    try {
      const metaResponse = await axios.get(metaUrl, {
        headers: {
          'Authorization': `Zoho-oauthtoken ${accessToken}`
        }
      });
      
      console.log('Applications:', JSON.stringify(metaResponse.data, null, 2));
    } catch (error) {
      if (error.response?.status === 401) {
        console.log('Token expired, refreshing...');
        accessToken = await refreshAccessToken();
        
        const metaResponse = await axios.get(metaUrl, {
          headers: {
            'Authorization': `Zoho-oauthtoken ${accessToken}`
          }
        });
        
        console.log('Applications:', JSON.stringify(metaResponse.data, null, 2));
      } else {
        throw error;
      }
    }
    
    // Test data access for the application
    const dataUrl = `${baseURL}/creator/v2/data/${owner}/${appName}/report/Users`;
    console.log('Data URL:', dataUrl);
    
    try {
      const dataResponse = await axios.get(dataUrl, {
        headers: {
          'Authorization': `Zoho-oauthtoken ${accessToken}`
        }
      });
      
      console.log('Users Data:', JSON.stringify(dataResponse.data, null, 2));
    } catch (error) {
      console.log('Data API Error:', error.response?.data || error.message);
    }
    
  } catch (error) {
    console.error('Zoho API Error:', error.response?.data || error.message);
    if (error.response) {
      console.error('Response status:', error.response.status);
      console.error('Response data:', error.response.data);
    }
  }
}
                                                           
testZohoAPI();
