package com.mis.otp.otpService.service;

public interface SmsService {
    public void sendOtp(String phoneNumber, String otp) throws Exception;
}
