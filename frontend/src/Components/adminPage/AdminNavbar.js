// AdminNavbar.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../../img/sprintLogo.png'; 
import '../../styles/AdminDashboard.css'; // Ensure this CSS file is imported

function AdminNavbar() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('User'));

  const handleSignOut = () => {
    localStorage.removeItem('User'); // Clear user data from local storage
    navigate('/login'); // Redirect to login page
  };

  return (
    <nav className="navbar">
      <img src={logo} alt="logo" className="navbar-logo" />
      <h1 className="navbar-title">Quick Bites Admin</h1>
      <div className="navbar-user">
        <div className="dropdown">
          <button className="dropdown-button">
            {user ? user.username : 'Admin'}
          </button>
          <div className="dropdown-content">
            <a href="#" onClick={handleSignOut}>Sign Out</a>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default AdminNavbar;
