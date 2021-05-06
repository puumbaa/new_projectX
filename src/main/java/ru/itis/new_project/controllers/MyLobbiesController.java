package ru.itis.new_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyLobbiesController {

    @GetMapping("/myLobbies")
    public String showMyLobbies(){
        return "my-lobbies";
    }

}
