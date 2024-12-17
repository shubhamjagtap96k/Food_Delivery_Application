import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/AdminDashboard.css';

function AdminCard({ title, link }) {
  return (
    <div className="admin-card">
      <Link to={link} className="admin-card-link">
        <h2>{title}</h2>
      </Link>
    </div>
  );
}

export default AdminCard;
