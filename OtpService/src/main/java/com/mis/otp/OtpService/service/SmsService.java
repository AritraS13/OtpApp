package com.mis.otp.OtpService.service;

public interface SmsService {
    public void sendOtp(String phoneNumber, String otp) throws Exception;
}
