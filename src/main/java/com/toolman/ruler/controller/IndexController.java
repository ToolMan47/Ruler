package com.toolman.ruler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * this controller is for getting web view
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
    public String indexPage() {
	return "index";
    }
    
    @GetMapping("/intro")
    public String introPage() {
	return "intro";
    }
    
    
    
    
}
