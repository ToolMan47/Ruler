package com.toolman.ruler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * this controller is for getting web view
 * @author ToolMan47
 *
 */
@Controller()
public class IndexController {

    @GetMapping("/")
    public String indexPage() {
	return "index";
    }
    
    @GetMapping("intro")
    public String introPage() {
	return "intro";
    }
    
    
    
    
}
