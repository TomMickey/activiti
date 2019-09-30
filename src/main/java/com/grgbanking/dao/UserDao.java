package com.grgbanking.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.grgbanking.entity.User;

@Repository
@Mapper
public interface UserDao {

	List<User> getUsersByGroupName(String groupName);
}
