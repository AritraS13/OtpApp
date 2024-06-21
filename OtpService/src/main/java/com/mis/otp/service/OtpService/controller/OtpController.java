package com.mis.otp.service.OtpService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mis.otp.serivce.OtpService.request.OtpRequest;
import com.mis.otp.service.OtpService.model.Otp;
import com.mis.otp.service.OtpService.response.OtpResponse;
import com.mis.otp.service.OtpService.service.OtpService;

@RestController
@RequestMapping("/_otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public Otp generateOtp(@RequestBody OtpRequest otpRequest) {
        return otpService.generateOtp(otpRequest.getPhoneNumber());
    }

    @PostMapping("/validate")
    public OtpResponse validateOtp(@RequestBody OtpRequest otpRequest) {
        boolean isValid = otpService.validateOtp(otpRequest.getPhoneNumber(), otpRequest.getOtpCode());
        return new OtpResponse(isValid);
    }
}
