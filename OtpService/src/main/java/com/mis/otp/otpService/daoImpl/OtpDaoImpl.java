package com.mis.otp.otpService.daoImpl;

import java.util.concurrent.TimeUnit;

import com.mis.otp.otpService.dao.OtpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.mis.otp.otpService.model.Otp;

@Repository
public class OtpDaoImpl implements OtpDao {

	private static final String OTP_MAP = "otp_map";
	private final StringRedisTemplate stringRedisTemplate;

	@Autowired
	public OtpDaoImpl(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Override
	public void saveOtp(Otp otp) {
		String key = otp.getPhoneNumber();
		HashOperations<String, Object, Object> hashOps = stringRedisTemplate.opsForHash();
		hashOps.put(OTP_MAP, key, otp.getOtpCode());
		stringRedisTemplate.expire(OTP_MAP, otp.getExpirationTime(), TimeUnit.MILLISECONDS);
	}

	
	public Otp getOtp(String phoneNumber) {
		HashOperations<String, Object, Object> hashOps = stringRedisTemplate.opsForHash();
		String otpCode = (String) hashOps.get(OTP_MAP, phoneNumber);
		// Assuming expiration time is handled by Redis TTL
		return new Otp(phoneNumber, otpCode, 2);
	}

	@Override
	public void deleteOtp(String phoneNumber) {
		stringRedisTemplate.opsForHash().delete(OTP_MAP, phoneNumber);
	}

}
