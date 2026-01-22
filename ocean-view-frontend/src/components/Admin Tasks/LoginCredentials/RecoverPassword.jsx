import { FaFacebook, FaGithub, FaWhatsapp } from "react-icons/fa"

function RecoverPassword() 
{
return(
   <div className="main-register">
           <div className="register">
   
                   <div className="heading">
                       <h3>Recover Password</h3>
                   </div>
               
               <div className="form-container">
                   <div className="form-body">
                       <form action="" className='register-form'>
                        
                           <input type="text"  placeholder='Enter Email' className='input'/>
                        
                           <button className='click-btn'>Register</button>
   
                           <a href="/Admin_Login">Remembered Password? Login</a>
                           
                       </form>
                   </div>
               </div>
           </div>
   
       </div>
);
}

export default RecoverPassword;