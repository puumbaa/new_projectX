package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.enums.Categories;
import ru.itis.new_project.repositories.LobbyRepository;
import ru.itis.new_project.services.LobbyService;

import java.time.LocalDate;
import java.util.*;


@Controller
public class MainPageController {

    @Autowired
    LobbyRepository lobbyRepository;

    @GetMapping("/lobbies/sort")
    public String getSortedLobbies(
            @RequestParam(value = "date-start", required = false) String dateStart,
            @RequestParam(value = "date-end", required = false) String dateEnd,
            @RequestParam(value = "capacity-start", required = false, defaultValue = "0") Integer capStart,
            @RequestParam(value = "capacity-end", required = false, defaultValue = "60") Integer capEnd,
            @RequestParam(value = "sport", required = false) Categories sport,
            @RequestParam(value = "board-game", required = false) Categories boardGame,
            @RequestParam(value = "development", required = false) Categories development,
            @RequestParam(value = "leisure", required = false) Categories leisure,
            @RequestParam(value = "party", required = false) Categories party,
            @RequestParam(value = "social", required = false) Categories social,
            Model model, Authentication auth) {
        System.out.println(auth.getName());
        List<Categories> categories = Arrays.asList(sport, boardGame, development, leisure, party, social);
        // HARDCODE НО ЕГО ЛУЧШЕ НЕ УБИРАТЬ
        LocalDate beginDate = dateStart.equals("") ? LocalDate.parse("2021-01-01") : LobbyService.getDate(dateStart);
        LocalDate endDate = dateEnd.equals("") ? LocalDate.parse("2030-01-01") : LobbyService.getDate(dateEnd);

        List<Lobby> lobbyList = new ArrayList<>();

        for (Categories cat : categories) {
            if (cat != null) {
                lobbyList.addAll(lobbyRepository.findAllByCapacityBetweenAndEventDateBetweenAndEventCategoryAndActualTrue(
                        capStart, capEnd, beginDate, endDate, cat
                ));
            }
        }

        if(lobbyList.isEmpty()){
            lobbyList.addAll(lobbyRepository.findAllByCapacityBetweenAndEventDateBetweenAndActualTrue(
                    capStart, capEnd, beginDate, endDate));
        }
        model.addAttribute("lobbies", lobbyList);
        return "index";
    }


    @GetMapping("/lobbies")
    public String greeting(Model model) {
        model.addAttribute("lobbies", lobbyRepository.findAllByActualTrue());
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


    //TODO Мб сделать дефолтные значения для вместимости
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
                .actual(true)
                .isFull(false)
                .build());
        return "redirect:/lobbies";
    }
}