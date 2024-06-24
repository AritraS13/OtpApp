package com.mis.otp.otpService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Otp {
	private String phoneNumber;
	private String otpCode;
	private long expirationTime;

}
