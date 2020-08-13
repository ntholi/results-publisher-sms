package com.breakoutms.luct.reg.model;

import java.util.Set;

import com.breakoutms.luct.reg.model.beans.SMS;
import com.breakoutms.luct.reg.model.beans.StudentResult;

import javafx.concurrent.Task;

public class ResultPublisher {

	public Task<Void> publish(Set<StudentResult> studentResults){
		int size = studentResults.size();
		return new Task<>() {
			@Override
			protected Void call() throws Exception {
				int workDone = 0;
				for (StudentResult results : studentResults) {
					SMS sms = results.getSMS();
					updateMessage(sms.getMessage());
					updateProgress(++workDone, size);
				}
				return null;
			}
		};
	}
}
