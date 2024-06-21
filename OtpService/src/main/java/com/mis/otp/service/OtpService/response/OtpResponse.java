package com.mis.otp.service.OtpService.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpResponse {

	private boolean valid;

	public OtpResponse(boolean valid) {
		this.valid = valid;
	}

}
