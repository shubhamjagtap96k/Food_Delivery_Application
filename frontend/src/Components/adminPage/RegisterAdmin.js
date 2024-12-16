import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import "../../styles/RegisterAdmin.css";
import AdminNavbar from './AdminNavbar';

function RegisterAdmin() {
  const [admin, setAdmin] = useState({
    username: '',
    password: '',
    email: ''
  });

  const navigate = useNavigate(); // Initialize useNavigate

  const handleChange = (e) => {
    setAdmin({ ...admin, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/admins', admin);
      alert('Admin registered successfully');
      navigate('/adminpage'); 
    } catch (error) {
      console.error('There was an error registering the admin!', error);
    }
  };

  return (
    <>
    <AdminNavbar />
    <div className="register-admin">
      <h2>Register Admin</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Username:</label>
          <input type="text" name="username" value={admin.username} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input type="email" name="email" value={admin.email} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Password:</label>
          <input type="password" name="password" value={admin.password} onChange={handleChange} required />
        </div>
        <button type="submit" className="btn-submit">Register</button>
      </form>
    </div>
    </>
  );
}

export default RegisterAdmin;
