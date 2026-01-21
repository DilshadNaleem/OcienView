import './Register.css'
import { FaFacebook, FaGithub, FaGoogleDrive, FaWhatsapp } from 'react-icons/fa';
function Register()
{
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
                    <form action="" className='register-form'>
                        <input type="text"  placeholder='Enter First Name' className='input'/>
                        <input type="text"  placeholder='Enter Last Name' className='input'/>
                        <input type="text"  placeholder='Enter Email' className='input'/>
                        <input type="text"  placeholder='Enter NIC' className='input'/>
                        <input type="text"  placeholder='Enter Contact Number' className='input'/>
                        <input type="password"  placeholder='Enter Password' className='input'/>
                        <input type="password"  placeholder='Enter Confirm Password' className='input'/>

                        <button className='click-btn'>Register</button>

                        <a href="/Admin_Login">Already Registered?</a>

                    </form>
                </div>
            </div>
        </div>

        
    </div>

    
)
}

export default Register;