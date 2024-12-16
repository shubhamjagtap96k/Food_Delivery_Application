import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import '../../styles/ViewMenu.css';
import RestaurantNavbar from './RestaurantNavbar';

function ViewMenu() {
  const { restaurantId } = useParams(); // Get restaurantId from URL
  const [menuItems, setMenuItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editItemId, setEditItemId] = useState(null); // Track which item is being edited
  const [editItemName, setEditItemName] = useState('');
  const [editItemDescription, setEditItemDescription] = useState('');
  const [editItemPrice, setEditItemPrice] = useState('');

  useEffect(() => {
    const fetchMenuItems = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/restaurants/${restaurantId}/menu`);
        setMenuItems(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchMenuItems();
  }, [restaurantId]);

  const handleDelete = async (itemId) => {
    try {
      await axios.delete(`http://localhost:8080/api/restaurants/${restaurantId}/menu/${itemId}`);
      setMenuItems(menuItems.filter(item => item.itemId !== itemId));
    } catch (err) {
      setError(err.message);
    }
  };

  const handleUpdate = async () => {
    try {
      const updatedItem = {
        itemName: editItemName,
        itemDescription: editItemDescription,
        itemPrice: parseFloat(editItemPrice),
      };
      await axios.put(`http://localhost:8080/api/restaurants/${restaurantId}/menu/${editItemId}`, updatedItem);
      setMenuItems(menuItems.map(item => item.itemId === editItemId ? { ...item, ...updatedItem } : item));
      setEditItemId(null); // Exit edit mode
    } catch (err) {
      setError(err.message);
    }
  };

  const startEditing = (item) => {
    setEditItemId(item.itemId);
    setEditItemName(item.itemName);
    setEditItemDescription(item.itemDescription);
    setEditItemPrice(item.itemPrice);
  };

  const cancelEditing = () => {
    setEditItemId(null);
    setEditItemName('');
    setEditItemDescription('');
    setEditItemPrice('');
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <>
    <RestaurantNavbar />
    <div className="view-menu">
      <h1>Menu Items for Restaurant</h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {menuItems.length === 0 ? (
            <tr>
              <td colSpan="5">No menu items found</td>
            </tr>
          ) : (
            menuItems.map(item => (
              <tr key={item.itemId}>
                <td>{item.itemId}</td>
                <td>
                  {editItemId === item.itemId ? (
                    <input
                      type="text"
                      value={editItemName}
                      onChange={(e) => setEditItemName(e.target.value)}
                    />
                  ) : (
                    item.itemName
                  )}
                </td>
                <td>
                  {editItemId === item.itemId ? (
                    <input
                      type="text"
                      value={editItemDescription}
                      onChange={(e) => setEditItemDescription(e.target.value)}
                    />
                  ) : (
                    item.itemDescription
                  )}
                </td>
                <td>
                  {editItemId === item.itemId ? (
                    <input
                      type="number"
                      value={editItemPrice}
                      onChange={(e) => setEditItemPrice(e.target.value)}
                      step="0.01"
                    />
                  ) : (
                    `$${item.itemPrice.toFixed(2)}`
                  )}
                </td>
                <td>
                  {editItemId === item.itemId ? (
                    <>
                      <button onClick={handleUpdate} className="btn btn-full">Update</button>
                      <button onClick={cancelEditing} className="btn btn-cancel">Cancel</button>
                    </>
                  ) : (
                    <div className="button-container">
                      <button onClick={() => startEditing(item)}>Edit</button>
                      <button onClick={() => handleDelete(item.itemId)}>Delete</button>
                    </div>
                  )}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
    </>
  );
}

export default ViewMenu;
