package com.mis.otp.otpService.controller;

import com.mis.otp.otpService.response.OtpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mis.otp.otpService.service.OtpService;

@RestController
@RequestMapping("/_otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<OtpResponse> generateOtp(@RequestParam String phoneNumber, @RequestParam String userId) {
        OtpResponse otpResponse = new OtpResponse();
        try {
            otpService.generateOtpAndStoreInRedis(phoneNumber, userId);
            otpResponse.setStatus("success");
            otpResponse.setMessage("OTP generated successfully");
            return ResponseEntity.ok(otpResponse);
        } catch (Exception e) {
            otpResponse.setStatus("failure");
            otpResponse.setMessage("Failed to generate OTP");
            return ResponseEntity.badRequest().body(otpResponse);
        }
    }


    @PostMapping("/validate")
    public ResponseEntity<OtpResponse> validateOtp(@RequestParam String phoneNumber, @RequestParam String otpCode, @RequestParam String userId) {
        OtpResponse otpResponse = new OtpResponse();
        if (otpService.validateOtp(phoneNumber, otpCode, userId)) {
            otpResponse.setStatus("success");
            otpResponse.setMessage("OTP validated successfully");
            return ResponseEntity.ok(otpResponse);
        } else {
            otpResponse.setStatus("failure");
            otpResponse.setMessage("Invalid OTP");
            return ResponseEntity.badRequest().body(otpResponse);
        }
    }
}
