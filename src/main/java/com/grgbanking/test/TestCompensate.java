package com.grgbanking.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCompensate {
	
	//日志
	private static Logger logger=LoggerFactory.getLogger(TestCompensate.class);
	
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
			.name("activiti子流程测试-整合嵌入式流程和调用式流程")//给对象起个名称
			.addClasspathResource("bpm/Compensate.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("bpm/Compensate.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		
		String message="myTestCom";
		
		ProcessInstance processInstance=runtimeService.startProcessInstanceByMessage(message);
				//startProcessInstanceByKey("myProcess");
				//.startProcessInstanceById(processDefinition.getId());
		
		List<Task> taskList=taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
		logger.info("task的数量:"+taskList.size());
		for(Task task:taskList) {
			logger.info("--------------------------");
			logger.info(task.getAssignee());
			logger.info(task.getName());
		}
	}
}
