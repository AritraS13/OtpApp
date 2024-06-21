package com.mis.otp.service.OtpService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mis.otp.service.OtpService.service.OtpService;

@RestController
@RequestMapping("/_otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateOtp(@RequestParam String phoneNumber, @RequestParam String userId) {
        otpService.generateOtpAndStoreInRedis(phoneNumber, userId);
        return ResponseEntity.ok("OTP generated successfully");
    }


    @PostMapping("/validate")
    public boolean validateOtp(@RequestParam String phoneNumber, @RequestParam String otpCode, @RequestParam String userId) {
        return otpService.validateOtp(phoneNumber, otpCode, userId);
    }
}
