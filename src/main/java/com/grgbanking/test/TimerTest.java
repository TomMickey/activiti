package com.grgbanking.test;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerTest {
	
	//日志
	private Logger logger=LoggerFactory.getLogger(TimerTest.class);
	
	@Test
	public void timerTest() throws InterruptedException {
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
			.addClasspathResource("diagrams/TestStartOne.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("diagrams/TestStartOne.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery()
				.deploymentId(deployment.getId())
				.processDefinitionKey("myProcess").singleResult();
		
		ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("myProcess");
				//.startProcessInstanceById(processDefinition.getId());
		
		StartFormData formData = formService.getStartFormData(processDefinition.getId());
		List<FormProperty> f=formData.getFormProperties();
		System.out.println("----------");
		System.out.println(f.size());
		for(FormProperty formProperty:f) {
			System.out.println("--------------------");
			System.out.println(formProperty.getId());
			System.out.println(formProperty.getName());
			System.out.println(formProperty.getValue());
			System.out.println(formProperty.getType());
		}
		System.out.println("///////////////////////////");
		System.out.println(formData.getFormKey());
		System.out.println(formData.getFormProperties());
		
		List<Task> taskList=taskService.createTaskQuery().taskAssignee("Tom").list();
		logger.info("task的数量:"+taskList.size());
		for(Task task:taskList) {
			logger.info("--------------------------");
			logger.info(task.getAssignee());
			logger.info(task.getName());
		}
		
	}

}
