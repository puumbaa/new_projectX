package ru.itis.new_project.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpServletRequest;


@Controller
public class LoginController{
    @GetMapping("/login")
    public String getLoginPage(Authentication authentication, HttpServletRequest request, Model model){
        if(authentication != null){
            return "redirect:/myLobbies";
        }
        if(request.getParameterMap().containsKey("error")){
            model.addAttribute("error", true);
        }
        return "login";
    }
 }
