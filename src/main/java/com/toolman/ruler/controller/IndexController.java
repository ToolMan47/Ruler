package com.toolman.ruler.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * this controller is for getting web view
 * 
 * @author ToolMan47
 *
 */
@Controller()
@RequestMapping("/**")
public class IndexController {

//    @GetMapping()
//    public String defaultPage() {
//	return "index";
//    }

    @GetMapping("/")
    public ModelAndView indexPage(@AuthenticationPrincipal() UserDetails authUser, ModelAndView model) {
	if (authUser != null) {
	    model.addObject("name", authUser.getUsername());
	    model.addObject("role", authUser.getAuthorities());
	}

	model.setViewName("index");
	return model;
    }

    @GetMapping("/intro")
    public String introPage() {
	return "intro";
    }

}
