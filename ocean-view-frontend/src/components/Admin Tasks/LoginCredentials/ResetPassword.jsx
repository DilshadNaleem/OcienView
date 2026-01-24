import { useState } from "react";


function ResetPassword() 
{

    const [formData,setFormData] = useState({
        password: '',
        ConfirmPassword: ''
    });

    const [errors, setErrors] = useState({
        password: '',
        ConfirmPassword: ''
    });

    const [touched,setTouched] = useState({
        password : false,
        ConfirmPassword : false
    });

    const [showPassword, setShowPassword] = useState({
        password: false,
        ConfirmPassword: false
    });
    
   
    const validateField = (name, value) => {
        let error = '';

        switch(name) {
            case 'password' : 
            if(!value.trim()) error = 'Password is Required';
            break;

            case 'ConfirmPassword' :
            if(!value.trim()) error = 'Password is Required';
            else if(value !== formData.password) error = 'Passwords do not match';
            break;


            default:
                break;
        }

        return error;
    };


    const handleChange = (e) => {
        const {name, value} = e.target;

        setFormData(prev => ({
            ...prev,
            [name] : value
        }));

        if(touched[name]) {
            const error = validateField(name, value);
            setErrors(prev => (
                {
                    ...prev,
                    [name] : error
                }
            ));
        }
    }

    const handleBlur = (e) => {
        const {name} = e.target;
        setTouched(prev => ({
            ...prev,
            [name] : true
        }));

        const error = validateField(name, formData[name]);
        setErrors(prev => ({
            ...prev,
            [name] : error
        }));
    };

    const togglePasswordVisibility = (field) => {
        setShowPassword(prev => ({
            ...prev,
            [field] : !prev[field]
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const allTouched = {};
        Object.keys(formData) .forEach(field => {
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
            // Form is valid, proceed with submission logic
            console.log('Form Submitted', formData);
            alert('Password Reset Successful');

            setFormData({
                password: '',
                ConfirmPassword: ''
            });

            setTouched({
                password: false,
                ConfirmPassword: false
            });

            setShowPassword({
                password: false,
                ConfirmPassword: false
            });

        }
    }
return(
   <div className="main-register">
           <div className="register">
   
                   <div className="heading">
                       <h3>Reset Password</h3>
                   </div>
               
               <div className="form-container">
                   <div className="form-body">
                       <form action="" className='register-form' onSubmit={handleSubmit}>
                        
                        <div className="input-group">
                              <div className="password-input-wrapper">
                           <input type={showPassword.password ? 'text' : 'password'}  
                           placeholder='Enter Password' 
                           name="password"
                           className={`input ${touched.password && errors.password ? 'error' : ''}`}
                           value={formData.password}
                           onChange={handleChange}
                           onBlur={handleBlur}
                           autoComplete="new-password"
                           />
                           <button
                           type="button"
                           className="password-toggle"
                           onClick={() => togglePasswordVisibility('password')}
                           tabIndex="-1">

                             {showPassword.password ? (
                                            <span className="eye-icon">🙈</span>
                                        ) : (
                                            <span className="eye-icon">👁️‍🗨️</span>
                                        )}
                           </button>
                           </div>
                           {
                            touched.password && errors.password && (
                                <div className="error-message">{errors.password}</div>
                            )
                           }
                        </div>
                        
                        
                        <div className="input-group">
                            <div className="password-input-wrapper">

                           <input type={showPassword.ConfirmPassword ? 'text' : 'password'}  
                           placeholder='Confirm Password' 
                           name="ConfirmPassword"
                           className={`input ${touched.ConfirmPassword && errors.ConfirmPassword ? 'error' : ''}`}
                           value={formData.ConfirmPassword}
                           onChange={handleChange}
                           onBlur={handleBlur}
                           autoComplete="new-password"
                           />

                           <button
                           type="button"
                           className="password-toggle"
                           onClick={() => togglePasswordVisibility('ConfirmPassword')}
                           >

                            {showPassword.ConfirmPassword ? (
                                            <span className="eye-icon">🙈</span>
                                        ) : (
                                            <span className="eye-icon">👁️‍🗨️</span>
                                        )}
                           </button>

                           </div>
                           {
                            touched.ConfirmPassword && errors.ConfirmPassword && (
                                <div className="error-message">{errors.ConfirmPassword}</div>
                            )
                           }
                        </div>

                           <button type='submit' className='click-btn'>Register</button>
   
                           <a href="/Admin_Login">Remembered Password? Login</a>
                           
                       </form>
                   </div>
               </div>
           </div>
   
       </div>
);
}

export default ResetPassword;