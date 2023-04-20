package com.toolman.ruler.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
public class UserController {
    
    @GetMapping("/user")
    public ModelAndView defaultPage(@AuthenticationPrincipal() UserDetails authUser, ModelAndView model) {
	
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authUser : " + authUser);
//        System.out.println("principal : " + authentication.getPrincipal());
//        System.out.println("Implementing class of UserDetails: " + authentication.getPrincipal().getClass());
//        System.out.println("Implementing class of UserDetailsService: " + userDetailsService.getClass());
	
	model.addObject("name", authUser.getUsername());
	model.addObject("role", authUser.getAuthorities());
	model.setViewName("user");
	return model;
    }

}
