package com.grgbanking.newPackage;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.grgbanking.entity.User;

public class CheckDel implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) {
		User user=(User) execution.getVariable("user");
		System.out.println(user);
		System.out.println("这个是测试用的啊------");
		throw new BpmnError("userError");
	}

}
