package com.breakoutms.luct.reg.model.beans;

import lombok.Data;

@Data
public class SMS {

	private String id;
	private String phoneNumber;
	private String message;
	private int numberOfSMSs;
	private boolean sent;
	private String errorMessage;
	private String response;
}
