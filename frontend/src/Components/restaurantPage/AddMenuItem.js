import React, { useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import '../../styles/AddMenuItem.css';
import RestaurantNavbar from './RestaurantNavbar';
 
function AddMenuItem() {
  const { restaurantId } = useParams();
  const navigate = useNavigate();
 
  const [itemName, setItemName] = useState('');
  const [itemDescription, setItemDescription] = useState('');
  const [itemPrice, setItemPrice] = useState('');
  const [error, setError] = useState(null);
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const menuItem = {
        itemName,
        itemDescription,
        itemPrice: parseFloat(itemPrice),
      };
 
      if (!itemName || !itemDescription || isNaN(menuItem.itemPrice)) {
        setError('Please fill in all fields correctly.');
        return;
      }
 
      await axios.post(`http://localhost:8080/api/restaurants/${restaurantId}/menu`, menuItem);
      alert('Item added successfully');
      setItemName('');
      setItemDescription('');
      setItemPrice('');
      setError(null);
      // navigate(`/restaurantpage/${restaurantId}`);
    } catch (err) {
      setError('Item already exists');
    }
  };
 
  return (
    <>
      <RestaurantNavbar />
      <div className="add-menu-item">
        <h1>Add Menu Item</h1>
        {error && <p className="error">{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="itemName">Item Name:</label>
            <input
              type="text"
              id="itemName"
              value={itemName}
              onChange={(e) => setItemName(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="itemDescription">Item Description:</label>
            <input
              type="text"
              id="itemDescription"
              value={itemDescription}
              onChange={(e) => setItemDescription(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="itemPrice">Item Price:</label>
            <input
              type="number"
              id="itemPrice"
              value={itemPrice}
              onChange={(e) => setItemPrice(e.target.value)}
              step="0.01"
              required
            />
          </div>
          <button type="submit" className="btn btn-submit">Add Item</button>
        </form>
      </div>
    </>
  );
}
 
export default AddMenuItem;