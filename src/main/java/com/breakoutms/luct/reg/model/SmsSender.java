package com.breakoutms.luct.reg.model;

import java.util.HashMap;
import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;

import com.breakoutms.luct.reg.model.beans.SMS;

public class SmsSender {

	public static void send(SMS sms){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String url = "http://smsapi.myself.co.ls"
				+ "?apikey=1549440526";
		Map<String, String> params = new HashMap<String, String>();
		if (sms.getPhoneNumber() != null) {
			String message = sms.getMessage();
			params.put("number", sms.getPhoneNumber());
			params.put("message", message);
			HttpRequest request = HttpRequest.post(url, params, true);
			System.out.println(request.body());
		}
	}
}
