import React from 'react';
import '../../styles/ContactUs.css'; 
 
const ContactUs = () => {
  return (
    <section className="contact-us">
      <h2>Contact Us</h2>
      <p>We'd love to hear from you! Here’s how you can get in touch with us:</p>
      <ul>
        <li>
          <strong>Phone:</strong> +91 932458483
        </li>
        <li>
          <strong>Email:</strong> support@quickbites.com
        </li>
        <li>
          <strong>Address:</strong> Hinjewadi Phase 3 Pune Maharashtra
        </li>
        <li>
          <strong>Business Hours:</strong> Mon-Fri 9:00 AM - 6:00 PM
        </li>
      </ul>
    </section>
  );
};
 
export default ContactUs;