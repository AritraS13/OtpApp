package com.mis.otp.service.OtpService.dao;

import com.mis.otp.service.OtpService.model.Otp;

public interface OtpDao {

	void saveOtp(Otp otp);

	Otp getOtp(String phoneNumber);

	void deleteOtp(String phoneNumber);

}
