package com.grgbanking.test;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CountDelete implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("抛出异常-----------------");
		String user=(String) execution.getVariable("user");
		System.out.println("------------"+user);
		execution.setVariable("user", "JjjAck");
	}

}
