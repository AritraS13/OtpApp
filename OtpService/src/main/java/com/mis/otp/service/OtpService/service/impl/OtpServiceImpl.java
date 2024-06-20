package com.mis.otp.service.OtpService.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String OTP_MAP = "otp_map";

    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

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

    public boolean validateOtp(String phoneNumber, String userId, String otp) {
        String key = phoneNumber + "_" + userId;
        HashOperations<String, Object, Object> hashOps = this.stringRedisTemplate.opsForHash();
        String storedOtp = (String) hashOps.get(OTP_MAP, key);
        return otp.equals(storedOtp);
    }
}

