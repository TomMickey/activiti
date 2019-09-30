package com.grgbanking.test;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class SignalMiddleTest {
	
	public static void main(String[] args) throws InterruptedException {
		//初始化
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		//runtimeService
		RuntimeService runtimeService=processEngine.getRuntimeService();
		//taskService
		TaskService taskService=processEngine.getTaskService();
		//repositoryService
		RepositoryService repositoryService=processEngine.getRepositoryService();
		//formService
		FormService formService=processEngine.getFormService();
		//返回一个部署对象
		Deployment deployment=repositoryService//与流程定义和部署对象相关的service
			.createDeployment()//创建一个部署对象
			.name("activiti子流程测试-整合嵌入式流程和调用式流程")//给对象起个名称
			.addClasspathResource("diagrams/singal.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("diagrams/singal.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		
		ProcessInstance processInstance=runtimeService.startProcessInstanceById(processDefinition.getId());
				//.startProcessInstanceById(processDefinition.getId());
		
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		
		System.out.println(task.getName());
		
		taskService.complete(task.getId());
		
		System.out.println("完成任务");
		
		Thread.sleep(5000);
		
		List<Task> tasks= taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
		
		System.out.println("---");
		
		System.out.println(tasks);
		
		for(Task task2:tasks) {
			System.out.println(task2.getId());
			System.out.println(task2.getName());
		}
	}

}
