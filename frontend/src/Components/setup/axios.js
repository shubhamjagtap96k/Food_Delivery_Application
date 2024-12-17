import axios from 'axios';
 
const instance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000, // Optional: set a timeout for requests
  headers: { 'Content-Type': 'application/json' } // Optional: default headers
});
 
export default instance;