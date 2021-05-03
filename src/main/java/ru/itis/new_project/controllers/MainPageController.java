package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.repositories.LobbyRepository;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class MainPageController {
    @Autowired
    LobbyRepository lobbyRepository;

    @GetMapping("/lobbies")
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

    @PostMapping("/lobbies/add")
    public String addLobby(@RequestParam String eventName, @RequestParam String brieflyInfo,
                           @RequestParam String dateTime,@RequestParam Integer capacity,
                           @RequestParam String aboutEvent, @RequestParam String chatLink){

        lobbyRepository.save(Lobby.builder()
                .eventName(eventName)
                .brieflyInfo(brieflyInfo)
                .dateTime(Timestamp.valueOf((dateTime+":00").replaceAll("T"," ")))
                .countOfMembers(1)
                .capacity(capacity)
                .aboutEvent(aboutEvent)
                .chatLink(chatLink)
                .isActual(true)
                .isFull(false)
                .build());
        return "redirect:/lobbies";
    }
}