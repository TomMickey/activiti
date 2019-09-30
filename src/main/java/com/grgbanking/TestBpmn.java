package com.grgbanking;

import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.junit.Test;

public class TestBpmn {
	
	@Test
	public void test() {
		//实例化BpmnModel对象
		BpmnModel bpmnModel=new BpmnModel();
		//开始节点的属性
		StartEvent startEvent=new StartEvent();
		startEvent.setId("start1shareniu");
		startEvent.setName("start1shareniu");
		//普通的UserTask节点
		UserTask userTask=new UserTask();
		userTask.setId("userTask1shareniu");
		userTask.setName("userTask1shareniu");
		//结束节点属性
		EndEvent endEvent=new EndEvent();
		endEvent.setId("endEventshareniu");
		endEvent.setName("endEventshareniu");
		//连线信息
		List<SequenceFlow> sequenceFlows=new ArrayList<SequenceFlow>();
		List<SequenceFlow> toEnd=new ArrayList<SequenceFlow>();
		SequenceFlow s1=new SequenceFlow();
		s1.setId("starttouserTask");
		s1.setName("starttouserTask");
		s1.setSourceRef("start1shareniu");
		s1.setTargetRef("userTask1shareniu");
		sequenceFlows.add(s1);
		SequenceFlow s2=new SequenceFlow();
		s2.setId("userTasktoend");
		s2.setName("userTasktoend");
		s2.setSourceRef("userTask1shareniu");
		s2.setTargetRef("endEventshareniu");
		toEnd.add(s2);
		startEvent.setOutgoingFlows(sequenceFlows);
		userTask.setOutgoingFlows(toEnd);
		userTask.setIncomingFlows(sequenceFlows);
		endEvent.setIncomingFlows(toEnd);
		org.activiti.bpmn.model.Process process=new org.activiti.bpmn.model.Process();
		//Process对象
		//Process process=new Process();
		//process
		process.setId("process1");
		process.addFlowElement(startEvent);
		process.addFlowElement(s1);
		process.addFlowElement(userTask);
		process.addFlowElement(s2);
		process.addFlowElement(endEvent);
		bpmnModel.addProcess(process);
		
		BpmnXMLConverter bpmnXMLConverter=new BpmnXMLConverter();

		byte[] convertToXML= bpmnXMLConverter.convertToXML(bpmnModel);

		String bytes=new String(convertToXML);

		System.out.println(bytes);
	}

}
