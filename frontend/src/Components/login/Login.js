// Login.js
import React, { useState } from 'react';
import instance from '../setup/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';
import '../../styles/Login.css';
import { useRecoilState } from 'recoil';
import { userState } from '../setup/Recoil';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('');
  const [user,setUser]=useRecoilState(userState);
  const navigate = useNavigate();

  const handleSignIn = async (e) => {
    e.preventDefault();
    if (!username || !password || !role) {
      toast.error("Username, password, and role are required");
      return;
    }

    try {
      let response;
      if (role === 'admin') {
        response = await instance.post('http://localhost:8080/api/admins/login', { username, password });
      } else if (role === 'restaurant') {
        response = await instance.post('http://localhost:8080/api/restaurants/login', { restaurantName: username, password });
        const restaurantData = response.data;
        if (restaurantData && restaurantData.restaurantId) {
          localStorage.setItem('Restaurant', JSON.stringify(restaurantData));
          navigate(`/restaurantpage/${restaurantData.restaurantId}`);
          toast.success("Login successful");
          return;
        }
      } else {
        response = await instance.post('http://localhost:8080/api/customers/login', { username, password });
        const userData = response.data;
 
        if (userData) {
          localStorage.setItem('User', JSON.stringify(userData)); // Store user data
          setUser(username)
          navigate("/");
          toast.success("Login successful");
        } else {
          toast.error("Username or password is invalid");
        }
   
      }

      const userData = response.data;

      if (userData) {
        localStorage.setItem('User', JSON.stringify(userData));
        if (role === 'admin') {
          navigate("/adminpage");
        } else {
          navigate("/home");
        }
        toast.success("Login successful");
      } else {
        toast.error("Username or password is invalid");
      }
    } catch (error) {
      toast.error("An error occurred during login");
    }
  };

  return (
    <div className="login-container">
      <ToastContainer />
      <div className="form-container">
        <div className="form-wrapper">
          <h2 className="form-title">Sign In</h2>
          <form onSubmit={handleSignIn} className="form">
            <div className="form-group">
              <select
                value={role}
                onChange={(e) => setRole(e.target.value)}
                className="form-control"
                required
              >
                <option value="" disabled>Select a role</option>
                <option value="admin">Admin</option>
                <option value="customer">Customer</option>
                <option value="restaurant">Restaurant</option>
              </select>
            </div>
            <div className="form-group">
              <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="form-control"
                required
              />
            </div>
            <div className="form-group">
              <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="form-control"
                required
              />
            </div>
            
            <button type="submit" className="btn-submit">Sign In</button>
            <p className="switch-form">
              Don't have an account? <a href="/signup" className="btn-switch">Sign Up</a>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
