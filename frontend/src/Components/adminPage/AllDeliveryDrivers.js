import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminNavbar from './AdminNavbar';
import '../../styles/AllDeliveryDrivers.css';
 
function AllDeliveryDrivers() {
  const [drivers, setDrivers] = useState([]);
  const [filteredDrivers, setFilteredDrivers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editingDriver, setEditingDriver] = useState(null);
  const [newLocation, setNewLocation] = useState('');
  const [searchId, setSearchId] = useState('');
 
  useEffect(() => {
    const fetchDrivers = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/drivers');
        setDrivers(response.data);
        setFilteredDrivers(response.data);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };
 
    fetchDrivers();
  }, []);
 
  const handleEditClick = (driver) => {
    setEditingDriver(driver);
    setNewLocation(driver.driverLocation);
  };
 
  const handleSaveLocation = async () => {
    try {
      await axios.put(`http://localhost:8080/api/drivers/${editingDriver.driverId}/location`, { driverLocation: newLocation });
      setDrivers(drivers.map(driver =>
        driver.driverId === editingDriver.driverId ? { ...driver, driverLocation: newLocation } : driver
      ));
      setFilteredDrivers(filteredDrivers.map(driver =>
        driver.driverId === editingDriver.driverId ? { ...driver, driverLocation: newLocation } : driver
      ));
      setEditingDriver(null);
      setError(null);
    } catch (err) {
      setError(err);
    }
  };
 
  const handleSearch = async () => {
    if (!searchId) {
      setFilteredDrivers(drivers);
      setError(null);
      return;
    }
 
    try {
      const response = await axios.get(`http://localhost:8080/api/drivers/${searchId}`);
      setFilteredDrivers([response.data]);
      setError(null);
    } catch (err) {
      setFilteredDrivers([]);
      setError({ message: 'No driver with that ID found' });
    }
  };
 
  if (loading) return <p>Loading...</p>;
 
  return (
    <>
      <AdminNavbar />
      <div className="all-drivers">
        <h1>All Delivery Drivers</h1>
        <div className="search-bar">
          <input
            type="text"
            placeholder="Search by ID"
            value={searchId}
            onChange={(e) => setSearchId(e.target.value)}
          />
          <button onClick={handleSearch}>Search</button>
        </div>
        {error && <p className="error-message">{error.message}</p>}
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Phone</th>
              <th>Vehicle</th>
              <th>Location</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredDrivers.length === 0 ? (
              <tr>
                <td colSpan="6">No drivers found</td>
              </tr>
            ) : (
              filteredDrivers.map(driver => (
                <tr key={driver.driverId}>
                  <td>{driver.driverId}</td>
                  <td>{driver.driverName}</td>
                  <td>{driver.driverPhone}</td>
                  <td>{driver.driverVehicle}</td>
                  <td>
                    {editingDriver && editingDriver.driverId === driver.driverId ? (
                      <input
                        type="text"
                        value={newLocation}
                        onChange={(e) => setNewLocation(e.target.value)}
                      />
                    ) : (
                      driver.driverLocation
                    )}
                  </td>
                  <td>
                    {editingDriver && editingDriver.driverId === driver.driverId ? (
                      <>
                        <button onClick={handleSaveLocation}>Save</button>
                        <button onClick={() => setEditingDriver(null)}>Cancel</button>
                      </>
                    ) : (
                      <button onClick={() => handleEditClick(driver)}>Edit Location</button>
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </>
  );
}
 
export default AllDeliveryDrivers;
 
 