package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.new_project.repositories.LobbyRepository;

//TODO Довести до ума myLobbies
@Controller
public class MyLobbiesController {

    @Autowired
    LobbyRepository lobbyRepository;

    @GetMapping("/myLobbies")
    public String showMyLobbies(Model model){
        return "my-lobbies";
    }
}
