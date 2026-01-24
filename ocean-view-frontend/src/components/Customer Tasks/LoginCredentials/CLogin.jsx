import { useState } from "react";
import { FaFacebook, FaGithub, FaWhatsapp } from "react-icons/fa";
import './CLogin.css';

function CLogin() {
  // State for form mode (login/register)
  const [isLoginMode, setIsLoginMode] = useState(true);
  
  // State for form data
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: ''
  });

  // State for errors
  const [errors, setErrors] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: ''
  });

  // State for touched fields
  const [touched, setTouched] = useState({
    name: false,
    email: false,
    password: false,
    confirmPassword: false
  });

  // State for password visibility
  const [showPassword, setShowPassword] = useState({
    password: false,
    confirmPassword: false
  });

  // Regex patterns
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{6,}$/;

  // Validate field
  const validateField = (name, value) => {
    let error = '';

    switch (name) {
      case 'name':
        if (!isLoginMode && !value.trim()) {
          error = 'Name is required';
        } else if (!isLoginMode && value.trim().length < 2) {
          error = 'Name must be at least 2 characters long';
        }
        break;

      case 'email':
        if (!value.trim()) {
          error = 'Email is required';
        } else if (!emailRegex.test(value)) {
          error = 'Invalid email format';
        }
        break;

      case 'password':
        if (!value.trim()) {
          error = 'Password is required';
        } else if (value.length < 6) {
          error = 'Password must be at least 6 characters long';
        } else if (!isLoginMode && !passwordRegex.test(value)) {
          error = 'Password must contain at least one letter and one number';
        }
        break;

      case 'confirmPassword':
        if (!isLoginMode) {
          if (!value.trim()) {
            error = 'Please confirm your password';
          } else if (value !== formData.password) {
            error = 'Passwords do not match';
          }
        }
        break;

      default:
        break;
    }

    return error;
  };

  // Handle input change
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData(prev => ({
      ...prev,
      [name]: value
    }));

    if (touched[name]) {
      const error = validateField(name, value);
      setErrors(prev => ({
        ...prev,
        [name]: error
      }));
    }

    // If confirmPassword field and password changes, validate confirmPassword again
    if (name === 'password' && touched.confirmPassword) {
      const confirmError = validateField('confirmPassword', formData.confirmPassword);
      setErrors(prev => ({
        ...prev,
        confirmPassword: confirmError
      }));
    }
  };

  // Toggle password visibility
  const togglePasswordVisibility = (field) => {
    setShowPassword(prev => ({
      ...prev,
      [field]: !prev[field]
    }));
  };

  // Handle blur
  const handleBlur = (e) => {
    const { name } = e.target;
    setTouched(prev => ({
      ...prev,
      [name]: true
    }));

    const error = validateField(name, formData[name]);
    setErrors(prev => ({
      ...prev,
      [name]: error
    }));
  };

  // Switch between login and register
  const toggleMode = () => {
    setIsLoginMode(!isLoginMode);
    
    // Reset form data
    setFormData({
      name: '',
      email: '',
      password: '',
      confirmPassword: ''
    });

    // Reset errors
    setErrors({
      name: '',
      email: '',
      password: '',
      confirmPassword: ''
    });

    // Reset touched
    setTouched({
      name: false,
      email: false,
      password: false,
      confirmPassword: false
    });

    // Reset password visibility
    setShowPassword({
      password: false,
      confirmPassword: false
    });
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();

    // Mark all fields as touched
    const allTouched = {};
    const fieldsToValidate = isLoginMode 
      ? ['email', 'password'] 
      : ['name', 'email', 'password', 'confirmPassword'];
    
    fieldsToValidate.forEach(field => {
      allTouched[field] = true;
    });
    setTouched(allTouched);

    // Validate all fields
    const newErrors = {};
    let isValid = true;

    fieldsToValidate.forEach(field => {
      const error = validateField(field, formData[field]);
      newErrors[field] = error;
      if (error) isValid = false;
    });

    setErrors(newErrors);

    if (isValid) {
      // Submit the form
      const submissionData = isLoginMode
        ? { email: formData.email, password: formData.password }
        : { name: formData.name, email: formData.email, password: formData.password };

      console.log(`Form Submitted (${isLoginMode ? 'Login' : 'Register'})`, submissionData);
      alert(`${isLoginMode ? 'Login' : 'Registration'} successful!`);

      // Reset form
      setFormData({
        name: '',
        email: '',
        password: '',
        confirmPassword: ''
      });

      setTouched({
        name: false,
        email: false,
        password: false,
        confirmPassword: false
      });

      setShowPassword({
        password: false,
        confirmPassword: false
      });
    }
  };

  return (
    <div className="main-register">
      <div className={`register ${isLoginMode ? 'login-mode' : 'register-mode'}`}>
        <div className="heading">
          <h3>{isLoginMode ? 'Login' : 'Register'}</h3>
        </div>

        <div className="register-head">
                 <a href="#" className='register-hyperlink'>
                   <FaFacebook/>
                 </a>
   
                 <a href="#" className='register-hyperlink'>
                   <FaGithub/>
                 </a>
   
                 <a href="#" className='register-hyperlink'>
                   <FaWhatsapp/>
                 </a>
               </div>

        <div className="form-container">
          <div className="form-body">
            <form className='register-form' onSubmit={handleSubmit}>
              {!isLoginMode && (
                <div className="input-group">
                  <input
                    type="text"
                    name="name"
                    placeholder='Enter Full Name'
                    className={`input ${touched.name && errors.name ? 'error' : ''}`}
                    value={formData.name}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  {touched.name && errors.name && (
                    <div className="error-message">{errors.name}</div>
                  )}
                </div>
              )}

              <div className="input-group">
                <input
                  type="text"
                  name="email"
                  placeholder='Enter Email'
                  className={`input ${touched.email && errors.email ? 'error' : ''}`}
                  value={formData.email}
                  onChange={handleChange}
                  onBlur={handleBlur}
                />
                {touched.email && errors.email && (
                  <div className="error-message">{errors.email}</div>
                )}
              </div>

              <div className="input-group">
                <div className="password-input-wrapper">
                  <input
                    type={showPassword.password ? 'text' : 'password'}
                    name="password"
                    placeholder='Enter Password'
                    className={`input ${touched.password && errors.password ? 'error' : ''}`}
                    value={formData.password}
                    onChange={handleChange}
                    onBlur={handleBlur}
                  />
                  <button
                    type="button"
                    className="password-toggle"
                    onClick={() => togglePasswordVisibility('password')}
                    tabIndex="-1"
                  >
                    {showPassword.password ? (
                      <span className="eye-icon">🙈</span>
                    ) : (
                      <span className="eye-icon">👁️‍🗨️</span>
                    )}
                  </button>
                </div>
                {touched.password && errors.password && (
                  <div className="error-message">{errors.password}</div>
                )}
              </div>

              {!isLoginMode && (
                <div className="input-group">
                  <div className="password-input-wrapper">
                    <input
                      type={showPassword.confirmPassword ? 'text' : 'password'}
                      name="confirmPassword"
                      placeholder='Confirm Password'
                      className={`input ${touched.confirmPassword && errors.confirmPassword ? 'error' : ''}`}
                      value={formData.confirmPassword}
                      onChange={handleChange}
                      onBlur={handleBlur}
                    />
                    <button
                      type="button"
                      className="password-toggle"
                      onClick={() => togglePasswordVisibility('confirmPassword')}
                      tabIndex="-1"
                    >
                      {showPassword.confirmPassword ? (
                        <span className="eye-icon">🙈</span>
                      ) : (
                        <span className="eye-icon">👁️‍🗨️</span>
                      )}
                    </button>
                  </div>
                  {touched.confirmPassword && errors.confirmPassword && (
                    <div className="error-message">{errors.confirmPassword}</div>
                  )}
                </div>
              )}

              <button className='auth-btn' type="submit">
                {isLoginMode ? 'Login' : 'Register'}
              </button>

              <div className="auth-links">
                <button
                  type="button"
                  className="toggle-mode-btn"
                  onClick={toggleMode}
                >
                  {isLoginMode 
                    ? 'Create New Account' 
                    : 'Already have an account? Login'}
                </button>
                
                {isLoginMode && (
                  <a href="/Admin_ResetPassword" className="reset-link">
                    Reset Password
                  </a>
                )}
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CLogin;