package com.breakoutms.luct.reg.model;

import java.util.Set;

import com.breakoutms.luct.reg.model.beans.SMS;
import com.breakoutms.luct.reg.model.beans.StudentResult;

import javafx.concurrent.Task;

public class ResultPublisher {

	public Task<Void> publish(Set<StudentResult> studentResults){
		int size = studentResults.size();
		var task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				int workDone = 0;
				for (StudentResult results : studentResults) {
					try {
						++workDone;
						updateProgress(workDone, size);
						if (results != null) {
							SMS sms = results.getSMS();
							SmsSender.send(sms);
							updateMessage(sms.getId());
							System.out.println(workDone+" of "+studentResults.size()+": "+results.getPhoneNumber()+" -> "+results);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		};
		new Thread(task).start();
		return task;
	}
}
