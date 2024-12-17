// RestaurantNavbar.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../../img/sprintLogo.png'; 
import "../../styles/RestaurantDashboard.css"; // Ensure CSS file is imported

function RestaurantNavbar() {
  const navigate = useNavigate();
  const restaurant = JSON.parse(localStorage.getItem('Restaurant'));

  const handleSignOut = () => {
    localStorage.removeItem('Restaurant'); // Clear restaurant data from local storage
    navigate('/login'); // Redirect to login page
  };

  return (
    <nav className="navbar">
      <img src={logo} alt="logo" className="navbar-logo" />
      <h1 className="navbar-title">Quick Bites Restaurant</h1>
      <div className="navbar-user">
        <div className="dropdown">
          <button className="dropdown-button">
            {restaurant ? restaurant.restaurantName : 'Restaurant'}
          </button>
          <div className="dropdown-content">
            <a href="#" onClick={handleSignOut}>Sign Out</a>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default RestaurantNavbar;
