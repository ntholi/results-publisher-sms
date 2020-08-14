package com.breakoutms.luct.reg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.breakoutms.luct.reg.model.beans.SMS;
import com.breakoutms.luct.reg.model.beans.StudentResult;

import javafx.concurrent.Task;

public class ResultPublisher {

	public Task<List<SMS>> publish(Set<StudentResult> studentResults){
		int size = studentResults.size();
		List<SMS> smses = new ArrayList<>();
 		var task = new Task<List<SMS>>() {
			@Override
			protected List<SMS> call() throws Exception {
				int workDone = 0;
				for (StudentResult results : studentResults) {
					try {
						++workDone;
						updateProgress(workDone, size);
						if (results != null) {
							SMS sms = results.getSMS();
							sms = SmsSender.send(sms);
							addMap(smses, sms);
							updateMessage(sms.getId());
							System.out.println(workDone+" of "+studentResults.size()+": "+results.getPhoneNumber()+" -> "+results);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return smses;
			}
		};
		new Thread(task).start();
		return task;
	}

	protected void addMap(List<SMS> map, SMS sms) {
		if(sms.getPhoneNumber() != null) {
			map.add(sms);
		}
	}
}
