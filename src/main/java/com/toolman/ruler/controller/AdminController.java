package com.toolman.ruler.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping()
    public ModelAndView defaultPage(@AuthenticationPrincipal() Object principal, ModelAndView model) {
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
	model.setViewName("admin");
	return model;
    }

}
