package com.grgbanking;


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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLeaveProcess {
	
	//日志
	private Logger logger=LoggerFactory.getLogger(TestLeaveProcess.class);
	
	@Test
	public void test() {
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
			.addClasspathResource("bpmn/LeaveProcess.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("bpmn/LeaveProcess.png")//加载资源,一次只能加载一个
			.addClasspathResource("bpmn/SubProcess.bpmn")//加载资源,一次只能加载一个
			.addClasspathResource("bpmn/SubProcess.png")//加载资源,一次只能加载一个
			.deploy();//完成部署
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery()
				.deploymentId(deployment.getId())
				.processDefinitionKey("myProcess").singleResult();
		
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
//		Map<String,Object> map=new HashMap<>();
//		map.put("users", "Tom,Jack,Lucy,Lee");
//		String messageName="startMsg";
//		ProcessInstance processInstance=runtimeService.startProcessInstanceByMessage(messageName, map);
//		
//		logger.info("-----------------------");
//		logger.info("开始输出当前的流程实例名称:"+processInstance.getProcessDefinitionName());
//		
//		String taskCandidateUser="Lee";
//		Task task=taskService.createTaskQuery().taskCandidateUser(taskCandidateUser).singleResult();
//		logger.info("----------查询组成员的任务---------------");
//		logger.info("组任务的名称:"+task.getName());
//		
//		Map<String,Object> m=task.getProcessVariables();
//		logger.info("-----输出当前任务的变量");
//		logger.info(""+m);
//		
//		task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
//		
//		logger.info("-----------------------");
//		logger.info("输出当前主流程任务的名称:"+task.getName());
//		
//		logger.info("完成当前主流程任务-------------------------------");
//		logger.info("设置嵌入式流程的变量--------------------");
//		taskService.setVariable(task.getId(), "head", "Head");
//		taskService.complete(task.getId());
		
//		logger.info("开始查询当前任务--------------------------------");
//		
//		task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
//		
//		logger.info("输出当前嵌入式流程任务的名称:"+task.getName());
//		logger.info("完成当前任务-------------------------------");
//		taskService.complete(task.getId());
//		
//		task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
//		
//		logger.info("输出当前主流程任务的名称:"+task.getName());
//		logger.info("完成当前主流程任务-------------------------------");
//		taskService.setVariable(task.getId(), "totle", "totletotle");
//		taskService.complete(task.getId());
//		
//		
//		logger.info("-----------------查询调用式流程的任务--------------------------");
//		ProcessInstance prInstance=runtimeService.createProcessInstanceQuery().superProcessInstanceId(processInstance.getId()).singleResult();
//		
//		logger.info("输出调用式流程定义的名称:"+prInstance.getProcessDefinitionName());
//		task=taskService.createTaskQuery().processInstanceId(prInstance.getId()).singleResult();
//		
//		logger.info("输出当前调用式流程任务的名称:"+task.getName());
//		logger.info("当前任务的办理人:"+task.getAssignee());
//		logger.info("完成当前任务-------------------------------");
//		taskService.complete(task.getId());
//		
//		task=taskService.createTaskQuery().processInstanceId(prInstance.getId()).singleResult();
//		
//		logger.info("输出当前调用式流程任务的名称:"+task.getName());
//		logger.info("当前任务的办理人:"+task.getAssignee());
//		logger.info("完成当前任务-------------------------------");
//		taskService.complete(task.getId());
//		
////		task=taskService.createTaskQuery().processInstanceId(prInstance.getId()).singleResult();
////		
////		logger.info("输出当前调用式流程任务的名称:"+task.getName());
////		logger.info("完成当前任务-------------------------------");
		
	}

}
