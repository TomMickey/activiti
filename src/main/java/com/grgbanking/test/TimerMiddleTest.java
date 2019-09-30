package com.grgbanking.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class TimerMiddleTest {
	
	public static void main(String[] args) throws InterruptedException {
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
			.name("activiti子流程测试-整合嵌入式流程和调用式流程")//给对象起个名称
			.addClasspathResource("diagrams/Timer.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("diagrams/Timer.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		
		ProcessInstance processInstance=runtimeService.startProcessInstanceById(processDefinition.getId());
		
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		
		System.out.println(task.getName());
		
		taskService.complete(task.getId());
		
		System.out.println("完成任务");
		
		Thread.sleep(10000);
		
		task= taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		
		System.out.println(task);
		
		System.out.println(task.getId());
		
		System.out.println(task.getName());
	}

}
