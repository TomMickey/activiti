package com.grgbanking.serviceTask;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class TestMyTask {
	
	public static void main(String[] args) {
		
		//初始化
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		
		//runtimeService
		RuntimeService runtimeService=processEngine.getRuntimeService();
		
		//taskService
		TaskService taskService=processEngine.getTaskService();
		
		//repositoryService
		RepositoryService repositoryService=processEngine.getRepositoryService();
		
		//返回一个部署对象
		Deployment deployment=repositoryService//与流程定义和部署对象相关的service
			.createDeployment()//创建一个部署对象
			.name("activiti测试任务监听器")//给对象起个名称
			.addClasspathResource("TestServiceTaskDelegateExpression.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("TestServiceTaskDelegateExpression.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		
		MyDelegate myDelegate=new MyDelegate();
		
		Map<String,Object> map=new HashMap<>();
		
		map.put("myDelegate", myDelegate);
		
		ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("myProcess",map);
		
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		
		System.out.println("-------------------");
		
		System.out.println(task);
		
		System.out.println(task.getName());
	}
}
