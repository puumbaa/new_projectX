package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.repositories.LobbyRepository;
import ru.itis.new_project.repositories.PersonRepository;
import ru.itis.new_project.services.LobbyService;
import ru.itis.new_project.services.MainPageService;
import ru.itis.new_project.services.facades.IAuthenticationFacade;

import java.util.Optional;

@Controller
public class LobbyPageController {
    @Autowired
    private IAuthenticationFacade authFacade;
    @Autowired
    private MainPageService mainPageService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LobbyRepository lobbyRepository;
    @Autowired
    private LobbyService lobbyService;

    @GetMapping("/lobbies/{id}")
    public String showLobbyPage(@PathVariable(value = "id") Long id, Model model) {
        Authentication auth = authFacade.getAuthentication();
        if (!mainPageService.isAuthenticated(auth)) return "redirect:/login";

        Optional<Person> person = personRepository.findPersonByEmail(auth.getName());
        Optional<Lobby> lobby = lobbyRepository.findById(id);

        if (!(person.isPresent() && lobby.isPresent())) return "redirect:/lobbies";
        if(!lobbyService.isInLobby(id, person.get().getId())) return "redirect:/lobbies";


        model.addAttribute("lobby", lobby.get());
        return lobby.get().getAdminId().equals(person.get().getId()) ? "redirect:/lobbies/"+id+"/admin" : "lobby-page";
    }



    @PostMapping("/lobbies/{id}/leave")
    public String showLobbyPage(@PathVariable(value = "id") Long id){
        Authentication auth = authFacade.getAuthentication();

        Person person = personRepository.findPersonByEmail(auth.getName()).get();
        if(lobbyRepository.findById(id).get().getAdminId().equals(person.getId())) return "redirect:/lobbies/"+id;
        lobbyService.deleteUser(id, person.getId());

        return "redirect:/lobbies";
    }
}
