package ru.itis.new_project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.repositories.LobbyRepository;
import ru.itis.new_project.repositories.PersonRepository;
import ru.itis.new_project.services.facades.IAuthenticationFacade;

import java.util.List;

@Controller
@RequestMapping("/myLobbies")
public class MyLobbiesController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private IAuthenticationFacade authFacade;
    @Autowired
    private LobbyRepository lobbyRepository;

    @GetMapping
    public String getLobbies(Model model){
        Authentication auth = authFacade.getAuthentication();
        Person person = personRepository.findPersonByEmail(auth.getName()).get();

        model.addAttribute("lobbies", lobbyRepository.findAllByInLobby(person.getId()));
        model.addAttribute("lobbiesControl", lobbyRepository.findAllByAdminId(person.getId()));
        return "my-lobbies";
    }
}
