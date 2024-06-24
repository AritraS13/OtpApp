package com.mis.otp.otpService.service.impl;

import com.mis.otp.otpService.service.SmsService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmsServiceImpl implements SmsService {


    @Value("${odi.gov.sms.action}")
    private String action;

    @Value("${odi.gov.sms.source}")
    private String source;

    @Value("${odi.gov.sms.department.id}")
    private String departmentId;

    @Value("${odi.gov.sms.template.id}")
    private String templateId;

    @Value("${odi.gov.sms.content.format}")
    private String smsContentFormat;

    @Value("${odi.gov.sms.api.url}")
    private String apiUrl;

    public void sendOtp(String phoneNumber, String otp) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String smsContent = String.format(smsContentFormat, otp);
        RequestBody formBody = new FormBody.Builder()
                .add("action", action)
                .add("source", source)
                .add("department_id", departmentId)
                .add("template_id", templateId)
                .add("sms_content", smsContent)
                .add("phonenumber", phoneNumber)
                .build();

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }
}

