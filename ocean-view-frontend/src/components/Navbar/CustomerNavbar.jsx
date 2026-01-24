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
  FaFirstOrder,
  FaHistory,
  FaAd,
  FaLifeRing,
  FaLongArrowAltUp,
  FaLongArrowAltDown
} from 'react-icons/fa';

// Import useTheme hook
import { useTheme } from "../ThemeContext.jsx";
import { useLocation } from "react-router-dom";

function CustomerNavbar() {
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
          <div className="admin-name">Customer Name</div>
        </div>
      </div>

      {/* Navigation Links */}
      <div className="nav-links">
        <a 
          href="/Customer/Dashboard" 
          className={`nav-link ${isActive("/Customer/Dashboard") ? "active" : ""}`}
        >
          <FaTachometerAlt className="nav-icon" />
          <span>Dashboard</span>
        </a>
        <a 
          href="/Customer/editProfile" 
          className={`nav-link ${isActive("/Customer/editProfile") ? "active" : ""}`}
        >
          <FaUserEdit className="nav-icon" />
          <span>Edit Profile</span>
        </a>
        <a 
          href="/Customer/history" 
          className={`nav-link ${isActive("/Customer/history") ? "active" : ""}`}
        >
          <FaHistory className="nav-icon" />
          <span>History</span>
        </a>
        <a 
          href="/Customer/reservations" 
          className={`nav-link ${isActive("/Customer/reservations") ? "active" : ""}`}
        >
          <FaFirstOrder className="nav-icon" />
          <span>Reservations</span>
        </a>
        <a 
          href="/Customer/helpSection" 
          className={`nav-link ${isActive("/Customer/helpSection") ? "active" : ""}`}
        >
          <FaLifeRing className="nav-icon" />
          <span>Help</span>
        </a>

        <a 
          href="/Customer/settings" 
          className={`nav-link ${isActive("/Customer/settings") ? "active" : ""}`}
        >
          <FaCog className="nav-icon" />
          <span>Settings</span>
        </a>

         <a 
          href="/Customer/logout" 
          className={`nav-link ${isActive("/Customer/logout") ? "active" : ""}`}
        >
          <FaLongArrowAltDown className="nav-icon" />
          <span>Logout</span>
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

export default CustomerNavbar;