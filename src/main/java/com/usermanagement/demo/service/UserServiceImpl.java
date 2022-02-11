package com.usermanagement.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.demo.model.User;
import com.usermanagement.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		List<User> userList = userRepository.findAll();
		if(userList!=null && userList.size()>0) {
			return userList;
		}
		return null;
	}

	@Override
	public User addUser(User user) {
		if(user!=null) {
			return userRepository.saveAndFlush(user);
		}
		return null;
	}

	@Override
	public boolean validateUser(String username, String password) {
		User user = userRepository.validateUser(username, password);
		if(user!=null) {
			return true;
		}
		return false;
	}

}
