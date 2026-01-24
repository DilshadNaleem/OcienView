// Dashboard.jsx

import { FaUsers, FaUserEdit, FaChartBar, FaCalendarAlt, FaCog } from 'react-icons/fa';

function Customer_Dashboard() {
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
        <a href="/editProfile" className="dashboard-card">
          <div className="dashboard-menu">
            <div className="card-icon">
              <FaUserEdit />
            </div>
          </div>
          <h3>Edit Profile</h3>
          <p>Update your personal information and settings</p>
          <span className="card-link">Go to Profile →</span>
        </a>
        
        <a href="/analytics" className="dashboard-card">
          <div className="dashboard-menu">
            <div className="card-icon">
              <FaChartBar />
            </div>
          </div>
          <h3>Analytics</h3>
          <p>View detailed reports and insights</p>
          <span className="card-link">View Analytics →</span>
        </a>
        
        <a href="/calendar" className="dashboard-card">
          <div className="dashboard-menu">
            <div className="card-icon">
              <FaCalendarAlt />
            </div>
          </div>
          <h3>Calendar</h3>
          <p>Manage your schedule and appointments</p>
          <span className="card-link">Open Calendar →</span>
        </a>
        
        {/* Add more cards as needed */}
        <a href="/users" className="dashboard-card">
          <div className="dashboard-menu">
            <div className="card-icon">
              <FaUsers />
            </div>
          </div>
          <h3>User Management</h3>
          <p>Manage users and permissions</p>
          <span className="card-link">Manage Users →</span>
        </a>
        
        <a href="/settings" className="dashboard-card">
          <div className="dashboard-menu">
            <div className="card-icon">
              <FaCog />
            </div>
          </div>
          <h3>Settings</h3>
          <p>Configure system preferences</p>
          <span className="card-link">Open Settings →</span>
        </a>
      </div>
    </div>
  );
}

export default Customer_Dashboard;