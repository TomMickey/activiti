package com.grgbanking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grgbanking.dao.UserDao;
import com.grgbanking.entity.User;
import com.grgbanking.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getUsersByGroupName(String groupName) {
		System.out.println(groupName);
		return userDao.getUsersByGroupName(groupName);
	}

}
