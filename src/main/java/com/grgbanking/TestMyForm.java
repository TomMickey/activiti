package com.grgbanking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMyForm {
	
	//日志
	private static Logger logger=LoggerFactory.getLogger(TestMyForm.class);
	
	public static void main(String[] args) {
		
		//初始化
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		
		//runtimeService
		RuntimeService runtimeService=processEngine.getRuntimeService();
		
		//taskService
		TaskService taskService=processEngine.getTaskService();
		
		//formService
		FormService formService=processEngine.getFormService();
		
		//repositoryService
		RepositoryService repositoryService=processEngine.getRepositoryService();
		
		//返回一个部署对象
		Deployment deployment=repositoryService//与流程定义和部署对象相关的service
			.createDeployment()//创建一个部署对象
			.name("activiti子流程测试-整合嵌入式流程和调用式流程")//给对象起个名称
			.addClasspathResource("diagrams/TestForm.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("diagrams/TestForm.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		
		String message="myMessageStart";
		
		Map<String,Object> map=new HashMap<>();
		
		User user=new User();
		
		user.setReason("肚子疼");
		
		user.setLeaveDays(9);
		
		user.setStartTime("2019-09-30");
		
		user.setEndTime("2019-10-07");
		
		map.put("user", user);
		
		map.put("leaveDays", 12);
		
		map.put("user1", "Tom");
		
		ProcessInstance processInstance=runtimeService.startProcessInstanceByMessage(message,map);
		
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
		
		TaskFormData taskFormData=formService.getTaskFormData(task.getId());
		
		List<FormProperty> formProperties=taskFormData.getFormProperties();
		
		for(FormProperty formProperty:formProperties) {
			System.out.println("@@@@@@@@@@@@@@@@@@@@@");
			System.out.println(formProperty.getId());
			System.out.println(formProperty.getName());
			System.out.println(formProperty.getValue());
			System.out.println(formProperty.getType());
			System.out.println("####################");
		}
		
		taskService.complete(task.getId());
		
		task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		
		System.out.println("__________________________________");
		
		System.out.println(task.getId());
		
		System.out.println(task.getDescription());
		
		System.out.println(task.getOwner());
		
		System.out.println(task.getAssignee());
		
		System.out.println(task.getName());
	}
}
