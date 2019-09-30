package com.grgbanking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

public class TestActiviti {
	
	//日志输出 slf4j日志
	private Logger logger=LoggerFactory.getLogger(TestActiviti.class);
	//默认读取classpath下的activiti.cfg.xml文件
	//初始化流程引擎
	private ProcessEngine processEngine;
	
	public TestActiviti() {
		this.processEngine=ProcessEngines.getDefaultProcessEngine();
	}
	
	//警告:Establishing SSL connection without server’s identity verification is not recommended
	//请注意:不建议在没有服务器身份验证的情况下建立SSL连接。
	//根据MySQL 5.5.45+、5.6.26+和5.7.6+的要求，如果不设置显式选项，则必须建立默认的SSL连接。
	//您需要通过设置useSSL=false显式地禁用SSL，或者设置useSSL=true并为服务器证书验证提供信任存储
	
	//api方式  mysql使用5版本
	@Test
	public void test1() {
		ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
	    pec.setJdbcUrl("jdbc:mysql://192.168.56.104:3306/activiti?characterEncoding=utf-8&serverTimezone=UTC");
	    pec.setJdbcDriver("com.mysql.jdbc.Driver");
	    pec.setJdbcUsername("root");
	    pec.setJdbcPassword("1234");
	    //创建表策略
	    pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
	    pec.setAsyncExecutorActivate(false);
	    ProcessEngine processEngine = pec.buildProcessEngine();
	    logger.info("创建数据库成功!");
	}
	
	//	表说明  https://www.cnblogs.com/telwanggs/p/7491564.html
	
	//	act_re_deployment 部署对象表
	//	act_re_procdef 流程定义表
	//	act_ge_bytearray 资源文件表
	//	act_ge_property 主键生成策略表
	//	act_ru_task 正在执行的任务表  针对节点是usertask
	//	act_hi_taskinst 任务历史表 针对节点是usertask
	//	act_hi_actinst 所有活动节点的历史表
	//	act_evt_log
	//  ACT_GE_* : “GE”代表“General”（通用），用在各种情况下；
	//  act_ge_bytearra、act_ge_property   
	//	ACT_HI_* : “HI”代表“History”（历史），这些表中保存的都是历史数据，比如执行过的流程实例、变量、任务，等等。
	//	Activit默认提供了4种历史级别：
	//Ø  none: 不保存任何历史记录，可以提高系统性能；
	//Ø  activity：保存所有的流程实例、任务、活动信息；
	//Ø  audit：也是Activiti的默认级别，保存所有的流程实例、任务、活动、表单属性；
	//Ø  full：最完整的历史记录，除了包含audit级别的信息之外还能保存详细，例如：流程变量。
	//	| act_hi_actinst      |
	//	| act_hi_attachment   |
	//	| act_hi_comment      |
	//	| act_hi_detail       |
	//	| act_hi_identitylink |
	//	| act_hi_procinst     |
	//	| act_hi_taskinst     |
	//	| act_hi_varinst      |
		
	//  ACT_ID_* : “ID”代表“Identity”（身份），这些表中保存的都是身份信息，如用户和组以及两者之间的关系。
	//	如果Activiti被集成在某一系统当中的话，这些表可以不用，可以直接使用现有系统中的用户或组信息；
	//	| act_id_group        |
	//	| act_id_info         |
	//	| act_id_membership   |
	//	| act_id_user         |
		
	
	//	| act_procdef_info    |
		
	//  ACT_RE_* : “RE”代表“Repository”（仓库），这些表中保存一些‘静态’信息，如流程定义和流程资源（如图片、规则等）；
	//	| act_re_deployment   |
	//	| act_re_model        |
	//	| act_re_procdef      |
		
	//  ACT_RU_* : “RU”代表“Runtime”（运行时），这些表中保存一些流程实例、用户任务、变量等的运行时数据。
	//	Activiti只保存流程实例在执行过程中的运行时数据，并且当流程结束后会立即移除这些数据，这是为了保证运行时表尽量的小并运行的足够快；
	//	| act_ru_event_subscr |
	//	| act_ru_execution    |
	//	| act_ru_identitylink |
	//	| act_ru_job          |
	//	| act_ru_task         |
	//	| act_ru_variable
	
	//配置文件方式  mysql使用5版本
	@Test
	public void test2() {
		ProcessEngine processEngine=
				ProcessEngineConfiguration.
				createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		logger.info("配置文件+创建数据库成功!");
	}
	
	//部署流程定义
	@Test
	public void deploy() {
		//返回一个部署对象
		Deployment deployment=processEngine.getRepositoryService()//与流程定义和部署对象相关的service
						.createDeployment()//创建一个部署对象
						.name("activiti入门示例-测试动态绑定")//给对象起个名称
						.addClasspathResource("bpmn/MySubProcess.bpmn")//加载资源,一次只能加载一个
						.addClasspathResource("bpmn/MySubProcess.png")//加载资源,一次只能加载一个
						.deploy();//完成部署
		//部署表:act_re_deployment 
		//流程定义表:act_re_procdef
		//每执行一次升级一个版本 
		logger.info("部署id:"+deployment.getId());
		logger.info("部署名称:"+deployment.getName());
		logger.info("部署时间:"+deployment.getDeploymentTime());
	}
	
	//部署流程定义
	@Test
	public void deployT() {
		//返回一个部署对象
		Deployment deployment=processEngine.getRepositoryService()//与流程定义和部署对象相关的service
						.createDeployment()//创建一个部署对象
						.name("activiti入门示例-测试动态绑定")//给对象起个名称
						.addClasspathResource("Test.bpmn")//加载资源,一次只能加载一个
						.addClasspathResource("Test.png")//加载资源,一次只能加载一个
						.deploy();//完成部署
		//部署表:act_re_deployment 
		//流程定义表:act_re_procdef
		//每执行一次升级一个版本 
		logger.info("部署id:"+deployment.getId());
		logger.info("部署名称:"+deployment.getName());
		logger.info("部署时间:"+deployment.getDeploymentTime());
	}
	
	//部署流程定义
	@Test
	public void deployTest() {
		//返回一个部署对象
		Deployment deployment=processEngine.getRepositoryService()//与流程定义和部署对象相关的service
						.createDeployment()//创建一个部署对象
						.name("activiti入门示例-测试条件判断")//给对象起个名称
						.addClasspathResource("MyLeave.bpmn")//加载资源,一次只能加载一个
						.addClasspathResource("MyLeave.png")//加载资源,一次只能加载一个
						.deploy();//完成部署
		//部署表:act_re_deployment 
		//流程定义表:act_re_procdef
		//每执行一次升级一个版本 
		logger.info("部署id:"+deployment.getId());
		logger.info("部署名称:"+deployment.getName());
		logger.info("部署时间:"+deployment.getDeploymentTime());
	}
	
	//部署流程定义
	@Test
	public void deployMyProcess() {
		//返回一个部署对象
		Deployment deployment=processEngine.getRepositoryService()//与流程定义和部署对象相关的service
						.createDeployment()//创建一个部署对象
						.name("activiti入门示例-测试条件判断")//给对象起个名称
						.addClasspathResource("MyProcess.bpmn")//加载资源,一次只能加载一个
						.addClasspathResource("MyProcess.png")//加载资源,一次只能加载一个
						.deploy();//完成部署
		//部署表:act_re_deployment 
		//流程定义表:act_re_procdef
		//每执行一次升级一个版本 
		logger.info("部署id:"+deployment.getId());
		logger.info("部署名称:"+deployment.getName());
		logger.info("部署时间:"+deployment.getDeploymentTime());
	}
	
	//部署流程定义
	@Test
	public void deployTestActiviti() {
		//返回一个部署对象
		Deployment deployment=processEngine.getRepositoryService()//与流程定义和部署对象相关的service
						.createDeployment()//创建一个部署对象
						.name("activiti入门示例-测试条件判断")//给对象起个名称
						.addClasspathResource("TestActiviti.bpmn")//加载资源,一次只能加载一个
						.addClasspathResource("TestActiviti.png")//加载资源,一次只能加载一个
						.deploy();//完成部署
		//部署表:act_re_deployment 
		//流程定义表:act_re_procdef
		//每执行一次升级一个版本 
		logger.info("部署id:"+deployment.getId());
		logger.info("部署名称:"+deployment.getName());
		logger.info("部署时间:"+deployment.getDeploymentTime());
	}
	
	//zip部署流程定义
	@Test
	public void deployZip() {
		//输入流
		InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("resources.zip");
		//输入流
		ZipInputStream zipInputStream=new ZipInputStream(inputStream);
		//返回一个部署对象
		Deployment deployment=processEngine.getRepositoryService()//与流程定义和部署对象相关的service
						.createDeployment()//创建一个部署对象
						.name("压缩包activiti入门示例")//给对想起个名称
						.addZipInputStream(zipInputStream)//zip方式
						.deploy();//完成部署
		logger.info("部署id:"+deployment.getId());
		logger.info("部署名称:"+deployment.getName());
		logger.info("部署时间:"+deployment.getDeploymentTime());
	}
	
	//查询流程定义
	@Test
	public void getProcessDefitition() {
		List<ProcessDefinition> processDefinitions=processEngine.getRepositoryService()
				.createProcessDefinitionQuery()//创建流程定义查询
				//.processDefinitionNameLike("My")
				//.processDefinitionName("My process")
				//.orderByProcessDefinitionVersion().desc()
				.processDefinitionNameLike("%pro%")
				.list();
		//流程定义表:act_re_procdef
		for(ProcessDefinition definition:processDefinitions) {
			logger.info("流程定义id:"+definition.getId());
			logger.info("流程定义关联部署id:"+definition.getDeploymentId());
			//启动流程实例的key值
			logger.info("流程定义key值:"+definition.getKey());
			logger.info("流程定义Diagram资源名称:"+definition.getDiagramResourceName());
			logger.info("流程定义资源名称:"+definition.getResourceName());
			logger.info("流程定义版本:"+definition.getVersion());
			logger.info("流程定义描述:"+definition.getDescription());
			logger.info(definition.getName());
			logger.info("-------------------------\n");
		}
	}
	
	
	/**
	 * 查询流程图
	 * @throws IOException 
	 */
	@Test
	public void getPicture() throws IOException {
		List<String> list=processEngine.getRepositoryService()
				.getDeploymentResourceNames("1");
		String resourceName=null;
		for(String str:list) {
			System.out.println(str);
			if(str.endsWith(".png")) {
				resourceName=str;
				break;
			}
		}
		InputStream inputStream=processEngine.getRepositoryService().getResourceAsStream("1", resourceName);
		File file=new File("txt.png");
		FileUtils.copyInputStreamToFile(inputStream,file);
	}
	//创建Activiti用户
  	@Test
  	public void addUser( ){	
  		//项目中每创建一个新用户，对应的要创建一个Activiti用户,两者的userId和userName一致
  		IdentityService identityService=processEngine.getIdentityService();
  		//添加用户 
  		//全国总行+省分行 100
  		//市分行 100
  		//区县支行100 
  		//员工编号 101开始
  		for(int i=0;i<100;i++) {
  			User user1 = identityService.newUser("user"+i);
  	  		user1.setFirstName("张三");
  	  		user1.setLastName("张");
  	  		user1.setPassword("123456");
  	  		user1.setEmail("zhangsan@qq.com");
  	  	    identityService.saveUser(user1);
  		}
  	}
  	
  	//创建Activiti用户组
  	@Test
  	public void addGroup( ) {
  		
  		IdentityService identityService=processEngine.getIdentityService();
  		Group group1 = identityService.newGroup("group1");
        group1.setName("广东省广州市天河支行");
        group1.setType("员工组");
        identityService.saveGroup(group1);
        
        Group group2 = identityService.newGroup("group2");
        group2.setName("广东省广州市天河支行");
        group2.setType("总监阻");
        identityService.saveGroup(group2);
        
        Group group3 = identityService.newGroup("group3");
        group3.setName("广东省广州市天河支行");
        group3.setType("经理组");
        identityService.saveGroup(group3);
        
        Group group4 = identityService.newGroup("group4");
        group4.setName("广东省广州市天河支行");
        group4.setType("人力资源组");
        identityService.saveGroup(group4);
        
        Group group6 = identityService.newGroup("group6");
        group6.setName("广东省广州市分行");
        group6.setType("总监阻");
        identityService.saveGroup(group6);
        
        Group group7 = identityService.newGroup("group7");
        group7.setName("广东省广州市分行");
        group7.setType("经理组");
        identityService.saveGroup(group7);
        
        Group group8 = identityService.newGroup("group8");
        group8.setName("广东省广州市分行");
        group8.setType("人力资源组");
        identityService.saveGroup(group8);
  	}

    //创建Activiti（用户-用户组）关系
   	@Test
   	public void addMembership(){
   		IdentityService identityService=processEngine.getIdentityService();
   		identityService.createMembership("user1", "group1");//user1 在员工阻
   		identityService.createMembership("user2", "group1");//user2在总监组
   		identityService.createMembership("user3", "group1");//user3在经理组
   		identityService.createMembership("user4", "group1");//user4在人力资源组
   	}

	//启动流程
	@Test
	public void startProcessInstance() {
		//设置环境变量
		Map<String,Object> map=new HashMap<>();
		map.put("请假原因", "肚子疼");
		map.put("请假时间", new Date());
		//返回一个流程实例
		//根据流程定义的key之启动流程实例  启动最高版本的流程定义
		ProcessInstance processInstance=processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
						.startProcessInstanceByKey("myProcessTest",map);//通过流程定义的key启动流程实例
		//启动流程实例的同时设置变量
		//processEngine.getRuntimeService().startProcessInstanceById(processDefinitionId, variables)
		//流程实例表 act_ru_execution
		//输出流程实例属性
		logger.info("流程实例id:"+processInstance.getId());//流程实例id
		logger.info("流程定义id:"+processInstance.getProcessDefinitionId());//流程定义id
		logger.info("流程实例当前执行的id:"+processInstance.getActivityId());				
	}
	
	//10、启动流程实例--使用流程变量 userId
	@Test
	public void startProcessInstance2() {
		// 流程定义的Key
		String processDefinitionKey = "myProcess";
		Map<String, Object> variables = new HashMap<String, Object>();
		List<String> list=new ArrayList<>();
		list.add("001");
		list.add("002");
		list.add("003");
		variables.put("candiateUserList", list);
		variables.put("users", "Tom,Jack,Lucy");
//		//这里传入id为user1的用户
//		variables.put("branchPerson", "老陈");
//		variables.put("day", 10);
//		//这里传入id为user1的用户
//		variables.put("group1", "Obama,老特");
		ProcessInstance processInstance = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service
				.startProcessInstanceByKey(processDefinitionKey,variables);// 使用流程定义的key启动流程实例，key对应leave.bpmn文件中id的属性
		logger.info("流程实例ID:" + processInstance.getId());// 流程实例ID: 
		logger.info("流程定义ID:" + processInstance.getProcessDefinitionId());// 流 
	
	}
	
	//查询当前人的组任务
    @Test
    public void findMyGroupTask() {
        String candidateUser = "支行张三三";
        List<Task> list = processEngine.getTaskService()// 与正在执行的任务管理相关的Service
                .createTaskQuery()// 创建任务查询对象
                /** 查询条件（where部分） */
                .taskCandidateUser(candidateUser)// 组任务的办理人查询
                /** 排序 */
                .orderByTaskCreateTime().asc()// 使用创建时间的升序排列
                /** 返回结果集 */
                .list();// 返回列表
        if (list != null && list.size() > 0) {
            for (Task task : list) {
            	logger.info("任务ID:" + task.getId());
            	logger.info("任务名称:" + task.getName());
            	logger.info("任务的创建时间:" + task.getCreateTime());
            	logger.info("任务的办理人:" + task.getAssignee());
            	logger.info("流程实例ID：" + task.getProcessInstanceId());
            	logger.info("执行对象ID:" + task.getExecutionId());
            	logger.info("流程定义ID:" + task.getProcessDefinitionId());
            	logger.info("########################################################");
            }
        }
    }

	//查询任务
	@Test
	public void getTask() {
		List<Task> list=processEngine.getTaskService()//得到正在执行任务的相关service
				.createTaskQuery()//创建查询
				.taskCandidateUser("Tom")
				//.taskAssignee("Tom")//指定任务所有人
				.list();
		if(list.size()>0&&list!=null) {
			//遍历任务
			for(Task task:list) {
				logger.info("任务的id:"+task.getId());
				logger.info("任务名称:"+task.getName());
				logger.info("任务的创建时间:"+task.getCreateTime());
				logger.info("任务的办理人:"+task.getAssignee());
				logger.info("流程定义的id:"+task.getProcessDefinitionId());
				logger.info("流程实例的id:"+task.getProcessInstanceId());
			}
		}
	}
	
	//完成任务
	@Test
	public void completeTask() {
		TaskService taskService=processEngine.getTaskService();
		//RuntimeService runtimeService=processEngine.getRuntimeService();
		//runtimeService.
		//taskService.
		//taskService.setVariable("37510", "branchStart", true);
		//taskService.setVariable("37510", "userId1","Jack6666");
		//taskService.setVariable("10009", "agree",true);
		//taskService.setVariable("5006", "userId2","Jack1");
		//taskService.
		taskService.complete("7504");
	}
	
	@Test
	public void getHistoryTask() {
		
	}
	//查询流程是否结束
	@Test
	public void getProcessInstance() {
		ProcessInstance processInstance=processEngine.getRuntimeService()//
				.createProcessInstanceQuery()
				.processInstanceId("2501")//
				.singleResult();
		if(processInstance!=null) {
			logger.info("流程没有结束!");
		}else {
			logger.info("流程已结束!");
		}
				
	}
	//查询历史任务
	@Test
	public void getHistoryTask1() {
		List<HistoricTaskInstance> historicTaskInstances =processEngine.getHistoryService()
						.createHistoricTaskInstanceQuery()//
						.taskAssignee("李四")
						.list();
		for(HistoricTaskInstance historicTaskInstance:historicTaskInstances) {
			logger.info(""+historicTaskInstance);
		}
	}
	
	//设置环境变量
	@Test
	public void setValue() {
		TaskService taskService=processEngine.getTaskService();
		taskService.setVariableLocal("7502", "请假天数1", 3);//与任务进行绑定
	}
	
	//获得流程变量
	@Test
	public void getValue() {
		TaskService taskService=processEngine.getTaskService();
		Integer days=(Integer) taskService.getVariable("7502", "请假天数");
		Date date=(Date) taskService.getVariable("7502", "请假时间");
		String reason=(String) taskService.getVariable("7502", "请假原因");
		logger.info("请假天数:"+days);
		logger.info("请假时间:"+date);
		logger.info("请假原因:"+reason);
	}
	
	//设置实体变量
	@Test
	public void setEntityValue() {
//		User user=new User();
//		user.setAccount("超级用户");
//		user.setPassword("admin");
//		TaskService taskService=processEngine.getTaskService();
//		String taskId="7502";
//		taskService.setVariable(taskId, "账号信息", user);
	}
	//得到实体变量
	@Test
	public void getEntityValue() {
		TaskService taskService=processEngine.getTaskService();
		User user=(User) taskService.getVariable("7502", "账号信息");
		logger.info("得到用户变量:"+user);
	}

}















