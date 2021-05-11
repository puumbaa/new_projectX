package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.models.forms.LobbyForm;
import ru.itis.new_project.repositories.LobbyRepository;
import ru.itis.new_project.repositories.PersonRepository;
import ru.itis.new_project.services.LobbyService;
import ru.itis.new_project.services.facades.IAuthenticationFacade;

@Controller
@RequestMapping("/lobbies/{id}/admin")
public class AdminLobbyPage {

    @Autowired
    private IAuthenticationFacade authFacade;
    @Autowired
    private LobbyRepository lobbyRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LobbyService lobbyService;

    @GetMapping
    public String getLobbyPage(@PathVariable("id") Long lobbyId, Model model){
        Authentication auth = authFacade.getAuthentication();

        Lobby lobby = lobbyRepository.findById(lobbyId).get();
        Person person = personRepository.findPersonByEmail(auth.getName()).get();

        if(!lobby.getAdminId().equals(person.getId())) return "redirect:/lobbies/"+lobbyId;

        model.addAttribute("lobby", lobby);
        model.addAttribute("participants" ,personRepository.findAllInLobby(lobbyId, person.getId()));
        return "lobby-page-admin";
    }


    //TODO Валидация по работоспособности чат линка и ивент нейму

    @PostMapping("/editinfo")
    public String editLobbyInfo(@PathVariable("id") Long lobbyId, LobbyForm lobbyForm, Model model){
        lobbyForm.setChatLink(lobbyForm.getChatLink().replace("http://",""));

        if(!lobbyService.isUpdatableDataValid(lobbyId, lobbyForm.getEventName(), lobbyForm.getChatLink(), model)){
           return getLobbyPage(lobbyId, model);
        }

        lobbyService.updateLobbyInfo(lobbyForm, lobbyId);

        return "redirect:/lobbies/"+lobbyId;
    }

    @PostMapping("/deleteuser")
    public String deleteUserFromLobby(@PathVariable("id") Long lobbyId, Long personId){

        lobbyService.deleteUser(lobbyId, personId);

        return "redirect:/lobbies/"+lobbyId;
    }

    @PostMapping("/deletelobby")
    public String deleteLobby(@PathVariable("id") Long lobbyId){

        lobbyService.deleteLobby(lobbyId);

        return "redirect:/lobbies";
    }
}
