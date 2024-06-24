package com.mis.otp.otpService.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.mis.otp.otpService.dao.OtpDao;
import com.mis.otp.otpService.service.SmsService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.mis.otp.otpService.service.OtpService;

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpDao otpDao;

	@Autowired
	private SmsService smsService;

	private final StringRedisTemplate stringRedisTemplate;
	private static final String OTP_MAP = "otp_map";
	private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

	@Autowired
	public OtpServiceImpl(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public void generateOtpAndStoreInRedis(String phoneNumber, String userId) throws Exception {
		// Generate a 6-digit OTP
		try {
			String otp = String.format("%06d", new Random().nextInt(999999));

			// Store the OTP in Redis with a TTL of 2 minutes
			String key = phoneNumber + "_" + userId;
			HashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.opsForHash();
			hashOps.put(OTP_MAP, key, otp);
			this.stringRedisTemplate.expire(OTP_MAP, 2, TimeUnit.MINUTES);

			logger.info("OTP for " + phoneNumber + " is: " + otp);
			smsService.sendOtp(phoneNumber, otp);
		}catch (Exception e) {
			logger.error("Error occurred while generating OTP for " + phoneNumber, e);
			throw new Exception("Error occurred while generating OTP for " + phoneNumber, e);
		}
	}


	 public boolean validateOtp(String phoneNumber, String otpCode, String userId) {
	        String key = phoneNumber + "_" + userId;
	        HashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.opsForHash();
	        String storedOtp = (String) hashOps.get(OTP_MAP, key);
	        if (StringUtils.isNotEmpty(storedOtp) && storedOtp.equals(otpCode)) {
	            //hashOps.delete(OTP_MAP, key);
	            return true;
	        }
	        return false;
	    }

}
