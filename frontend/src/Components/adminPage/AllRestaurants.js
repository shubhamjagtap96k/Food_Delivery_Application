import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../../styles/AllRestaurants.css';
import AdminNavbar from './AdminNavbar';

const AllRestaurants = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/restaurants');
        setRestaurants(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchRestaurants();
  }, []);

  const deleteRestaurant = async (restaurantId) => {
    try {
      await axios.delete(`http://localhost:8080/api/restaurants/${restaurantId}`);
      setRestaurants(restaurants.filter(r => r.restaurantId !== restaurantId));
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <>
      <AdminNavbar />
      <div className="all-restaurants">
        <h1>All Restaurants</h1>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Address</th>
              <th>Phone</th>
              <th>Delivery Areas</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {restaurants.map((restaurant) => (
              <tr key={restaurant.restaurantId}>
                <td>{restaurant.restaurantId}</td>
                <td>{restaurant.restaurantName}</td>
                <td>{restaurant.restaurantAddress}</td>
                <td>{restaurant.restaurantPhone}</td>
                <td>
                  {Array.isArray(restaurant.deliveryAreas) 
                    ? restaurant.deliveryAreas.join(', ') 
                    : 'N/A'}
                </td>
                <td>
                  <button onClick={() => deleteRestaurant(restaurant.restaurantId)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default AllRestaurants;
