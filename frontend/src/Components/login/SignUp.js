import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css'; 
import '../../styles/SignUp.css';
 
const Signup = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [number, setNumber] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  //const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      //setError('Passwords do not match');
      toast.error('Passwords do not match');
      return;
    }
 
    const customerData = {
      customerName: username,
      customerEmail: email,
      customerPhone: number,
      password: password,
      deliveryAddresses: [],
      orders: [],
      favoriteRestaurants: [],
      rating: []
    };
 
    try {
      const response = await axios.post('http://localhost:8080/api/customers', customerData);
      console.log(response.data);
      toast.success('Registration successful..');
      setTimeout(() => {
        navigate('/login');
      }, 2000);
    } catch (error) {
      toast.error('Failed to create user');
    }
  };
 
  // const togglePasswordVisibility = () => {
  //   setShowPassword(!showPassword);
  // };
 
  return (
    <div className="signup-container">
      <div className="signup-form-container">
        <div className="signup-form-wrapper">
          <h2 className="signup-title">Customer SignUp</h2>
          <form onSubmit={handleSubmit} className="signup-form">
            <div className="form-group">
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="form-control"
                placeholder="Username"
                required
              />
            </div>
 
            <div className="form-group">
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="form-control"
                placeholder="Email Address"
                required
              />
            </div>
 
            <div className="form-group">
              <input
                type="text"  // Changed to 'text' for phone number to handle all cases
                value={number}
                onChange={(e) => setNumber(e.target.value)}
                className="form-control"
                placeholder="Phone Number"
                required
              />
            </div>
 
            <div className="form-group password-group">
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="form-control"
                placeholder="Password"
                required
              />
              {/* <span className="password-toggle" onClick={togglePasswordVisibility}>
                {showPassword ? 'Hide' : 'Show'}
              </span> */}
            </div>
            <div className="form-group">
              <input
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                className="form-control"
                placeholder="Confirm Password"
                required
              />
            </div>
 
            {/* {error && <div className="alert">{error}</div>} */}
 
            <button type="submit" className="btn-submit">Sign Up</button>
 
            <p className="signin-link">
              Already have an account? <a href="/login" className="link-login">Login</a>
            </p>
          </form>
          <ToastContainer /> {/* Add the ToastContainer component here */}
        </div>
      </div>
    </div>
  );
};
 
export default Signup;