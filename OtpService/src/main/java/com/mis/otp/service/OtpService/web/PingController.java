package com.mis.otp.service.OtpService.web;

import com.mis.otp.service.OtpService.service.impl.OtpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/otp")
public class PingController {

    private final OtpServiceImpl otpService;

    @Autowired
    public PingController(OtpServiceImpl otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateOtp(@RequestParam String phoneNumber, @RequestParam String userId) {
        otpService.generateOtpAndStoreInRedis(phoneNumber, userId);
        return ResponseEntity.ok("OTP generated and stored in Redis.");
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateOtp(@RequestParam String phoneNumber, @RequestParam String userId, @RequestParam String otp) {
        boolean isValid = otpService.validateOtp(phoneNumber, userId, otp);
        return ResponseEntity.ok(isValid ? "OTP is valid." : "OTP is not valid.");
    }
}
