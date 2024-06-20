package com.mis.otp.serivce.OtpService.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpResponse {
	
	private String phoneNumber;
    private String otpCode;
    private long expirationTime;


}
