package com.mis.otp.service.OtpService.service;

import com.mis.otp.service.OtpService.model.Otp;

public interface OtpService {
	
	Otp generateOtp(String phoneNumber);
	void generateOtpAndStoreInRedis(String phoneNumber, String userId);
    boolean validateOtp(String phoneNumber, String otpCode);
    boolean validateOtp(String phoneNumber, String otpCode, String userId);
}
