package com.mis.otp.OtpService.dao;

import com.mis.otp.OtpService.model.Otp;

public interface OtpDao {

	void saveOtp(Otp otp);

	Otp getOtp(String phoneNumber);

	void deleteOtp(String phoneNumber);
}
