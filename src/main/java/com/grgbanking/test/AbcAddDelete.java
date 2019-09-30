package com.grgbanking.test;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class AbcAddDelete implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println(execution.getVariable("amount"));
		System.out.println("农业银行加款----");
	}

}
