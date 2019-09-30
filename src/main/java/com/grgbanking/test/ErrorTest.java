package com.grgbanking.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorTest {
	
	//日志
	private Logger logger=LoggerFactory.getLogger(MessageTest.class);
	
	@Test
	public void test() {
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
			.addClasspathResource("diagrams/Error.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("diagrams/Error.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		Map<String,Object> map=new HashMap();
		map.put("user", "Tom");
		
		ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("myProcess",map);
				//startProcessInstanceByKey("myProcess");
				//.startProcessInstanceById(processDefinition.getId());
		
		taskService.complete("10");
	}

}
