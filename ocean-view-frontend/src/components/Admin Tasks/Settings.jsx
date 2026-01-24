import React, { useState } from 'react';
import './Settings.css';
import {
  FaUser,
  FaBell,
  FaPalette,
  FaShieldAlt,
  FaLanguage,
  FaMoon,
  FaSun,
  FaKey,
  FaGlobe,
  FaSave,
  FaTrash,
  FaLock,
  FaEye,
  FaEyeSlash,
  FaCheckCircle,
  FaVolumeUp,
  FaVolumeMute
} from 'react-icons/fa';
import { MdNotifications, MdSecurity, MdPrivacyTip } from 'react-icons/md';

const Set = () => {
  const [activeTab, setActiveTab] = useState('profile');
  const [darkMode, setDarkMode] = useState(false);
  const [notifications, setNotifications] = useState(true);
  const [twoFactorAuth, setTwoFactorAuth] = useState(false);
  const [language, setLanguage] = useState('english');
  const [showOldPassword, setShowOldPassword] = useState(false);
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [passwordData, setPasswordData] = useState({
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  });

  const tabs = [
    { id: 'profile', label: 'Profile', icon: <FaUser /> },
    { id: 'security', label: 'Security', icon: <FaShieldAlt /> },
    { id: 'notifications', label: 'Notifications', icon: <FaBell /> },
    { id: 'appearance', label: 'Appearance', icon: <FaPalette /> },
    { id: 'language', label: 'Language', icon: <FaLanguage /> },
    { id: 'privacy', label: 'Privacy', icon: <MdPrivacyTip /> }
  ];

  const languages = [
    { code: 'english', label: 'English' },
    { code: 'spanish', label: 'Spanish' },
    { code: 'french', label: 'French' },
    { code: 'german', label: 'German' },
    { code: 'japanese', label: 'Japanese' }
  ];

  const handlePasswordChange = (e) => {
    const { name, value } = e.target;
    setPasswordData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle settings save logic here
    console.log('Settings saved');
  };

  return (
    <div className={`settings-container ${darkMode ? 'dark-mode' : ''}`}>
      <div className="settings-header">
        <h1><FaUser className="header-icon" /> Settings</h1>
        <p className="settings-subtitle">Manage your account preferences and security settings</p>
      </div>

      <div className="settings-content">
        {/* Sidebar Navigation */}
        <div className="settings-sidebar">
          <div className="sidebar-tabs">
            {tabs.map(tab => (
              <button
                key={tab.id}
                className={`tab-button ${activeTab === tab.id ? 'active' : ''}`}
                onClick={() => setActiveTab(tab.id)}
              >
                <span className="tab-icon">{tab.icon}</span>
                {tab.label}
              </button>
            ))}
          </div>
          
          <div className="sidebar-actions">
            <button className="save-button" onClick={handleSubmit}>
              <FaSave /> Save Changes
            </button>
            <button className="reset-button">
              <FaTrash /> Reset to Default
            </button>
          </div>
        </div>

        {/* Main Settings Panel */}
        <div className="settings-panel">
          {/* Profile Settings */}
          {activeTab === 'profile' && (
            <div className="settings-section">
              <h2 className="section-title">
                <FaUser /> Profile Information
              </h2>
              <form className="settings-form">
                <div className="form-group">
                  <label>Full Name</label>
                  <input type="text" placeholder="Enter your full name" />
                </div>
                <div className="form-group">
                  <label>Email Address</label>
                  <input type="email" placeholder="Enter your email" />
                </div>
                <div className="form-group">
                  <label>Phone Number</label>
                  <input type="tel" placeholder="Enter your phone number" />
                </div>
                <div className="form-group">
                  <label>Bio</label>
                  <textarea 
                    placeholder="Tell us about yourself"
                    rows="4"
                  ></textarea>
                </div>
              </form>
            </div>
          )}

          {/* Security Settings */}
          {activeTab === 'security' && (
            <div className="settings-section">
              <h2 className="section-title">
                <FaShieldAlt /> Security Settings
              </h2>
              
              <div className="security-section">
                <h3><FaLock /> Change Password</h3>
                <form className="password-form">
                  <div className="form-group password-group">
                    <label>Old Password</label>
                    <div className="password-input-wrapper">
                      <input
                        type={showOldPassword ? "text" : "password"}
                        name="oldPassword"
                        value={passwordData.oldPassword}
                        onChange={handlePasswordChange}
                        placeholder="Enter old password"
                      />
                      <button
                        type="button"
                        className="password-toggle"
                        onClick={() => setShowOldPassword(!showOldPassword)}
                      >
                        {showOldPassword ? <FaEyeSlash /> : <FaEye />}
                      </button>
                    </div>
                  </div>
                  
                  <div className="form-group password-group">
                    <label>New Password</label>
                    <div className="password-input-wrapper">
                      <input
                        type={showNewPassword ? "text" : "password"}
                        name="newPassword"
                        value={passwordData.newPassword}
                        onChange={handlePasswordChange}
                        placeholder="Enter new password"
                      />
                      <button
                        type="button"
                        className="password-toggle"
                        onClick={() => setShowNewPassword(!showNewPassword)}
                      >
                        {showNewPassword ? <FaEyeSlash /> : <FaEye />}
                      </button>
                    </div>
                  </div>
                  
                  <div className="form-group password-group">
                    <label>Confirm New Password</label>
                    <div className="password-input-wrapper">
                      <input
                        type={showConfirmPassword ? "text" : "password"}
                        name="confirmPassword"
                        value={passwordData.confirmPassword}
                        onChange={handlePasswordChange}
                        placeholder="Confirm new password"
                      />
                      <button
                        type="button"
                        className="password-toggle"
                        onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                      >
                        {showConfirmPassword ? <FaEyeSlash /> : <FaEye />}
                      </button>
                    </div>
                  </div>
                  
                  <button type="submit" className="update-password-btn">
                    <FaKey /> Update Password
                  </button>
                </form>
              </div>

              <div className="security-section">
                <h3><MdSecurity /> Two-Factor Authentication</h3>
                <div className="toggle-section">
                  <div className="toggle-info">
                    <p>Add an extra layer of security to your account</p>
                    <span className="toggle-status">
                      {twoFactorAuth ? 'Enabled' : 'Disabled'}
                    </span>
                  </div>
                  <label className="toggle-switch">
                    <input
                      type="checkbox"
                      checked={twoFactorAuth}
                      onChange={() => setTwoFactorAuth(!twoFactorAuth)}
                    />
                    <span className="toggle-slider"></span>
                  </label>
                </div>
              </div>
            </div>
          )}

          {/* Notifications Settings */}
          {activeTab === 'notifications' && (
            <div className="settings-section">
              <h2 className="section-title">
                <MdNotifications /> Notification Preferences
              </h2>
              
              <div className="toggle-section">
                <div className="toggle-info">
                  <h4>Email Notifications</h4>
                  <p>Receive updates via email</p>
                </div>
                <label className="toggle-switch">
                  <input
                    type="checkbox"
                    checked={notifications}
                    onChange={() => setNotifications(!notifications)}
                  />
                  <span className="toggle-slider"></span>
                </label>
              </div>

              <div className="toggle-section">
                <div className="toggle-info">
                  <h4>SMS Notifications</h4>
                  <p>Receive updates via SMS</p>
                </div>
                <label className="toggle-switch">
                  <input
                    type="checkbox"
                    checked={true}
                    onChange={() => {}}
                  />
                  <span className="toggle-slider"></span>
                </label>
              </div>

              <div className="toggle-section">
                <div className="toggle-info">
                  <h4>Push Notifications</h4>
                  <p>Receive browser notifications</p>
                </div>
                <label className="toggle-switch">
                  <input
                    type="checkbox"
                    checked={true}
                    onChange={() => {}}
                  />
                  <span className="toggle-slider"></span>
                </label>
              </div>

              <div className="toggle-section">
                <div className="toggle-info">
                  <h4>Sound Effects</h4>
                  <p>Play sounds for notifications</p>
                </div>
                <div className="sound-toggle">
                  <button className="sound-btn" title="Mute">
                    <FaVolumeMute />
                  </button>
                  <button className="sound-btn active" title="Unmute">
                    <FaVolumeUp />
                  </button>
                </div>
              </div>
            </div>
          )}

          {/* Appearance Settings */}
          {activeTab === 'appearance' && (
            <div className="settings-section">
              <h2 className="section-title">
                <FaPalette /> Appearance Settings
              </h2>
              
              <div className="toggle-section">
                <div className="toggle-info">
                  <h4>Dark Mode</h4>
                  <p>Switch between light and dark themes</p>
                  <span className="theme-icon">
                    {darkMode ? <FaMoon /> : <FaSun />}
                  </span>
                </div>
                <label className="toggle-switch">
                  <input
                    type="checkbox"
                    checked={darkMode}
                    onChange={() => setDarkMode(!darkMode)}
                  />
                  <span className="toggle-slider"></span>
                </label>
              </div>

              <div className="color-scheme">
                <h4>Color Scheme</h4>
                <div className="color-options">
                  <div className="color-option blue active">
                    <div className="color-preview"></div>
                    <span>Blue</span>
                    <FaCheckCircle className="check-icon" />
                  </div>
                  <div className="color-option green">
                    <div className="color-preview"></div>
                    <span>Green</span>
                  </div>
                  <div className="color-option purple">
                    <div className="color-preview"></div>
                    <span>Purple</span>
                  </div>
                  <div className="color-option red">
                    <div className="color-preview"></div>
                    <span>Red</span>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* Language Settings */}
          {activeTab === 'language' && (
            <div className="settings-section">
              <h2 className="section-title">
                <FaGlobe /> Language & Region
              </h2>
              
              <div className="form-group">
                <label>Select Language</label>
                <select 
                  value={language}
                  onChange={(e) => setLanguage(e.target.value)}
                  className="language-select"
                >
                  {languages.map(lang => (
                    <option key={lang.code} value={lang.code}>
                      {lang.label}
                    </option>
                  ))}
                </select>
              </div>

              <div className="form-group">
                <label>Time Zone</label>
                <select className="language-select">
                  <option>UTC-5 (Eastern Time)</option>
                  <option>UTC-6 (Central Time)</option>
                  <option>UTC-7 (Mountain Time)</option>
                  <option>UTC-8 (Pacific Time)</option>
                </select>
              </div>

              <div className="form-group">
                <label>Date Format</label>
                <div className="format-options">
                  <button className="format-btn active">MM/DD/YYYY</button>
                  <button className="format-btn">DD/MM/YYYY</button>
                  <button className="format-btn">YYYY-MM-DD</button>
                </div>
              </div>
            </div>
          )}

          {/* Privacy Settings */}
          {activeTab === 'privacy' && (
            <div className="settings-section">
              <h2 className="section-title">
                <MdPrivacyTip /> Privacy Settings
              </h2>
              
              <div className="toggle-section">
                <div className="toggle-info">
                  <h4>Profile Visibility</h4>
                  <p>Make your profile visible to other users</p>
                </div>
                <label className="toggle-switch">
                  <input
                    type="checkbox"
                    checked={true}
                    onChange={() => {}}
                  />
                  <span className="toggle-slider"></span>
                </label>
              </div>

              <div className="toggle-section">
                <div className="toggle-info">
                  <h4>Data Collection</h4>
                  <p>Allow us to collect anonymous usage data</p>
                </div>
                <label className="toggle-switch">
                  <input
                    type="checkbox"
                    checked={false}
                    onChange={() => {}}
                  />
                  <span className="toggle-slider"></span>
                </label>
              </div>

              <div className="privacy-actions">
                <button className="danger-btn">
                  <FaTrash /> Delete Account
                </button>
                <button className="secondary-btn">
                  Export Data
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Set;