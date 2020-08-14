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
						if (results != null) {
							SMS sms = results.getSMS();
							updateMessage(sms.getId());
						}
						Thread.sleep(100);
						System.out.println(workDone+" of "+studentResults.size()+" -> "+results);
						updateProgress(++workDone, size);
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
