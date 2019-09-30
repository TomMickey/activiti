package com.grgbanking;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class TestErrorClass implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("抛出异常------");
		throw new BpmnError("子流程报错了!");
	}

}
