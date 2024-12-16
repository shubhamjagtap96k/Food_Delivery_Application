import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../../styles/RestaurantList.css"; // Ensure path is correct
import instance from "../setup/axios"; // Ensure path is correct

const RestaurantList = ({ searchQuery }) => {
  const [restaurants, setRestaurants] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch restaurants with error handling
    instance.get(`/api/restaurants`)
      .then((response) => {
        setRestaurants(response.data);
      })
      .catch((error) => {
        console.error('Error fetching restaurants:', error);
        setError('Failed to load restaurants.');
      });
  }, []);

  const toggleFavorite = (restaurant) => {
    let favorites = JSON.parse(localStorage.getItem("favorites")) || [];

    if (favorites.some((fav) => fav.restaurantId === restaurant.restaurantId)) {
      favorites = favorites.filter((fav) => fav.restaurantId !== restaurant.restaurantId);
    } else {
      favorites.push(restaurant);
    }

    localStorage.setItem("favorites", JSON.stringify(favorites));
  };

  const isFavorite = (id) => {
    const favorites = JSON.parse(localStorage.getItem("favorites")) || [];
    return favorites.some((fav) => fav.restaurantId === id);
  };

  return (
    <section className="restaurant-list">
      <h2>Our Restaurants</h2>
      {error && <p className="error-message">{error}</p>}
      <div className="restaurant-cards">
        {restaurants
          .filter(r => r.restaurantName.toLowerCase().includes(searchQuery.toLowerCase()))
          .map((restaurant) => (
            <div className="restaurant-card" key={restaurant.restaurantId}>
              <Link to={`/restaurant-menu/${restaurant.restaurantId}`} className="card-link">
                <div className="image-container">
                  <img
                    src={(restaurant.image) || (`https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJ_ejK5ccqJOOjzIaEPltNbVNpJRFDYJb6Y8wVQFCZhaAbbOHPiZNt06KveQ&s`)}
                    alt={restaurant.restaurantName}
                    className="restaurant-image"
                  />
                  <div
                    className={`favorite-icon ${isFavorite(restaurant.restaurantId) ? 'favorited' : ''}`}
                    onClick={(e) => {
                      e.stopPropagation();
                      toggleFavorite(restaurant);
                    }}
                  >
                    <i className="fas fa-bookmark"></i>
                  </div>
                </div>
                <h3>{restaurant.restaurantName}</h3>
                <div className="restaurant-info">
                  {/* <span className="rating">⭐ {restaurant.averageRating || 'No ratings'}</span> */}
                </div>
                <p>{restaurant.description}</p>
                <p className="address">
                  <strong>Location:</strong> {restaurant.restaurantAddress}
                </p>
              </Link>
            </div>
          ))}
      </div>
    </section>
  );
};

export default RestaurantList;
