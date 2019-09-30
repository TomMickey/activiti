package com.grgbanking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grgbanking.entity.R;
import com.grgbanking.entity.User;
import com.grgbanking.service.UserService;

@RestController
@RequestMapping("/index")
public class HelloController {
	
	private ProcessEngine processEngine;
	
	@Autowired
	private UserService userService;
	
	private TaskService taskService;
	
	public HelloController() {
		this.processEngine=ProcessEngines.getDefaultProcessEngine();
		//this.taskService=this.processEngine.getTaskService();
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("/startProcess")
	public R startProcess(@Param("groupName")String groupName,@Param("key")String key) {
		this.processEngine=ProcessEngines.getDefaultProcessEngine();
		List<User> users=userService.getUsersByGroupName(groupName);
		String name="";
		for(User user:users) {
			name=name+","+user.getAccount();
		}
		System.out.println(users);
		Map<String,Object> map=new HashMap<>();
		map.put("student", name);
		System.out.println(map);
		System.out.println(processEngine);
		ProcessInstance processInstance=this.processEngine.getRuntimeService().startProcessInstanceByKey(key, map);
		return R.ok().put("users", users);
	}
	
	@RequestMapping("/getUsers")
	public R getUsers(@Param("student")String student,@Param("taskId")String taskId) {
		this.processEngine=ProcessEngines.getDefaultProcessEngine();
		String usList=(String)this.processEngine.getTaskService().getVariable(taskId, student);
		System.out.println(usList);
		return R.ok();
	}
	
	@RequestMapping("/getTask")
	public R getTask() {
		this.processEngine=ProcessEngines.getDefaultProcessEngine();
		//任务办理人
		String candidateUser = "A";
		List<Task> list = this.processEngine.getTaskService()//
						.createTaskQuery()//
						.taskCandidateUser(candidateUser)//参与者，组任务查询
						.list();
		if(list!=null && list.size()>0){
			for(Task task:list){
				System.out.println("任务ID："+task.getId());
				System.out.println("任务的办理人："+task.getAssignee());
				System.out.println("任务名称："+task.getName());
				System.out.println("任务的创建时间："+task.getCreateTime());
				System.out.println("流程实例ID："+task.getProcessInstanceId());
				System.out.println("#######################################");
			}
		}
		return R.ok();
	}
	
	@Test
	public void completeTask() {
		this.processEngine=ProcessEngines.getDefaultProcessEngine();
		TaskService taskService=this.processEngine.getTaskService();
		taskService.setVariable("17506", "day",7);
		taskService.complete("17506");
	}

}
