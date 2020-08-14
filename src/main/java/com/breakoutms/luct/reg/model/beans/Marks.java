package com.breakoutms.luct.reg.model.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Marks {
	private String cause;
	private String grade;
	
	@Override
	public String toString() {
		return cause +": "+ grade;
	}
}
