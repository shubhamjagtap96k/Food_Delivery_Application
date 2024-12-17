import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../../styles/FavoriteRestaurants.css"; // For styling
import Header from "./Header";
 
const FavoriteRestaurants = () => {
  const [favorites, setFavorites] = useState([]);
 
  useEffect(() => {
    // Retrieve favorites from localStorage
    const storedFavorites = JSON.parse(localStorage.getItem("favorites")) || [];
    setFavorites(storedFavorites);
  }, []);
 
  return (
    <>
    <Header />
    <section className="favorite-restaurants">
      <h2>Your Favorite Restaurants</h2>
      <div className="restaurant-cards">
        {favorites.length > 0 ? (
          favorites.map((restaurant) => (
            <div className="restaurant-card" key={restaurant.restaurantId}>
              <Link to={`/restaurant-menu/${restaurant.restaurantId}`} className="card-link">
                <div className="image-container">
                  <img
                    src={restaurant.image}
                    alt={restaurant.restaurantName}
                    className="restaurant-image"
                  />
                  <div className="favorite-icon favorited">
                    <i className="fas fa-heart"></i>
                  </div>
                </div>
                <h3>{restaurant.restaurantName}</h3>
                <p>{restaurant.description}</p>
                <p className="address">
                  <strong>Location:</strong> {restaurant.restaurantAddress}
                </p>
              </Link>
            </div>
          ))
        ) : (
          <p className="no-items">You have no favorite restaurants yet.</p>
        )}
      </div>
    </section>
    </>
  );
};
 
export default FavoriteRestaurants;
 