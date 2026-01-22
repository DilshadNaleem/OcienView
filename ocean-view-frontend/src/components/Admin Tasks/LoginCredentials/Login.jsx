import { useState } from "react"
import { FaFacebook, FaGithub, FaWhatsapp } from "react-icons/fa"

function Login()
{

    const [formData, setFormData]= useState({
        email: '',
        password: ''
    });

    const [errors, setErrors]= useState({
        email: '',
        password: ''
    });

    const [touched, setTouched] = useState({
        email: false,
        password: false
    });

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    const validateField = (name, value) => {
        let error = '';

        switch (name) {
            case 'email':
                if(!value.trim()) error = 'Email is Required';
                else if(!emailRegex.test(value)) error = 'Invalid Email Format';
                break;

            case 'password':
                if(!value.trim()) error = 'Password is Required';
                else if(value.length < 6) error = 'Password must be at least 6 characters long';
                break;
            default:
                break;
        }

        return error;
    };

    const handleChange = (e) => {
        const {name, value} = e.target;

        setFormData(prev => (
            {
                ...prev,
                [name]: value
            }
        ));

        if(touched[name]){
            const error = validateField(name, value);
            setErrors(prev => (
                {
                    ...prev,
                    [name]: error
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
            if(isValid){
                // Submit the form
                console.log('Form Submitted', formData);
                alert('Login successful!');

                setFormData({
                    email: '',
                    password: ''
                });

                setTouched({
                    email: false,
                    password: false
                });
            }
    }

 return(
   
       <div className="main-register">
           <div className="register">
   
               
                   <div className="heading">
                       <h3>Login</h3>
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
                            name="email"
                            placeholder='Enter Email' 
                            className={`input ${touched.email && errors.email ? 'error' : ''}`}
                            value={formData.email}  
                            onChange={handleChange}
                            onBlur={handleBlur}
                        /> {
                            touched.email && errors.email && (
                                <div className="error-message">{errors.email}</div>
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
                             {
                                touched.password && errors.password && (
                                    <div className="error-message">{errors.password}</div>
                             )
                             }
                        </div>
                          
                           
   
                           <button className='click-btn'>Register</button>
   
                           <a href="/Admin_Register">Create New Account</a>
                           <a href="/Admin_ResetPassword">Reset Password</a>
   
                       </form>
                   </div>
               </div>
           </div>
   
           
       </div>
   
       
   )
 
}

export default Login