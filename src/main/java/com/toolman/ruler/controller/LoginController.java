package com.toolman.ruler.controller;

import com.toolman.ruler.entity.User;
import com.toolman.ruler.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller()
@RequestMapping()
public class LoginController {
    @Autowired
    JwtUtils jwtUtils;

    /**
     * 取得 JWT
     * @return token in header Authorization
     */
    @PostMapping("/generateToken")
    public ResponseEntity<String> getToken(@RequestBody User user) {
        log.info("user:{}", user);
        String name = user.getUsername();
        String pwd = user.getPassword();

        // TODO 一些通行條件判斷

        String jwt = jwtUtils.generateToken(name);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);
        return new ResponseEntity<>("JWT generated", headers, HttpStatus.OK);

    }


    @GetMapping("/login")
    public String defaultPage() {
	return "login";
    }

}
