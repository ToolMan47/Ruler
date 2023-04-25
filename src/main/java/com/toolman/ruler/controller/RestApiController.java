package com.toolman.ruler.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/rest")
public class RestApiController {
    /**
     * Test API resource
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


}
