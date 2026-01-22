

function ResetPassword() 
{
return(
   <div className="main-register">
           <div className="register">
   
                   <div className="heading">
                       <h3>Reset Password</h3>
                   </div>
               
               <div className="form-container">
                   <div className="form-body">
                       <form action="" className='register-form'>
                        
                           <input type="password"  placeholder='Enter Password' className='input'/>
                            <input type="password"  placeholder='Confirm Password' className='input'/>
                           <button className='click-btn'>Register</button>
   
                           <a href="/Admin_Login">Remembered Password? Login</a>
                           
                       </form>
                   </div>
               </div>
           </div>
   
       </div>
);
}

export default ResetPassword;