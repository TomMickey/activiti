package com.grgbanking.test;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class IcbcReduceDelete implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		execution.setVariable("amount", 1000);
		System.out.println("工商银行扣款-----------------------------------");
	}

}
