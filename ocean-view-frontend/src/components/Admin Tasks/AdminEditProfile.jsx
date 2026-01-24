import pImage from "../../assets/image/p.jpg";
import { useState, useRef } from "react";
import { FaCamera } from "react-icons/fa";

function AdminEditProfile() 
{
    const [profileImage, setProfileImage] = useState(pImage);
    const [selectedImage, setSelectedImage] = useState(null);
    const fileInputRef = useRef(null);


    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if(file) {
            setSelectedImage(file);
            const imageUrl = URL.createObjectURL(file);
            setProfileImage(imageUrl);
        }
    };

    const handleImageClick = () => {
        fileInputRef.current.click();
    };

    const handleSubmit = (e) => {
        console.log("Profile Updated" , selectedImage);
    };

    return(
    <div className="main-register">  
        <div className="register">     
            <div className="heading">
                <h3>Admin Edit Profile</h3>
            </div>

            <div className="form-container">
                <div className="form-body">
                    <form action="" className='register-form' onSubmit={handleSubmit}>

                        <div className="input-group-img">
                            <div className="profile-image-container">
                                <img src={profileImage} alt="Profile"
                                className="profile-image" 
                                />

                                 <div 
                                        className="image-overlay"
                                        onClick={handleImageClick}
                                    >
                                        <FaCamera className="camera-icon" />
                                        <span className="change-photo">Change Photo</span>
                                    </div>
                                     <input
                                    type="file"
                                    ref={fileInputRef}
                                    onChange={handleImageChange}
                                    accept="image/*"
                                    style={{ display: 'none' }}
                                />

                            </div>
                        </div>
                     <div className="input-group">
                            <input 
                                type="text" 
                                name="firstName"
                                placeholder='Enter First Name' 
                                className="input"
                            />
                        </div>

                         <div className="input-group">
                            <input 
                                type="text" 
                                name="lastName"
                                placeholder='Enter Last Name' 
                                className="input"
                            />
                        </div>

                        <div className="input-group">
                            <input 
                                type="text" 
                                name="email"
                                placeholder='Enter Email' 
                                className="input"
                                readOnly
                            />
                        </div>

                        <div className="input-group">
                            <input 
                                type="text" 
                                name="nic"
                                placeholder='Enter NIC' 
                                readOnly
                                className="input"
                            />
                        </div>

                        <div className="input-group">
                            <input 
                                type="text" 
                                name="contactNumber"
                                placeholder='Enter Contact Number' 
                                className="input"
                                readOnly
                            />
                        </div>
                        
                         <button className='click-btn'>Edit Profile</button>
                    </form>
                        
                </div>
            </div>
        </div> 
    </div>
        
    )
}

export default AdminEditProfile;