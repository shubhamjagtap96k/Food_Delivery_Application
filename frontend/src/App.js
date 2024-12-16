import React, { useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import AdminDashboard from './Components/adminPage/AdminDashboard';
import RestaurantRegistrationForm from './Components/adminPage/RestaurantRegistrationForm';
import AllRestaurants from './Components/adminPage/AllRestaurants';
import AllDeliveryDrivers from './Components/adminPage/AllDeliveryDrivers';
import AllCustomers from './Components/adminPage/AllCustomers';
import RestaurantDashboard from './Components/restaurantPage/RestaurantDashboard';
import ViewMenu from './Components/restaurantPage/ViewMenu';
import AddMenuItem from './Components/restaurantPage/AddMenuItem';
import ViewOrders from './Components/restaurantPage/ViewOrders';
import RegisterAdmin from './Components/adminPage/RegisterAdmin';
import Login from './Components/login/Login';
import Signup from './Components/login/SignUp';
import AboutUs from './Components/LandingPage/AboutUs';
import Home from './Components/LandingPage/Home';
import FavoriteRestaurants from './Components/LandingPage/FavoriteRestaurants';
import RestaurantMenu from './Components/LandingPage/RestaurantMenu';
import RestaurantList from './Components/LandingPage/RestaurantList';
import Cart from './Components/LandingPage/Cart';
import Header from './Components/LandingPage/Header';
import Profile from './Components/LandingPage/Profile';
import Checkout from './Components/LandingPage/CheckOut';


function App() {
  const [favorites, setFavorites] = useState([]); // State to track favorite restaurants
 
  const addToFavorites = (restaurant) => {
    if (!favorites.some(fav => fav.restaurantId === restaurant.restaurantId)) {
      setFavorites([...favorites, restaurant]); // Add restaurant if not already in favorites
    }
  };
  return (
    <BrowserRouter>
      <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />
        
        <Route path="/adminpage" element={<AdminDashboard />} />
        <Route path="/adminpage/all-restaurants" element={<AllRestaurants />} />
        <Route path="/adminpage/register-admin" element={<RegisterAdmin />} />
        <Route path="/adminpage/all-delivery-drivers" element={<AllDeliveryDrivers />} />
        <Route path="/adminpage/all-customers" element={<AllCustomers />} />
        <Route path="/adminpage/register-restaurant" element={<RestaurantRegistrationForm />} />
        
        <Route path="/restaurantpage/:restaurantId" element={<RestaurantDashboard />} />
        {/* <Route path="/restaurantpage/:restaurantId/view-deliverydrivers" element={<AllDeliveryDrivers />} /> */}
        <Route path="/restaurantpage/:restaurantId/view-menu" element={<ViewMenu />} />
        <Route path="/restaurantpage/:restaurantId/add-menu" element={<AddMenuItem />} />
        <Route path="/restaurantpage/:restaurantId/view-orders" element={<ViewOrders />} />

            {/* <Header /> */}
            <Route path="/home" element={<Home />} />
            <Route path="/about" element={<AboutUs />} />
            <Route path="/favorite-restaurants" element={<FavoriteRestaurants favorites={favorites} />} /> {/* Pass favorites to component */}
            {/* <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} /> */}
            <Route path="/restaurant-menu/:restaurantId" element={<RestaurantMenu />} />
            <Route path="/restaurants" element={<RestaurantList addToFavorites={addToFavorites} />} /> {/* Pass addToFavorites to component */}
            <Route path="/cart" element={<Cart />} />
            <Route path="/profile" element={<Profile/>} />
            <Route path="/checkout" element={<Checkout />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
