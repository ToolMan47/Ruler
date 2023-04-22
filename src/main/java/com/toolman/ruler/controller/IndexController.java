package com.toolman.ruler.controller;

import com.toolman.ruler.service.AdminService;
import com.toolman.ruler.service.IntroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Set;

/**
 * this controller is for getting web view
 *
 * @author ToolMan47
 */
@Slf4j
@Controller()
@RequestMapping("/**")
public class IndexController {

    @Autowired
    private IntroService introService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public ModelAndView indexPage(@AuthenticationPrincipal() Object principal, ModelAndView model) {
        // pattern matching
        if (principal instanceof UserDetails userDetails) {
            model.addObject("name", userDetails.getUsername());
            model.addObject("role", userDetails.getAuthorities());
        } else if (principal instanceof DefaultOidcUser oidcUser) {
            model.addObject("name", oidcUser.getAttribute("given_name"));
            model.addObject("role", oidcUser.getAuthorities());
        } else if (principal instanceof DefaultOAuth2User oauth2User) {
            model.addObject("name", oauth2User.getAttribute("name"));
            model.addObject("role", oauth2User.getAuthorities());
        }

        model.setViewName("index");
        return model;
    }

    @GetMapping("/intro")
    public ModelAndView introPage(ModelAndView model) {

        Map<String, Set<String>> demoList = introService.getDemoList();

        demoList.forEach((key, value) -> System.out.println("Key: " + key + " Value: " + value.toString()));

        model.addObject("demoList", demoList);
        model.setViewName("intro");
        return model;
    }

    @GetMapping("/error")
    public ModelAndView errorPage(ModelAndView model) {
        model.setViewName("error");
        return model;
    }


    @GetMapping("/intro/secret")
    @ResponseBody
    public Map<String, String> secretPage() {
        // the method is security by ADMIN auth
        return adminService.getSecretMap();
    }


}
