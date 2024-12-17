import React from 'react';
import '../../styles/Header.css';
import '@fortawesome/fontawesome-free/css/all.min.css';  
import { useRecoilState, useRecoilValue } from 'recoil';
import { cartState, userState } from '../setup/Recoil'; // Adjust the path if needed
import { Link } from 'react-router-dom';

const Header = () => {
  const cart = useRecoilValue(cartState); // Get cart state
  const cartItemCount = cart.length; // Number of items in cart
  const [user, setUser] = useRecoilState(userState);

  // Get favorite count from localStorage
  const favorites = JSON.parse(localStorage.getItem('favorites')) || [];
  const favoriteCount = favorites.length;

  return (
    <header className="header">
      <div className="logo">QuickBites</div>
      <nav>
        <ul>
          <li><a href="/home"><i className="fas fa-home"></i> Home</a></li>
          <li><a href="/favorite-restaurants">
            <i className="fas fa-heart"></i> Favorite Restaurants
            {favoriteCount > 0 && <span className="cart-count">{favoriteCount}</span>}
          </a></li>
          <li><a href="/about"><i className="fas fa-info-circle"></i> About Us</a></li>
          <li>
            {!user ? (
              <a href="/login"><i className="fa-regular fa-user"></i> Login</a>
            ) : (
              <>
                <Link to="/profile"><i className="fa-regular fa-user"></i> Profile</Link>
                <span>{user}</span>
              </>
            )}
          </li>
          <li><Link to="/cart">
            <i className="fas fa-shopping-cart"></i> Cart
            {cartItemCount > 0 && <span className="cart-count">{cartItemCount}</span>}
          </Link></li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
