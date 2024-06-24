package com.mis.otp.otpService.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {
	private String phoneNumber;
	private String otpCode;

}
