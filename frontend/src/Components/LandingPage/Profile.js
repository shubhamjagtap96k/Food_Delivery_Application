import React, { useState, useEffect } from 'react';
import '../../styles/Profile.css'; // Add styling for the profile page
import instance from '../setup/axios'; // Adjust the path if needed
import { useRecoilValue } from 'recoil';
import { userState } from '../setup/Recoil'; // Adjust the path if needed

const Profile = () => {
  const [userDetails, setUserDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const user = useRecoilValue(userState);

  useEffect(() => {
    if (user) {
      fetchUserDetails(user); // Fetch user details based on user ID
    }
  }, [user]);

  const fetchUserDetails = async (userId) => {
    try {
      const response = await instance.get(`/api/customers/${userId}`);
      setUserDetails(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching user details:', error);
      setError('Failed to load user details.');
      setLoading(false);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <section className="profile">
      <h2>User Profile</h2>
      {userDetails ? (
        <div className="profile-details">
          <p><strong>Name:</strong> {userDetails.customerName}</p>
          <p><strong>Email:</strong> {userDetails.customerEmail}</p>
          <p><strong>Phone:</strong> {userDetails.customerPhone}</p>
          <p><strong>Address:</strong> {userDetails.deliveryAddresses.map(address => (
            <span key={address.id}>{address.addressLine}, {address.city}, {address.state}, {address.zipcode}</span>
          ))}</p>
        </div>
      ) : (
        <p>No user details available.</p>
      )}
    </section>
  );
};

export default Profile;
