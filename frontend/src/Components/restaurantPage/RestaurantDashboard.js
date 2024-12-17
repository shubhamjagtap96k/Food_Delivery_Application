import React from 'react';
import { Link } from 'react-router-dom';
import RestaurantCard from './RestaurantCard';
import RestaurantNavbar from './RestaurantNavbar';
import "../../styles/RestaurantDashboard.css"



function RestaurantDashboard() {
  return (
    <div className="restaurant-dashboard">
      <RestaurantNavbar />
      <div className="card-container">
        {/* <Link to="view-deliverydrivers" className='under'>
          <RestaurantCard title="View Delivery Drivers" />
        </Link> */}
        <Link to="view-menu" className='under'>
          <RestaurantCard title="View Menu" />
        </Link>
        <Link to="add-menu" className='under'>
          <RestaurantCard title="Add Menu Item" />
        </Link>
        <Link to="view-orders" className='under'>
          <RestaurantCard title="View Orders" />
        </Link>
        {/* <Link to="all-customers" className='under'>
          <RestaurantCard title="All Customers" />
        </Link> */}
      </div>
    </div>
  );
}

export default RestaurantDashboard;
