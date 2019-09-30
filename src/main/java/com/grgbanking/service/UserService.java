package com.grgbanking.service;

import java.util.List;

import com.grgbanking.entity.User;

public interface UserService {
	
	List<User> getUsersByGroupName(String groupName);

}
