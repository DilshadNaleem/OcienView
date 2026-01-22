import './Register.css'
import { FaFacebook, FaGithub, FaGoogleDrive, FaWhatsapp } from 'react-icons/fa';
import { useState } from 'react';


function Register()
{

    const[formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        nic: '',
        contactNumber: '',
        password: '',
        confirmPassword: ''

      });

      const [errors, setErrors] = useState({
        firstName: '',
        lastName: '',
        email: '',
        nic: '',
        contactNumber: '',
        password: '',
        confirmPassword: ''
      });

      const [touched, setTouched] = useState({
        firstName: false,
        lastName: false,
        email: false,
        nic: false,
        contactNumber: false,
        password: false,
        confirmPassword: false
      });

      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      const nicRegex = /^(?:\d{9}[VXvx]|\d{12})$/;
      const contactNumberRegex = /^(?:\+94|0)7[0-9]{8}$/;

      const validateField = (name, value) => {
        let error = '';
        switch (name) {
            case 'firstName': 
            if(!value.trim()) error = 'First Name is required';
            else if(value.trim().length < 2) error = 'First Name must be at least 2 characters';
            break;

            case 'lastName':
            if(!value.trim()) error = 'Last Name is required';
            else if(value.trim().length < 2) error = 'Last Name must be at least 2 characters';
            break;

            case 'email':
            if(!value.trim()) error = 'Email is required';
            else if(!emailRegex.test(value)) error = 'Invalid email format';
            break;

            case 'nic':
            if(!value.trim()) error = 'NIC is required';
            else if(!nicRegex.test(value)) error = 'Invalid NIC format';
            break;

            case 'contactNumber':
            if(!value.trim()) error = 'Contact Number is required';
            else if(!contactNumberRegex.test(value)) error = 'Invalid Contact Number format';
            break;

            case 'password':
            if(!value.trim()) error = 'Password is required';
            else if(value.length < 6) error = 'Password must be at least 6 characters';
            break;

            case 'confirmPassword':
            if(!value.trim()) error = 'Confirm Password is required';
            else if(value !== formData.password) error = 'Passwords do not match';
            break;

            default:
                break;
        }

        return error;
      };

      const handleChange = (e) => {
        const { name, value } = e.target;

        setFormData(prev => ({
            ...prev,
            [name]: value
        }));

        if(touched[name]) {
            const error = validateField(name, value);
            setErrors(prev => ({
                ...prev,
                [name]: error
            }));
        }
    }
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

      const handleSubmit = (e) => {
        e.preventDefault();

        const allTouched = {};
        Object.keys(formData).forEach(field => {
            allTouched[field] = true;
        });
        setTouched(allTouched);

        const newErrors = {};
        let isValid = true;

        Object.keys(formData).forEach(field => {
            const error = validateField(field, formData[field]);
            newErrors[field] = error;
            if(error) isValid = false;
        });

        setErrors(newErrors);
        if(isValid) {
            console.log('Form Data Submitted:', formData);
             alert('Registration successful!');

              setFormData({
        firstName: '',
        lastName: '',
        email: '',
        nic: '',
        contactNumber: '',
        password: '',
        confirmPassword: ''
      });
      setTouched({
        firstName: false,
        lastName: false,
        email: false,
        nic: false,
        contactNumber: false,
        password: false,
        confirmPassword: false
      });
    }
  };

return(
    <div className="main-register">
        <div className="register">

            
                <div className="heading">
                    <h3>Register</h3>
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
                    <form action="" className='register-form' onSubmit={handleSubmit}>
                        <div className="input-group">
                            <input 
                                type="text" 
                                name="firstName"
                                placeholder='Enter First Name' 
                                className={`input ${touched.firstName && errors.firstName ? 'error' : ''}`}
                                value={formData.firstName}
                                onChange={handleChange}
                                onBlur={handleBlur}
                            />
                            {touched.firstName && errors.firstName && (
                                <div className="error-message">{errors.firstName}</div>
                            )}
                        </div>
                        
                        <div className="input-group">
                            <input 
                                type="text" 
                                name="lastName"
                                placeholder='Enter Last Name' 
                                className={`input ${touched.lastName && errors.lastName ? 'error' : ''}`}
                                value={formData.lastName}
                                onChange={handleChange}
                                onBlur={handleBlur}
                            />
                            {touched.lastName && errors.lastName && (
                                <div className="error-message">{errors.lastName}</div>
                            )}
                        </div>
                        
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
                            <input 
                                type="text" 
                                name="nic"
                                placeholder='Enter NIC' 
                                className={`input ${touched.nic && errors.nic ? 'error' : ''}`}
                                value={formData.nic}
                                onChange={handleChange}
                                onBlur={handleBlur}
                            />
                            {touched.nic && errors.nic && (
                                <div className="error-message">{errors.nic}</div>
                            )}
                        </div>
                        
                        <div className="input-group">
                            <input 
                                type="text" 
                                name="contactNumber"
                                placeholder='Enter Contact Number' 
                                className={`input ${touched.contactNumber && errors.contactNumber ? 'error' : ''}`}
                                value={formData.contactNumber}
                                onChange={handleChange}
                                onBlur={handleBlur}
                            />
                            {touched.contactNumber && errors.contactNumber && (
                                <div className="error-message">{errors.contactNumber}</div>
                            )}
                        </div>
                        
                        <div className="input-group">
                            <input 
                                type="password" 
                                name="password"
                                placeholder='Enter Password' 
                                className={`input ${touched.password && errors.password ? 'error' : ''}`}
                                value={formData.password}
                                onChange={handleChange}
                                onBlur={handleBlur}
                            />
                            {touched.password && errors.password && (
                                <div className="error-message">{errors.password}</div>
                            )}
                        </div>
                        
                        <div className="input-group">
                            <input 
                                type="password" 
                                name="confirmPassword"
                                placeholder='Enter Confirm Password' 
                                className={`input ${touched.confirmPassword && errors.confirmPassword ? 'error' : ''}`}
                                value={formData.confirmPassword}
                                onChange={handleChange}
                                onBlur={handleBlur}
                            />
                            {touched.confirmPassword && errors.confirmPassword && (
                                <div className="error-message">{errors.confirmPassword}</div>
                            )}
                        </div>

                        <button className='click-btn'>Register</button>

                        <a href="/Admin_Login">Already Registered?</a>

                    </form>
                </div>
            </div>
        </div>

        
    </div>

    
);
}

export default Register;