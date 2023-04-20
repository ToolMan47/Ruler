package com.toolman.ruler.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toolman.ruler.dao.UserRepository;
import com.toolman.ruler.entity.Role;
import com.toolman.ruler.entity.User;

@Service
public class IntroService {

    @Autowired
    UserRepository userRepository;

    public Map<String, Set<String>> getDemoList() {
	List<User> allUser = userRepository.findAll();

	return allUser.stream()
		.collect(Collectors.toMap(User::getUsername,
			user -> user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())));
    }
}
