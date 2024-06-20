package com.mis.otp.serivce.OtpService.serviceImpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mis.otp.serivce.OtpService.dao.OtpDao;
import com.mis.otp.serivce.OtpService.model.Otp;

@Service
public class OtpServiceImpl {

	@Autowired
	private OtpDao otpDao;

	public Otp generateOtp(String phoneNumber) {
		String otpCode = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
		Otp otp = new Otp();
		otp.setPhoneNumber(phoneNumber);
		otp.setOtpCode(otpCode);
		otp.setExpirationTime(System.currentTimeMillis() + 300000); // 5 minutes

		otpDao.saveOtp(otp);
		return otp;
	}

	public boolean validateOtp(String phoneNumber, String otpCode) {
		Otp otp = otpDao.getOtp(phoneNumber);
		if (otp != null && otp.getOtpCode().equals(otpCode) && otp.getExpirationTime() > System.currentTimeMillis()) {
			otpDao.deleteOtp(phoneNumber);
			return true;
		}
		return false;
	}

//	public Otp generateOtp(String phoneNumber) {
//		String otpCode = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
//		Otp otp = new Otp();
//		otp.setPhoneNumber(phoneNumber);
//		otp.setOtpCode(otpCode);
//		otp.setExpirationTime(System.currentTimeMillis() + 300000); // 5 minutes
//
//		otpStore.put(phoneNumber, otp);
//		return otp;
//	}
//
//	public boolean validateOtp(String phoneNumber, String otpCode) {
//		Otp otp = otpStore.get(phoneNumber);
//		if (otp != null && otp.getOtpCode().equals(otpCode) && otp.getExpirationTime() > System.currentTimeMillis()) {
//			otpStore.remove(phoneNumber);
//			return true;
//		}
//		return false;
//	}

}
