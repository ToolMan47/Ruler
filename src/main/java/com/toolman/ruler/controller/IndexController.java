package com.toolman.ruler.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.toolman.ruler.service.IntroService;

/**
 * this controller is for getting web view
 * 
 * @author ToolMan47
 *
 */
@Controller()
@RequestMapping("/**")
public class IndexController {
    
    @Autowired
    private IntroService introService;

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
    public ModelAndView introPage(ModelAndView model) {
	
	Map<String, Set<String>> demoList = introService.getDemoList();
	
	demoList.entrySet().stream().forEach(entry -> {
	    String key = entry.getKey();
	    Set<String> value = entry.getValue();
	    System.out.println("Key: " + key + " Value: " + value.toString());
	});
	
	model.addObject("demoList", demoList);
	model.setViewName("intro");
	return model;
    }

}
