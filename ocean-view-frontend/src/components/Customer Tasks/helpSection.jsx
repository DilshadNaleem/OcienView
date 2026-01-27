import React from 'react';
import './HelpSection.css'; // Optional: For styling

function HelpSection() {
    return (
        <div className="help-section">
            <div className="help-header">
                <h1>Help & Support Center</h1>
                <p className="help-subtitle">Step-by-step guide to using our reservation system</p>
            </div>
            
            <div className="help-content">
                <div className="help-card">
                    <h2>🔐 Account Registration & Login Process</h2>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 1</span>
                            <h3>Enter Your Credentials</h3>
                        </div>
                        <p>Navigate to the login page and enter your registered email address and password.</p>
                    </div>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 2</span>
                            <h3>Verify with OTP</h3>
                        </div>
                        <p>After entering credentials, check your email for a One-Time Password (OTP). Enter this OTP on the verification page to proceed.</p>
                    </div>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 3</span>
                            <h3>Complete Login</h3>
                        </div>
                        <p>Once OTP is verified, you will be automatically logged into your account dashboard.</p>
                    </div>
                </div>
                
                <div className="help-card">
                    <h2>🔑 Password Recovery Process</h2>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 4</span>
                            <h3>Click "Forgot Password" Link</h3>
                        </div>
                        <p>If you've forgotten your password, click on the "Forgot Password" link on the login page.</p>
                    </div>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 5</span>
                            <h3>Check Your Email</h3>
                        </div>
                        <p>You will receive a password reset link in your registered email address. Click this link to proceed with password reset.</p>
                    </div>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 6</span>
                            <h3>Enter Valid Credentials</h3>
                        </div>
                        <p>On the password reset page, enter your new password (and confirm it) following the password requirements shown on screen.</p>
                    </div>
                </div>
                
                <div className="help-card">
                    <h2>🏨 Room Reservation Process</h2>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 7</span>
                            <h3>Select Your Room</h3>
                        </div>
                        <p>Browse available rooms, select your preferred room type, and choose your check-in/check-out dates. A confirmation email will be sent upon successful reservation.</p>
                    </div>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 8</span>
                            <h3>Late Check-Out Policy</h3>
                        </div>
                        <p className="warning-note">
                            ⚠️ <strong>Important:</strong> Late room handovers will incur a fine of <strong>5,000 LKR per day</strong>. Please ensure timely check-out to avoid additional charges.
                        </p>
                    </div>
                </div>
                
                <div className="help-card">
                    <h2>💳 Payment & Billing Process</h2>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 9</span>
                            <h3>Choose Payment Method</h3>
                        </div>
                        <p>Select your preferred payment method from the available options (Credit/Debit Card, Bank Transfer, Cash, etc.).</p>
                    </div>
                    
                    <div className="step">
                        <div className="step-header">
                            <span className="step-number">Step 10</span>
                            <h3>Generate Bill & Receipt</h3>
                        </div>
                        <p>After payment, a detailed bill will be generated. You can download the receipt and a copy will also be sent to your registered email address.</p>
                    </div>
                </div>
                
                <div className="contact-section">
                    <h2>📞 Need Further Assistance?</h2>
                    <p>If you encounter any issues or have additional questions, please contact our support team:</p>
                    <ul>
                        <li><strong>Email:</strong> support@hotelreservation.com</li>
                        <li><strong>Phone:</strong> +94 11 234 5678</li>
                        <li><strong>Live Chat:</strong> Available Mon-Fri, 9 AM - 6 PM</li>
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default HelpSection;