package com.mis.otp.serivce.OtpService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mis.otp.serivce.OtpService.model.Otp;
import com.mis.otp.serivce.OtpService.request.OtpRequest;
import com.mis.otp.serivce.OtpService.response.OtpResponse;
import com.mis.otp.serivce.OtpService.service.OtpService;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

	@Autowired
	private OtpService otpService;

	@PostMapping("/generate")
	public OtpResponse generateOtp(@RequestBody OtpRequest request) {
		Otp otp = otpService.generateOtp(request.getPhoneNumber());
		OtpResponse response = new OtpResponse();
		response.setPhoneNumber(otp.getPhoneNumber());
		response.setOtpCode(otp.getOtpCode());
		response.setExpirationTime(otp.getExpirationTime());
		return response;
	}

	@PostMapping("/validate")
	public boolean validateOtp(@RequestParam String phoneNumber, @RequestParam String otpCode) {
		return otpService.validateOtp(phoneNumber, otpCode);
	}

}
