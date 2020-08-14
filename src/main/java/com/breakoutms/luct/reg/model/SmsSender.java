package com.breakoutms.luct.reg.model;

import java.util.HashMap;
import java.util.Map;

import com.github.kevinsawicki.http.HttpRequest;

import com.breakoutms.luct.reg.model.beans.SMS;

public class SmsSender {

	public static void send(SMS sms){
		String url = "http://api.rmlconnect.net"
				+ "/bulksms/bulksms?username=m2camp"
				+ "&password=111111"
				+ "&type=0&dlr=1";
		Map<String, String> params = new HashMap<String, String>();
		if (sms.getPhoneNumber() != null) {
			String message = sms.getMessage();
			params.put("destination", sms.getPhoneNumber());
			params.put("message", message);
//			HttpRequest request = HttpRequest.post(url, params, true);
//			System.out.println(request.body());
		}
	}
}
