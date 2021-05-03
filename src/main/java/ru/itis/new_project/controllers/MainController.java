package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.repositories.LobbyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class MainController {
    @Autowired
    LobbyRepository lobbyRepository;

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("lobbies", lobbyRepository.findAll());
        return "index";
    }

    @GetMapping("/lobbies/{id}")
    public String showLobbyPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Lobby> lobby = lobbyRepository.findById(id);
        if (lobby.isPresent()){
            model.addAttribute("lobby",lobby.get());
            return "lobby-page";
        }
        return "redirect:/lobbies";
    }


}