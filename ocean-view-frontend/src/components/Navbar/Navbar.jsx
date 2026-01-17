import "./Navbar.css";
import pImage from "../../assets/image/p.jpg";

// Import icons
import { 
  FaTachometerAlt, 
  FaUsers, 
  FaBox, 
  FaShoppingCart, 
  FaCog,
  FaSun,
  FaMoon, 
  FaTaxi,
  FaMoneyBill,
  FaPersonBooth,
  FaMale,
  FaUserEdit
} from 'react-icons/fa';

// Import useTheme hook
import { useTheme } from "../ThemeContext.jsx";

function Navbar() {
  // Use theme context
  const { darkMode, toggleTheme } = useTheme();

  return (
    <div className="navbar">
      {/* Admin Panel Section */}
      <div className="admin-panel">
        <div className="admin-img">
          <img src={pImage} alt="Admin" />
          <div className="admin-name">Admin Name</div>
        </div>
      </div>

      {/* Navigation Links */}
      <div className="nav-links">
        <a href="/dashboard" className="nav-link">
          <FaTachometerAlt className="nav-icon" />
          <span>Dashboard</span>
        </a>
         <a href="/editProfile" className="nav-link">
          <FaUserEdit className="nav-icon" />
          <span>Edit Profile</span>
        </a>
        <a href="/users" className="nav-link">
          <FaUsers className="nav-icon" />
          <span>Users</span>
        </a>
        <a href="/reservations" className="nav-link">
          <FaMoneyBill className="nav-icon" />
          <span>Reservations</span>
        </a>
        <a href="#" className="nav-link">
          <FaShoppingCart className="nav-icon" />
          <span>Orders</span>
        </a>
        <a href="#" className="nav-link">
          <FaCog className="nav-icon" />
          <span>Settings</span>
        </a>
      </div>

      {/* Theme Toggle Button */}
      <div className="theme-toggle-container">
        <button 
          className="theme-toggle-btn" 
          onClick={toggleTheme}
          aria-label={darkMode ? "Switch to light mode" : "Switch to dark mode"}
        >
          {darkMode ? (
            <>
              <FaSun className="theme-icon" />
              <span>Light Mode</span>
            </>
          ) : (
            <>
              <FaMoon className="theme-icon" />
              <span>Dark Mode</span>
            </>
          )}
        </button>
      </div>
    </div>
  );
}

export default Navbar;