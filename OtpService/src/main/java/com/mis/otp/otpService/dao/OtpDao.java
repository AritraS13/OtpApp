package com.mis.otp.otpService.dao;

import com.mis.otp.otpService.model.Otp;

public interface OtpDao {

	void saveOtp(Otp otp);

	Otp getOtp(String phoneNumber);

	void deleteOtp(String phoneNumber);
}
