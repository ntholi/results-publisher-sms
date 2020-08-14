package com.breakoutms.luct.reg.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.breakoutms.luct.reg.model.beans.SMS;
import com.github.kevinsawicki.http.HttpRequest;

public class SmsSender {

	public static void send(SMS sms){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String format = "http://smsapi.myself.co.ls"
				+ "/send?apikey=1549440526&number=%s&msg=%s";
		if (sms.getPhoneNumber() != null) {
			String message = URLEncoder.encode(sms.getMessage(), StandardCharsets.UTF_8);
			String url = String.format(format, sms.getPhoneNumber(), message);
			HttpRequest request = HttpRequest.post(url);
			System.out.println(request.body());
		}
	}
}
