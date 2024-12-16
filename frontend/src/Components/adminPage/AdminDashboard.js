import React from 'react';
import { Link } from 'react-router-dom';
import Navbar from './AdminNavbar';
import AdminCard from './AdminCard';
import '../../styles/AdminDashboard.css';

function AdminDashboard() {
  return (
    <div className="admin-dashboard">
      <Navbar />
      <div className="card-container">
        <Link to="register-restaurant" className='under'>
          <AdminCard title="Register Restaurant" />
        </Link>
        <Link to="register-admin" className='under'>
          <AdminCard title="Register Admin" />
        </Link>
        <Link to="all-restaurants" className='under'>
          <AdminCard title="All Restaurants" />
        </Link>
        <Link to="all-delivery-drivers" className='under'>
          <AdminCard title="All Delivery Drivers" />
        </Link>
        <Link to="all-customers" className='under'>
          <AdminCard title="All Customers" />
        </Link>
      </div>
    </div>
  );
}

export default AdminDashboard;
