package com.grgbanking.newPackage;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.grgbanking.entity.User;

public class MessageTest {
	
	//日志
	private Logger logger=LoggerFactory.getLogger(MessageTest.class);
	
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
		//返回一个部署对象
		Deployment deployment=repositoryService//与流程定义和部署对象相关的service
			.createDeployment()//创建一个部署对象
			.name("activiti子流程测试-整合嵌入式流程和调用式流程")//给对象起个名称
			.addClasspathResource("bpm/TestBpmn.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("bpm/TestBpmn.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		
		User user=new User();
		
		user.setUsername("Jack");
		
		user.setAddress("广州市");
		
		Map<String,Object> map=new HashMap<>();
		
		map.put("user", user);
		
		ProcessInstance processInstance=runtimeService.startProcessInstanceByMessage("myMessage",map);
		
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		
		System.out.println(task.getName());
		
		taskService.complete(task.getId());
		
	}

}
