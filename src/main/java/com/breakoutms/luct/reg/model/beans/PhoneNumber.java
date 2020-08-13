package com.breakoutms.luct.reg.model.beans;

import com.breakoutms.luct.reg.utils.PhoneNumberParser;

import lombok.Getter;

@Getter
public class PhoneNumber {

	private String value;

	public PhoneNumber(String value) {
		this.value = PhoneNumberParser.parseNumber(value);
	}

	@Override
	public String toString() {
		return value;
	}	
}
