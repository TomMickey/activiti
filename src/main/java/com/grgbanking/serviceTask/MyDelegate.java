package com.grgbanking.serviceTask;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class MyDelegate implements JavaDelegate, Serializable {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("---------------------");
		System.out.println("这是处理类啊-----------------");
	}

}
