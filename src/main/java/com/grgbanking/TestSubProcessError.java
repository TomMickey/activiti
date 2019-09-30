package com.grgbanking;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TestSubProcessError {
	
	@Test
	public void test() {
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService=processEngine.getRuntimeService();
		TaskService taskService=processEngine.getTaskService();
		RepositoryService repositoryService=processEngine.getRepositoryService();
		//返回一个部署对象
		Deployment deployment=processEngine.getRepositoryService()//与流程定义和部署对象相关的service
			.createDeployment()//创建一个部署对象
			.name("activiti入门示例-测试子流程错误!")//给对象起个名称
			.addClasspathResource("bpmn/MyProcess.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("bpmn/MyProcess.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		ProcessInstance processInstance=runtimeService.startProcessInstanceById(processDefinition.getId());
		
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println(task.getName());
		System.out.println(processInstance.getId());
		System.out.println("获取流程参数");
		
	}

}
