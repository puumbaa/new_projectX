package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.repositories.LobbyRepository;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {
    @Autowired
    LobbyRepository lobbyRepository;

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("lobbies", lobbyRepository.findAll());
        return "index";
    }
}