// Dashboard.jsx
import "./Dashboard.css";
import { FaUsers, FaUserEdit, FaChartBar, FaCalendarAlt, FaCog } from 'react-icons/fa';

function Dashboard() {
  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h1>Welcome to the Dashboard</h1>
        <p>Here's what's happening with your business today.</p>
      </div>
      
      {/* Stats Section */}
      <div className="dashboard-stats">
        <div className="stat-card">
          <h3>Total Users</h3>
          <p>1,234</p>
        </div>
        <div className="stat-card">
          <h3>Revenue</h3>
          <p>$12,345</p>
        </div>
        <div className="stat-card">
          <h3>Active Projects</h3>
          <p>24</p>
        </div>
        <div className="stat-card">
          <h3>Growth</h3>
          <p>+15%</p>
        </div>
      </div>
      
      {/* Main Dashboard Grid */}
      <div className="dashboard-grid">
        <div className="dashboard-card">
          <a href="/editProfile">
          <div className="dashboard-menu">
          <div className="card-icon">
            <FaUserEdit />
          </div>
          </div>
          <h3>Edit Profile</h3>
          <p>Update your personal information and settings</p>
          <a href="/editProfile" className="card-link">Go to Profile →</a>
          </a>
        </div>
        
      
       
      <div className="dashboard-card">
          <a href="/editProfile">
          <div className="dashboard-menu">
          <div className="card-icon">
            <FaChartBar />
          </div>
          </div>
          <h3>Edit Profile</h3>
          <p>Update your personal information and settings</p>
          <a href="/editProfile" className="card-link">Go to Profile →</a>
          </a>
        </div>
        
        
       <div className="dashboard-card">
          <a href="/editProfile">
          <div className="dashboard-menu">
          <div className="card-icon">
            <FaCalendarAlt />
          </div>
          </div>
          <h3>Edit Profile</h3>
          <p>Update your personal information and settings</p>
          <a href="/editProfile" className="card-link">Go to Profile →</a>
          </a>
        </div>
        
        
        
        
      </div>
    </div>
  );
}

export default Dashboard;