package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.models.enums.Categories;
import ru.itis.new_project.models.forms.LobbyForm;
import ru.itis.new_project.repositories.LobbyRepository;
import ru.itis.new_project.repositories.PersonRepository;
import ru.itis.new_project.security.details.PersonDetailsImpl;
import ru.itis.new_project.services.LobbyService;
import ru.itis.new_project.services.LobbyServiceImpl;
import ru.itis.new_project.services.MainPageService;
import ru.itis.new_project.services.facades.IAuthenticationFacade;

import java.time.LocalDate;

import java.util.*;


@Controller
@RequestMapping("/lobbies")
public class MainPageController {

    @Autowired
    private MainPageService mainPageService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LobbyService lobbyService;
    @Autowired
    private IAuthenticationFacade authFacade;
    @Autowired
    LobbyRepository lobbyRepository;

    @GetMapping("/")
    public String getPages() {
        return "redirect:/lobbies";
    }

    @GetMapping("/sort")
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
            Model model) {
        Authentication auth = authFacade.getAuthentication();

        List<Categories> categories = Arrays.asList(sport, boardGame, development, leisure, party, social);
        // HARDCODE НО ЕГО ЛУЧШЕ НЕ УБИРАТЬ
        LocalDate beginDate = dateStart.equals("") ? LocalDate.parse("2021-01-01") : LobbyServiceImpl.getDate(dateStart);
        LocalDate endDate = dateEnd.equals("") ? LocalDate.parse("2030-01-01") : LobbyServiceImpl.getDate(dateEnd);

        List<Lobby> lobbyList = new ArrayList<>();

        for (Categories cat : categories) {
            if (cat != null) {
                lobbyList.addAll(lobbyRepository.findAllByCapacityBetweenAndEventDateBetweenAndEventCategoryAndActualTrueAndFullFalse(
                        capStart, capEnd, beginDate, endDate, cat
                ));
            }
        }

        if (lobbyList.isEmpty()) {
            lobbyList.addAll(lobbyRepository.findAllByCapacityBetweenAndEventDateBetweenAndActualTrueAndFullFalse(
                    capStart, capEnd, beginDate, endDate));
        }
        model.addAttribute("lobbies", lobbyList);
        System.out.println(auth.isAuthenticated());
        System.out.println(auth.getName());
        return mainPageService.isAuthenticated(auth) ? "index-auth" : "index";
    }


    @GetMapping
    public String greeting(Model model) {
        model.addAttribute("lobbies", lobbyRepository.findAllByActualTrue());
        Authentication auth = authFacade.getAuthentication();
        return mainPageService.isAuthenticated(auth) ? "index-auth" : "index";
    }

    //TODO Мб перенести в лобби сервис?
    @PostMapping("/enter/{id}")
    public String enterLobby(@PathVariable("id") Long id) {

        Authentication auth = authFacade.getAuthentication();
        if (!mainPageService.isAuthenticated(auth)) return "redirect:/login";

        Optional<Lobby> lobby = lobbyRepository.findById(id);
        Optional<Person> person = personRepository.findPersonByEmail(auth.getName());

        if (!(lobby.isPresent() && person.isPresent())) return "redirect:/lobbies";
        if (lobby.get().isFull()) return "redirect:/lobbies";

        // Если человек уже в лобби, то перекидывает на страницу лобби
        if(lobbyService.isInLobby(id, person.get().getId())) return ("redirect:/lobbies/"+id);

        lobbyService.enterToLobby(id, person.get());
        return "redirect:/lobbies";
    }


    @PostMapping("/add")
    public String addLobby(LobbyForm lobbyForm) {
        Authentication auth = authFacade.getAuthentication();
        Person person = ((PersonDetailsImpl) auth.getPrincipal()).getPerson();

        lobbyService.createLobby(lobbyForm, person);
        return "redirect:/lobbies";
    }
}
