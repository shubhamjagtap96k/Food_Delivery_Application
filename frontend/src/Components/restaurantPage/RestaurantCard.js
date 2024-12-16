import React from 'react';
import { Link } from 'react-router-dom';
import "../../styles/RestaurantDashboard.css"

function RestaurantCard({ title, link }) {
  return (
    <div className="restaurant-card">
      <Link to={link} className="restaurant-card-link">
        <h2>{title}</h2>
      </Link>
    </div>
  );
}

export default RestaurantCard;
