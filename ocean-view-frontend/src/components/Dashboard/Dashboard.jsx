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
          <div className="card-icon">
            <FaUserEdit />
          </div>
          <h3>Edit Profile</h3>
          <p>Update your personal information and settings</p>
          <a href="#" className="card-link">Go to Profile →</a>
        </div>
        
        <div className="dashboard-card">
          <div className="card-icon">
            <FaChartBar />
          </div>
          <h3>Analytics</h3>
          <p>View detailed reports and performance metrics</p>
          <a href="#" className="card-link">View Analytics →</a>
        </div>
        
        <div className="dashboard-card">
          <div className="card-icon">
            <FaCalendarAlt />
          </div>
          <h3>Calendar</h3>
          <p>Manage your schedule and appointments</p>
          <a href="#" className="card-link">Open Calendar →</a>
        </div>
        
        <div className="dashboard-card">
          <div className="card-icon">
            <FaCog />
          </div>
          <h3>Settings</h3>
          <p>Configure system preferences and options</p>
          <a href="#" className="card-link">Settings →</a>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;