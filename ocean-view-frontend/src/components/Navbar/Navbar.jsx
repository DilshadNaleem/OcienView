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
  FaUserEdit,
  FaJediOrder,
  FaFirstOrder
} from 'react-icons/fa';

// Import useTheme hook
import { useTheme } from "../ThemeContext.jsx";
import { useLocation } from "react-router-dom";

function Navbar() {
  // Use theme context
  const { darkMode, toggleTheme } = useTheme();
  const location = useLocation();

  const isActive = (path) => {
    if(path === "/dashboard") {
      return location.pathname === "/" || location.pathname === "/dashboard";
    }

    return location.pathname === path || location.pathname.startsWith(path + "/");
  };
  
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
        <a 
          href="/dashboard" 
          className={`nav-link ${isActive("/dashboard") ? "active" : ""}`}
        >
          <FaTachometerAlt className="nav-icon" />
          <span>Dashboard</span>
        </a>
        <a 
          href="/editProfile" 
          className={`nav-link ${isActive("/editProfile") ? "active" : ""}`}
        >
          <FaUserEdit className="nav-icon" />
          <span>Edit Profile</span>
        </a>
        <a 
          href="/users" 
          className={`nav-link ${isActive("/users") ? "active" : ""}`}
        >
          <FaUsers className="nav-icon" />
          <span>Users</span>
        </a>
        <a 
          href="/reservations" 
          className={`nav-link ${isActive("/reservations") ? "active" : ""}`}
        >
          <FaFirstOrder className="nav-icon" />
          <span>Reservations</span>
        </a>
        <a 
          href="/fine" 
          className={`nav-link ${isActive("/fine") ? "active" : ""}`}
        >
          <FaMoneyBill className="nav-icon" />
          <span>Orders</span>
        </a>
        <a 
          href="/settings" 
          className={`nav-link ${isActive("/settings") ? "active" : ""}`}
        >
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