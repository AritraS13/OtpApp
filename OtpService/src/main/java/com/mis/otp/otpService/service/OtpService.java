package com.mis.otp.otpService.service;

public interface OtpService {
	
//	Otp generateOtp(String phoneNumber);
	void generateOtpAndStoreInRedis(String phoneNumber, String userId) throws Exception;
//    boolean validateOtp(String phoneNumber, String otpCode);
    boolean validateOtp(String phoneNumber, String otpCode, String userId);
}
