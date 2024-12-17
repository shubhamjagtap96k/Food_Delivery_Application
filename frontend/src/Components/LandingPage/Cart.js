import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import '../../styles/Cart.css';
import instance from "../setup/axios";
import Header from "./Header";

const Cart = () => {
  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [customerId, setCustomerId] = useState(null);
  const [restaurantId, setRestaurantId] = useState(null);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    setCartItems(cart);
    calculateTotalPrice(cart);  // Ensure total price is calculated here
    fetchCustomerAndRestaurantDetails();
  }, []);

  const fetchCustomerAndRestaurantDetails = async () => {
    try {
      const customerResponse = await instance.get('/api/customers');
      const restaurantResponse = await instance.get('/api/restaurants');
      setCustomerId(customerResponse.data.customerId || 3);
      setRestaurantId(restaurantResponse.data.restaurantId || 4);
      setLoading(false);
    } catch (error) {
      setCustomerId(2);
      setRestaurantId(4);
      setLoading(false);
    }
  };

  const handleRemove = (itemId) => {
    const updatedCart = cartItems.filter(item => item.itemId !== itemId);
    setCartItems(updatedCart);
    localStorage.setItem('cart', JSON.stringify(updatedCart));
    calculateTotalPrice(updatedCart);  // Recalculate price
  };

  const handleDecreaseQuantity = (itemId) => {
    const updatedCart = cartItems.map(item =>
      item.itemId === itemId && item.itemQuantity > 1
        ? { ...item, itemQuantity: item.itemQuantity - 1 }
        : item
    );
    setCartItems(updatedCart);
    localStorage.setItem('cart', JSON.stringify(updatedCart));
    calculateTotalPrice(updatedCart);  // Recalculate price
  };

  const handleIncreaseQuantity = (itemId) => {
    const updatedCart = cartItems.map(item =>
      item.itemId === itemId
        ? { ...item, itemQuantity: item.itemQuantity + 1 }
        : item
    );
    setCartItems(updatedCart);
    localStorage.setItem('cart', JSON.stringify(updatedCart));
    calculateTotalPrice(updatedCart);  // Recalculate price
  };

  const calculateTotalPrice = (cart) => {
    const total = cart.reduce((sum, item) => sum + (item.itemPrice * item.itemQuantity), 0);
    setTotalPrice(total.toFixed(2));  // Format to 2 decimal places
  };

  const placeOrder = async () => {
    if (loading || !customerId || !restaurantId) {
      alert('Loading, please wait...');
      return;
    }

    try {
      const orderDetails = {
        orderDate: new Date().toISOString(),
        customerId,
        restaurantId,
        deliveryDriverId: null,
        orderStatus: "PROCESSING",
      };

      await instance.post('/api/orders', orderDetails);

      // Save cart items and total price in localStorage for Checkout page
      localStorage.setItem('cart', JSON.stringify(cartItems));
      localStorage.setItem('totalPrice', totalPrice);

      // Clear cart and localStorage after successful order placement
      setCartItems([]);
      localStorage.removeItem('cart');
     
      // Store order status in localStorage for Checkout page
      localStorage.setItem('orderStatus', 'PROCESSING');
 
      // Navigate to Checkout page
      navigate('/checkout');
    } catch (error) {
      alert('Failed to place order.');
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
    <>
      <Header />
      <section className="cart">
        <h2>Cart</h2>
        {cartItems.length > 0 ? (
          <>
            <div className="cart-items">
              {cartItems.map(item => (
                <div className="cart-item" key={item.itemId}>
                  <img src={item.image} alt={item.itemName} />
                  <div className="item-details">
                    <h3>{item.itemName}</h3>
                    <p>{item.itemDescription}</p>
                    <p className="price"><strong>Price:</strong> ${item.itemPrice}</p>
                    <div className="quantity-controls">
                      <button className="quantity-btn" onClick={() => handleDecreaseQuantity(item.itemId)}>-</button>
                      <span>{item.itemQuantity}</span>
                      <button className="quantity-btn" onClick={() => handleIncreaseQuantity(item.itemId)}>+</button>
                    </div>
                    <button className="remove-button" onClick={() => handleRemove(item.itemId)}>Remove</button>
                  </div>
                </div>
              ))}
            </div>
            <div className="checkout-section">
              <p className="total-price">Total Price: ${totalPrice}</p>
              <button className="checkout-btn" onClick={placeOrder}>Place Order</button>
            </div>
          </>
        ) : (
          <p className="empty-cart">Thank You Visit again !!!</p>
        )}
      </section>
    </>
  );
};

export default Cart;
