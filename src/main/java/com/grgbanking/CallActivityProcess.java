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

public class CallActivityProcess {
	
	@Test
	public void test() {
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService=processEngine.getRuntimeService();
		TaskService taskService=processEngine.getTaskService();
		RepositoryService repositoryService=processEngine.getRepositoryService();
		//返回一个部署对象
		Deployment deployment=processEngine.getRepositoryService()//与流程定义和部署对象相关的service
			.createDeployment()//创建一个部署对象
			.name("activiti入门示例-测试调用子流程!")//给对象起个名称
			.addClasspathResource("bpmn/CallActivityProcess.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("bpmn/CallActivityProcess.png")
			.addClasspathResource("bpmn/OffWork.bpmn")
			.addClasspathResource("bpmn/OffWork.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).processDefinitionKey("offWork").singleResult();
		ProcessInstance processInstance=runtimeService.startProcessInstanceById(processDefinition.getId());
		System.out.println("输出流程实例id:"+processInstance.getId());
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("当前流程的名称:"+task.getName());
		System.out.println(processInstance.getId());
		taskService.complete(task.getId());
		ProcessInstance processInstance2=runtimeService.createProcessInstanceQuery().superProcessInstanceId(processInstance.getId()).singleResult();
		task=taskService.createTaskQuery().processInstanceId(processInstance2.getId()).singleResult();
		System.out.println("当前流程的名称:"+task);
//		System.out.println(processInstance.getId());
//		taskService.complete(task.getId());
	}

}
