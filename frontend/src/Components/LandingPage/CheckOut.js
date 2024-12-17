import React, { useEffect, useState } from 'react';
import '../../styles/Checkout.css'; // For styling
import Header from './Header';

const CheckOut = () => {
  const [cartItems, setCartItems] = useState([]);
  const [orderStatus, setOrderStatus] = useState("PROCESSING");
  const [totalPrice, setTotalPrice] = useState(0);

  useEffect(() => {
    // Retrieve cart items and order status from localStorage
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    setCartItems(cart);

    // Retrieve total price from localStorage
    const price = localStorage.getItem('totalPrice') || "0.00";
    setTotalPrice(price);

    // Retrieve order status from localStorage
    const status = localStorage.getItem('orderStatus') || "PROCESSING";
    setOrderStatus(status);

    // Clear cart after showing
    localStorage.removeItem('cart');
    localStorage.removeItem('totalPrice');
    localStorage.removeItem('orderStatus');
  }, []);

  return (
    <>
      <Header />
      <section className="checkout">
        <h2>Order Confirmation</h2>
        <p className="success-message">Your order has been placed successfully!</p>
        <div className="order-status">
          <p><strong>Order Status:</strong> {orderStatus}</p>
        </div>
        {/* <h3>Ordered Items:</h3> */}
        <div className="checkout-items">
          {cartItems.length > 0 ? (
            cartItems.map(item => (
              <div className="checkout-item" key={item.itemId}>
                <img src={item.image} alt={item.itemName} />
                <div className="item-details">
                  <h4>{item.itemName}</h4>
                  <p>{item.itemDescription}</p>
                  <p><strong>Quantity:</strong> {item.itemQuantity}</p>
                  <p><strong>Price:</strong> ${item.itemPrice}</p>
                </div>
              </div>
            ))
          ) : (
            <p></p>
          )}
        </div>
        {cartItems.length > 0 && (
          <div className="total-price">
            <h3>Total Price: ${totalPrice}</h3>
          </div>
        )}
      </section>
    </>
  );
};

export default CheckOut;
