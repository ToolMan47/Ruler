package com.toolman.ruler.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping()
    public ModelAndView defaultPage(@AuthenticationPrincipal() UserDetails authUser, ModelAndView model) {
	model.addObject("name", authUser.getUsername());
	model.addObject("role", authUser.getAuthorities());
	model.setViewName("admin");
	return model;
    }

}
