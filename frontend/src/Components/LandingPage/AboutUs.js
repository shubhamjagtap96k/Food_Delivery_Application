import React from 'react';
import '../../styles/AboutUs.css'; // For styling
import base64 from '../../img/base64';
import base from '../../img/base';

 
const AboutUs = () => {
  return (
    <section className="about-us">
      <h2>About Us</h2>
      <div className="about-content">
        <p>Welcome to QuickBites, your ultimate destination for a seamless food delivery experience. We are passionate about bringing the best dining experiences to your doorstep. Our curated selection of restaurants offers a diverse range of cuisines, from local favorites to international delights, catering to every palate and preference.</p>
        <p>At QuickBites, we are committed to providing top-notch service, ensuring that your food arrives hot, fresh, and on time. Our user-friendly app makes it easy to browse menus, place orders, and track deliveries, all with just a few taps on your phone.</p>
        <p>Join our community of food lovers and experience the convenience and quality that QuickBites has to offer. Whether you're craving a hearty meal or a quick snack, we've got you covered!</p>
        <div className="about-images">
          <img src={base}alt="Delicious food" className="about-image" />
          <img src={base64}alt="Food delivery" className="about-image" />
        </div>
      </div>
    </section>
  );
};
 
export default AboutUs;
