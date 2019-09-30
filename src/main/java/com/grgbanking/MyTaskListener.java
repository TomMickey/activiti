package com.grgbanking;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8535626741437969091L;

	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.addCandidateUser("Tom");
		delegateTask.addCandidateUser("Jack");
		delegateTask.addCandidateUser("Lucy");
	}

}
