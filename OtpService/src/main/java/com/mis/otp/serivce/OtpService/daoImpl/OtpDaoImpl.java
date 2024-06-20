package com.mis.otp.serivce.OtpService.daoImpl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.mis.otp.serivce.OtpService.model.Otp;

@Repository
public class OtpDaoImpl {

	private static final String OTP_KEY_PREFIX = "OTP_";

	@Autowired
	private RedisTemplate<String, Otp> redisTemplate;

	public void saveOtp(Otp otp) {
		ValueOperations<String, Otp> ops = redisTemplate.opsForValue();
		ops.set(OTP_KEY_PREFIX + otp.getPhoneNumber(), otp, 5, TimeUnit.MINUTES); // Expires in 5 minutes
	}

	public Otp getOtp(String phoneNumber) {
		ValueOperations<String, Otp> ops = redisTemplate.opsForValue();
		return ops.get(OTP_KEY_PREFIX + phoneNumber);
	}

	public void deleteOtp(String phoneNumber) {
		redisTemplate.delete(OTP_KEY_PREFIX + phoneNumber);
	}

}
