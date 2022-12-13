package com.example.cmsshoppingcart;


import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class Common {
    public void sharedData(Model model, HttpSession session, Principal principal){
        if(principal != null){
            model.addAttribute("principal", principal.getName());
        }
        System.out.println(principal);
    }
}
