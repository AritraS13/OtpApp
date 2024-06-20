package com.mis.otp.serivce.OtpService.dao;

import com.mis.otp.serivce.OtpService.model.Otp;

public interface OtpDao {

	void saveOtp(Otp otp);

	Otp getOtp(String phoneNumber);

	void deleteOtp(String phoneNumber);

}
