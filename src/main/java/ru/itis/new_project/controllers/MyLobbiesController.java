package ru.itis.new_project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.new_project.services.facades.IAuthenticationFacade;

@Controller
@RequestMapping("/myLobbies")
public class MyLobbiesController {
    @Autowired
    private IAuthenticationFacade authFacade;

    @GetMapping
    public String getLobbies(Model model){
        return "my-lobbies";
    }
}
