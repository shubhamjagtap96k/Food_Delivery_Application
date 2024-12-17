import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../../styles/AllCustomers.css';
import AdminNavbar from './AdminNavbar';

function AllCustomers() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCustomers = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/customers');
        setCustomers(response.data);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCustomers();
  }, []);

  const handleDelete = async (customerId) => {
    try {
      await axios.delete(`http://localhost:8080/api/customers/${customerId}`);
      setCustomers(customers.filter(customer => customer.customerId !== customerId));
    } catch (err) {
      setError(err);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error loading customers: {error.message}</p>;

  return (
    <>
    <AdminNavbar />
    <div className="all-customers">
      <h1>All Customers</h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {customers.length === 0 ? (
            <tr>
              <td colSpan="5">No customers found</td>
            </tr>
          ) : (
            customers.map(customer => (
              <tr key={customer.customerId}>
                <td>{customer.customerId}</td>
                <td>{customer.customerName}</td>
                <td>{customer.customerEmail}</td>
                <td>{customer.customerPhone}</td>
                <td>
                  <button onClick={() => handleDelete(customer.customerId)}>Delete</button>
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

export default AllCustomers;
