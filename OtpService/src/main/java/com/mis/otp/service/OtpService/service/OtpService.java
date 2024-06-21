package com.mis.otp.service.OtpService.service;

import com.mis.otp.service.OtpService.model.Otp;

public interface OtpService {
	
	Otp generateOtp(String phoneNumber);
    boolean validateOtp(String phoneNumber, String otpCode);

}
