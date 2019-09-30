package com.grgbanking;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ErrorServiceDeleteService implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("错误处理啊");
		User user=(User) execution.getVariable("user");
		System.out.println(user);
	}

}
