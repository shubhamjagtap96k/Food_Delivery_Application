import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../../styles/RestaurantRegistrationForm.css';
import AdminNavbar from './AdminNavbar';

function RestaurantRegistrationForm() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: '',
    address: '',
    phoneNumber: '',
    deliveryAreas: [''],
    password: ''
  });

  const handleChange = (e) => {
    const { name, value, dataset } = e.target;
    if (name === 'deliveryArea') {
      const updatedAreas = [...formData.deliveryAreas];
      updatedAreas[dataset.index] = value;
      setFormData({ ...formData, deliveryAreas: updatedAreas });
    } else {
      setFormData({
        ...formData,
        [name]: value
      });
    }
  };

  const handleAddArea = () => {
    setFormData({
      ...formData,
      deliveryAreas: [...formData.deliveryAreas, '']
    });
  };

  const handleRemoveArea = (index) => {
    const updatedAreas = formData.deliveryAreas.filter((_, i) => i !== index);
    setFormData({ ...formData, deliveryAreas: updatedAreas });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const restaurantData = {
      restaurantName: formData.name,
      restaurantAddress: formData.address,
      restaurantPhone: formData.phoneNumber,
      deliveryAreas: formData.deliveryAreas,
      password: formData.password 
    };
    try {
      await axios.post('http://localhost:8080/api/restaurants', restaurantData);
      alert('Restaurant registered successfully');
      setFormData({
        name: '',
        address: '',
        phoneNumber: '',
        deliveryAreas: [''],
        password: '' 
      });
      navigate('/adminpage'); 
    } catch (err) {
      alert('Error registering restaurant: ' + err.message);
    }
  };

  return (
    <> 
    <AdminNavbar/>
    <div className="registration-form-container">
      <h1>Register a New Restaurant</h1>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Restaurant Name</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="address">Restaurant Address</label>
          <input
            type="text"
            id="address"
            name="address"
            value={formData.address}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="phoneNumber">Restaurant Phone Number</label>
          <input
            type="text"
            id="phoneNumber"
            name="phoneNumber"
            value={formData.phoneNumber}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Delivery Areas</label>
          {formData.deliveryAreas.map((area, index) => (
            <div key={index} className="delivery-area-group">
              <input
                type="text"
                name="deliveryArea"
                data-index={index}
                value={area}
                onChange={handleChange}
                placeholder="Enter delivery area"
                required
              />
              <button
                type="button"
                onClick={() => handleRemoveArea(index)}
                className="remove-area-btn"
              >
                Remove
              </button>
            </div>
          ))}
          <button
            type="button"
            onClick={handleAddArea}
            className="add-area-btn"
          >
            + Add Another Area
          </button>
        </div>
        <button type="submit" className="btn btn-full">Register</button>
      </form>
    </div>
    </>
  );
}

export default RestaurantRegistrationForm;
