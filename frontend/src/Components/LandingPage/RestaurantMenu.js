import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import instance from "../setup/axios"; // Ensure this path is correct
import { useRecoilState } from 'recoil';
import { cartState, userState } from '../setup/Recoil'; // Adjust the path if needed
import "../../styles/RestaurantMenu.css"; // For styling
import Header from "./Header";
 
const RestaurantMenu = () => {
  const { restaurantId } = useParams(); // Extract restaurantId from URL
  const [menuItems, setMenuItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [cart, setCart] = useRecoilState(cartState); // Recoil state for cart
  const [user,setUser] = useRecoilState(userState)
 
  useEffect(() => {
    console.log('Fetching menu for restaurant ID:', restaurantId);
   
    instance.get(`/api/restaurants/${restaurantId}/menu`)
    .then((response) => {
      console.log('Menu item response:', response.data); // Check the response data
      setMenuItems(response.data);
      setLoading(false);
    })
    .catch((error) => {
      console.error('Error fetching menu items:', error.response ? error.response.data : error.message);
      setError('Failed to load menu items.');
      setLoading(false);
    });
  }, [restaurantId]);
 
  const handleAddClick = (item) => {
    setCart((prevCart) => {
      const existingItem = prevCart.find(cartItem => cartItem.itemId === item.itemId);
 
      if (existingItem) {
        return prevCart.map(cartItem =>
          cartItem.itemId === item.itemId
            ? { ...cartItem, itemQuantity: cartItem.itemQuantity + 1 }
            : cartItem
        );
      } else {
        return [...prevCart, { ...item, itemQuantity: 1 }];
      }
    });
    localStorage.setItem('cart', JSON.stringify([...cart, { ...item, itemQuantity: 1 }])); // Optional
  };
 
 
 
 
 
  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;
 
  return (
    <>
    <Header />
    <section className="restaurant-menu">
      <h2>Menu</h2>
      <div className="menu-item-container">
        {menuItems.length > 0 ? (
          menuItems.map((item) => (
            <div className="menu-item" key={item.itemId}>
              <img src={item.image} alt={item.itemName} /> {/* Prefix URL if needed */}
              <h3>{item.itemName}</h3>
              <p>{item.itemDescription}</p>
              <p className="price"><strong>Price:</strong> ${item.itemPrice}</p>
              <button className="add-button" onClick={() => handleAddClick(item)}>Add</button>
            </div>
          ))
        ) : (
          <p className="no-items">No menu items available.</p>
        )}
      </div>
    </section>
    </>
  );
};
 
export default RestaurantMenu;