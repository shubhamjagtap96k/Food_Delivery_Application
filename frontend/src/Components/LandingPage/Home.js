import React, { useState } from "react";
import RestaurantList from "./RestaurantList";
import "../../styles/Home.css";
import logo from "../../img/sprintLogo.png";
import Header from "./Header";
 
const Home = () => {
  const [searchQuery, setSearchQuery] = useState("");
 
  const handleSearchChange = (event) => {
    setSearchQuery(event.target.value);
  };
 
  return (
    <>
    <Header />
    <div className="home-container">
      <section className="intro">
        <img src={logo} alt="QuickBites Logo" className="intro-logo" />
        <h1>Welcome to QuickBites!</h1>
        <p>Platform for discovering and ordering from the best restaurants in town.</p>
        <p>With a diverse selection of dining options and a seamless ordering experience, QuickBites is here to satisfy all your food cravings.</p>
        <p>Scroll down to explore our top restaurants and find your next favorite meal!</p>
        {/* Search bar */}
        <div className="search-container">
          <input
            type="text"
            placeholder="Search for restaurants..."
            value={searchQuery}
            onChange={handleSearchChange}
            className="search-input"
          />
        </div>
      </section>
      <section className="restaurant-list-container">
        <RestaurantList searchQuery={searchQuery} />
      </section>
    </div>
    </>
  );
};
 
export default Home;