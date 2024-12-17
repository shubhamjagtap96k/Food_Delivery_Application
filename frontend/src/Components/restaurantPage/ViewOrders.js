import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import '../../styles/ViewOrders.css';
import RestaurantNavbar from './RestaurantNavbar';
 
function ViewOrders() {
  const { restaurantId } = useParams();
  const [orders, setOrders] = useState([]);
  const [drivers, setDrivers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [assigningDriverOrderId, setAssigningDriverOrderId] = useState(null);
  const [selectedDriverId, setSelectedDriverId] = useState(null);
 
  useEffect(() => {
    const fetchOrdersAndDrivers = async () => {
      try {
        const ordersResponse = await axios.get(`http://localhost:8080/api/orders/restaurants/${restaurantId}`);
        setOrders(ordersResponse.data);
 
        const driversResponse = await axios.get('http://localhost:8080/api/drivers');
        setDrivers(driversResponse.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
 
    fetchOrdersAndDrivers();
  }, [restaurantId]);
 
  const handleAssignDriver = async () => {
    try {
      await axios.put(`http://localhost:8080/api/drivers/orders/${assigningDriverOrderId}/assignDriver/${selectedDriverId}`);
     
      // Optionally, re-fetch orders to ensure the latest data
      const ordersResponse = await axios.get(`http://localhost:8080/api/orders/restaurants/${restaurantId}`);
      setOrders(ordersResponse.data);
     
      setAssigningDriverOrderId(null);
      setSelectedDriverId(null);
    } catch (err) {
      setError('Failed to assign driver. Please try again later.');
    }
  };
 
  const startAssigningDriver = (orderId) => {
    setAssigningDriverOrderId(orderId);
  };
 
  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;
 
  return (
    <>
    <RestaurantNavbar />
    <div className="view-orders">
      <h1>List Of Orders</h1>
      {orders.length === 0 ? (
        <p>No orders found.</p>
      ) : (
        <table className="orders-table">
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Item Name</th>
              <th>Quantity</th>
              <th>Restaurant ID</th>
              <th>Price</th>
              <th>Customer ID</th>
              <th>Order Time and Date</th>
              <th>Delivery Driver</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {orders.map(order => (
              order.orderItems.map(item => (
                <tr key={`${order.orderId}-${item.orderItemId}`}>
                  <td>{order.orderId}</td>
                  <td>{item.itemName}</td>
                  <td>{item.quantity}</td>
                  <td>{order.restaurantId}</td>
                  <td>{item.price ? `$${item.price.toFixed(2)}` : 'N/A'}</td>
                  <td>{order.customerId || 'N/A'}</td>
                  <td>{new Date(order.orderDate).toLocaleString()}</td>
                  <td>
                    {order.deliveryDriverId ? (
                      `Driver ID: ${order.deliveryDriverId}`
                    ) : (
                      'Pending'
                    )}
                  </td>
                  <td>
                    {assigningDriverOrderId === order.orderId ? (
                      <>
                        <select
                          onChange={(e) => setSelectedDriverId(Number(e.target.value))}
                          value={selectedDriverId || ''}
                        >
                          <option value="">Select Driver</option>
                          {drivers.map(driver => (
                            <option key={driver.driverId} value={driver.driverId}>
                              {driver.driverName}
                            </option>
                          ))}
                        </select>
                        <button onClick={handleAssignDriver} className="btn btn-full">Assign Driver</button>
                        <button onClick={() => setAssigningDriverOrderId(null)} className="btn btn-cancel">Cancel</button>
                      </>
                    ) : (
                      <button onClick={() => startAssigningDriver(order.orderId)}>Assign Driver</button>
                    )}
                  </td>
                </tr>
              ))
            ))}
          </tbody>
        </table>
      )}
    </div>
    </>
  );
}
 
export default ViewOrders;