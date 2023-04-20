package com.toolman.ruler.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.toolman.ruler.dao.UserRepository;

/**
 * 此內的方法要有權限才可以使用
 * 
 * @author ToolMan
 *
 */
@Service
@Secured("ADMIN")
public class AdminService {
    
    @Value("${spring.security.user.name}")
    private String configUser;
    
    @Value("${spring.security.user.password}")
    private String configPwd;
    
    @Autowired
    UserRepository userRepository;

    public Map<String, String> getSecretMap() {
	Map<String, String> secretMap = new HashMap<>();
	
	secretMap.put("user", configUser);
	secretMap.put("pwd", configPwd);
	
	return secretMap;
    }

}
