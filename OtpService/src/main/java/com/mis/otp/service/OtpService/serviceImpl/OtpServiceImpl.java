package com.mis.otp.service.OtpService.serviceImpl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.mis.otp.service.OtpService.dao.OtpDao;
import com.mis.otp.service.OtpService.model.Otp;
import com.mis.otp.service.OtpService.service.OtpService;

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpDao otpDao;

	private final StringRedisTemplate stringRedisTemplate;
	private static final String OTP_MAP = "otp_map";
	private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

	@Autowired
	public OtpServiceImpl(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public void generateOtpAndStoreInRedis(String phoneNumber, String userId) {
		// Generate a 6-digit OTP
		String otp = String.format("%06d", new Random().nextInt(999999));

		// Store the OTP in Redis with a TTL of 2 minutes
		String key = phoneNumber + "_" + userId;
		HashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.opsForHash();
		hashOps.put(OTP_MAP, key, otp);
		this.stringRedisTemplate.expire(OTP_MAP, 2, TimeUnit.MINUTES);

		logger.info("OTP for " + phoneNumber + " is: " + otp);

		// Send kafka event to notification service for SMS
	}

	public Otp generateOtp(String phoneNumber) {
		String otpCode = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
		Otp otp = new Otp();
		otp.setPhoneNumber(phoneNumber);
		otp.setOtpCode(otpCode);
		otp.setExpirationTime(System.currentTimeMillis() + 300000); // 5 minutes---

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

	public boolean validateOtp1(String phoneNumber, String otpCode) {
        String key = phoneNumber + "_someUserId"; // Adjust the key accordingly
        HashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.opsForHash();
        String storedOtp = (String) hashOps.get(OTP_MAP, key);
        return otpCode.equals(storedOtp);
    }

}
