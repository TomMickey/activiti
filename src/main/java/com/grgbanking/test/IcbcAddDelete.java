package com.grgbanking.test;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class IcbcAddDelete implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println(execution.getEventName());
		System.out.println(execution.getVariable("amount"));
		System.out.println("工商银行加款");

	}

}
