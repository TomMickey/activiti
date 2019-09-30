package com.grgbanking.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TestForm {
	
	//初始化
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	//runtimeService
	private RuntimeService runtimeService=processEngine.getRuntimeService();
	
	//taskService
	private TaskService taskService=processEngine.getTaskService();
	
	//repositoryService
	private RepositoryService repositoryService=processEngine.getRepositoryService();
	
	//formService
	private FormService formService=processEngine.getFormService();
	
	//histortService
	private HistoryService historyService=processEngine.getHistoryService();
	
	private Map<String,List<FormProperty>> formList=new ConcurrentHashMap();
	
	@Test
	public void form() {
		
		//返回一个部署对象
		Deployment deployment=repositoryService//与流程定义和部署对象相关的service
			.createDeployment() //创建一个部署对象
			.name("activiti内置表单测试") //给对象起个名称
			.addClasspathResource("diagrams/TestForm.bpmn") //加载资源,一次只能加载一个
			.addClasspathResource("diagrams/TestForm.png") //加载资源,一次只能加载一个
			.deploy(); //完成部署
		
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery()
				.deploymentId(deployment.getId())
				.processDefinitionKey("myProcess").singleResult();
		
		Map<String,Object> mapP=new HashMap<String, Object>();
		
		mapP.put("reason", "想去看美女");
		mapP.put("startTime", "2010");
		
		User user=new User();
		user.setEndTime("20111");
		mapP.put("user", user);
		mapP.put("lead", "姚明");
		
		ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("myProcess",mapP);
		
		StartFormData formData = formService.getStartFormData(processDefinition.getId());
		
		List<FormProperty> f=formData.getFormProperties();
		
		System.out.println("表单的属性数量:"+f.size());
		
		Map<String,String> fromValues = new HashMap<String,String>();
		
		for(FormProperty formProperty:f){
			System.out.println("000000000000000000");
			System.out.println(formProperty.getId());
			System.out.println(formProperty.getName());
			System.out.println(formProperty.getValue());
			System.out.println(formProperty.getClass());
			System.out.println(formProperty.getType());
			//String value = request.getParameter(formProperty.getId());//拿取具体参数值
			fromValues.put(formProperty.getId(),formProperty.getValue());//将ID和value存入map中
		}
		
		System.out.println("----------\n"+fromValues);
		
		formService.submitStartFormData(processDefinition.getId(),fromValues);//启动流程，提交表单
		
		Map<String,Object> map=runtimeService.getVariables(processInstance.getId());
		
		System.out.println("输出流程变量的值");
		
		System.out.println(map);
		
		Task task = taskService.createTaskQuery().
				processInstanceId(processInstance.getId()).active().singleResult();
		
		System.out.println(task);
		
		//任务中显示前面传过来的表单值
		TaskFormData taskFormData = formService.getTaskFormData(task.getId());//根据任务ID拿取表单数据
		
		System.out.println(taskFormData);
		//然后可以用这个taskFormData去前台渲染显示。
		//提交任务
		List<FormProperty> formProperties = taskFormData.getFormProperties();//获取表单字段值
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		this.formList.put(task.getId(), formProperties);
		
		System.out.println(formProperties.size());
		
		for(FormProperty formProperty:formProperties){
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(formProperty.getId());
			System.out.println(formProperty.getName());
			System.out.println(formProperty.getValue());
			System.out.println(formProperty.getClass());
			System.out.println(formProperty.getType());
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		}
		
		taskService.complete(task.getId());
		
		task = taskService.createTaskQuery().
				processInstanceId(processInstance.getId()).active().singleResult();
		
		System.out.println("---==========");
		
		System.out.println(task);
		//任务中显示前面传过来的表单值
		TaskFormData taFormData = formService.getTaskFormData(task.getId());//根据任务ID拿取表单数据
		
		System.out.println(taFormData);
		//然后可以用这个taskFormData去前台渲染显示。
		//提交任务
		List<FormProperty> formProperties1 = taFormData.getFormProperties();//获取表单字段值
		
		List<HistoricTaskInstance> list=historyService// 历史相关Service
	            .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
	            .processInstanceId(processInstance.getId()) // 用流程实例id查询
	            .finished() // 查询已经完成的任务
                .orderByTaskCreateTime()//按照任务创建的时间升序
                .asc()//升序
	            .list();
		
		for(HistoricTaskInstance historicTaskInstance:list) {
			List<FormProperty> fList=this.formList.get(historicTaskInstance.getId());
			System.out.println("$$$$$$$$$$$$$$$$$$$$$");
			for(FormProperty formProperty:fList) {
				System.out.println(formProperty.getId());
				System.out.println(formProperty.getName());
				System.out.println(formProperty.getValue());
				System.out.println(formProperty.getClass());
				System.out.println(formProperty.getType());
			}
			System.out.println("$$$$$$$$$$$$$$$$$$$$$");
		}
		
		System.out.println(formProperties1.size());
		
		this.formList.put(task.getId(), formProperties);
		
		for(FormProperty formProperty:formProperties1){
			System.out.println("---------------------------------------");
			System.out.println(formProperty.getId());
			System.out.println(formProperty.getName());
			System.out.println(formProperty.getValue());
			System.out.println(formProperty.getClass());
			System.out.println(formProperty.getType());
			System.out.println("----------------------------------------");
		}
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(formList);
	}

}
