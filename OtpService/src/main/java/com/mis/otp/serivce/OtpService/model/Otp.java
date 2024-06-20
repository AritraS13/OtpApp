package com.mis.otp.serivce.OtpService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Otp {
	private String phoneNumber;
	private String otpCode;
	private long expirationTime;

}
