package com.grgbanking;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ErrorServiceDelegete implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		User user =(User) execution.getVariable("user");
		System.out.println("请假天数大于10天!");
		System.out.println(user);
		throw new BpmnError("overTenDays");
	}

}
