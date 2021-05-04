package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.new_project.models.forms.PersonForm;
import ru.itis.new_project.services.SignUpService;

@Controller
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    @GetMapping("/sign_up")
    public String getSignUp(){
        return "signUp";
    }

    //TODO Сделать валидацию данных при регистрации
    @PostMapping("/sign_up")
    public String signUp(PersonForm personForm){
        signUpService.signUp(personForm);
        return "redirect:/login";
    }
}
