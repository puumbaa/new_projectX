package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.enums.Categories;
import ru.itis.new_project.repositories.LobbyRepository;

import java.time.LocalDate;
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
        if (lobby.isPresent()) {
            model.addAttribute("lobby", lobby.get());
            return "lobby-page";
        }
        return "redirect:/lobbies";
    }

    @PostMapping("/lobbies/add")
    public String addLobby(@RequestParam String eventName, @RequestParam String brieflyInfo,
                           @RequestParam String date, @RequestParam String category,
                           @RequestParam Integer capacity, @RequestParam String aboutEvent,
                           @RequestParam String chatLink) {

            lobbyRepository.save(Lobby.builder()
                    .eventName(eventName)
                    .brieflyInfo(brieflyInfo)
                    .eventDate(LocalDate.parse(date))
                    .eventCategory(Categories.valueOf(category))
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