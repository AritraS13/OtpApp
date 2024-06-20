package com.mis.otp.serivce.OtpService.service;

import com.mis.otp.serivce.OtpService.model.Otp;

public interface OtpService {
	
	Otp generateOtp(String phoneNumber);
    boolean validateOtp(String phoneNumber, String otpCode);

}
