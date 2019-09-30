package com.grgbanking;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CheckServiceDelegete implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		User user=(User) execution.getVariable("user");
		System.out.println("检测------------");
		System.out.println(user);
		throw new BpmnError("overTenDays");
	}

}
