import { useRef, useState } from 'react';
import './OtpVerification.css';

function OtpVerification() {

    const [otp, setOtp] = useState(['','','','','','']);
    const inputRefs = useRef([]);

    const handleChange = (index, value) => {
        if(value && !/^\d$/.test(value)) {
            return;
        }

        const newOtp = [...otp];
        newOtp[index] = value;
        setOtp(newOtp);

        if(value && index < inputRefs.current.length - 1) {
            inputRefs.current[index + 1].focus();
        }
    };


    const handleKeyDown = (index, e) => {
        if(e.key === 'Backspace' ) {
            if(!otp[index] && index > 0) {
                const newOtp = [...otp];
                newOtp[index - 1] = '';
                setOtp(newOtp);
                inputRefs.current[index - 1].focus();
            } else if (otp[index]) {
                const newOtp = [...otp];
                newOtp[index] = '';
                setOtp(newOtp);
            }
        }

        if(e.key === 'ArrowLeft' && index > 0) {
            inputRefs.current[index - 1].focus();
        }

        if(e.key === 'ArrowRight' && index < inputRefs.current.length - 1) {
            inputRefs.current[index + 1].focus();
        }
    };

    const handlePaste = (e) => {
        e.preventDefault();
        const pastedData = e.clipboardData.getData('text/plain').trim();

        if(/^\d+$/.test(pastedData)) {
            const digits = pastedData.split('').slice(0, otp.length);
            const newOtp = [...otp];

            digits.forEach((digit, idx) => {
                newOtp[idx] = digit;
            });

            setOtp(newOtp);
            const nextIndex = Math.min(digits.length, otp.length - 1);
            
            if(inputRefs.current[nextIndex]) {
                inputRefs.current[nextIndex].focus();
            }
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const otpValue = otp.join('');
        console.log('Submitted OTP:', otpValue);
        // Add further submission logic here
    }
    return(
        <div className="main-register">
            <div className="register">
                <div className="heading">
                    <h3>Otp Verification</h3>
                </div>

                <div className="form-container">
                    <div className="form-body">
                        <form action="" className="register-form" onSubmit={handleSubmit}>
                            <div className="input-group-otp">
                                {otp.map((digit,index) =>(
                                    <input 
                                    type="text" 
                                    key={index}
                                    ref={(el) => (inputRefs.current[index] = el)}
                                    name={`otp-${index}`}
                                    value={digit}
                                    placeholder='X'
                                    className='input-otp'
                                    maxLength="1"
                                    onChange={(e) => handleChange(index, e.target.value)}
                                    onKeyDown={(e) => handleKeyDown(index, e)}
                                    onPaste={index === 0 ? handlePaste : undefined}
                                    onFocus={(e) => e.target.select()}
                                    autoComplete="off"
                                    autoFocus={index === 0}
                                    />
                                ))}
                            </div>
                            <button className='click-btn'>Register</button>
                            
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default OtpVerification;