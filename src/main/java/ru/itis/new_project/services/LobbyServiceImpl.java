package ru.itis.new_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.models.enums.Categories;
import ru.itis.new_project.models.forms.LobbyForm;
import ru.itis.new_project.repositories.LobbyRepository;
import ru.itis.new_project.repositories.PersonLobbyRepository;
import ru.itis.new_project.repositories.PersonRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LobbyServiceImpl implements LobbyService{

    @Autowired
    private PersonLobbyRepository plRepo;
    @Autowired
    private LobbyRepository lobbyRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    public static LocalDate getDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateTimeFormatter);
    }

    @Override
    public void createLobby(LobbyForm lobbyForm, String email){
        Person person = personRepository.findPersonByEmail(email).get();

        Lobby lobby = Lobby.builder()
                .eventName(lobbyForm.getEventName())
                .brieflyInfo(lobbyForm.getBrieflyInfo())
                .eventDate(LocalDate.parse(lobbyForm.getDate()))
                .eventCategory(Categories.valueOf(lobbyForm.getCategory()))
                .countOfMembers(1)
                .capacity(lobbyForm.getCapacity())
                .aboutEvent(lobbyForm.getAboutEvent())
                .chatLink(lobbyForm.getChatLink())
                .personSet(new HashSet<>())
                .adminId(person.getId())
                .actual(true)
                .isFull(false)
                .build();

        lobby.getPersonSet().add(person);

        lobbyRepository.save(lobby);
    }

    @Override
    public void enterToLobby(Long lobbyId, Person person) {
        Lobby lobby = lobbyRepository.findById(lobbyId).get();
        person = personRepository.findById(person.getId()).get();

        lobby.getPersonSet().add(person);
        lobby.setCountOfMembers(lobby.getCountOfMembers() + 1);
        if(lobby.getCountOfMembers().equals(lobby.getCapacity())) lobby.setFull(true);

        lobbyRepository.save(lobby);
    }

    @Override
    public boolean isInLobby(Long lobbyId, Long personId) {
        return plRepo.findPersonLobbyByLobbyIdAndPersonId(lobbyId, personId).isPresent();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void checkLobbiesDate(){
        List<Lobby> allByActualTrue = lobbyRepository.findAllByActualTrue();
        for (Lobby lobby: allByActualTrue){
            if (lobby.getEventDate().compareTo(LocalDate.now())<0){
                lobbyRepository.updateActualTrueById(lobby.getId());
            }
        }
    }

    @Override
    public void updateLobbyInfo(LobbyForm lobbyForm, Long lobbyId) {

        Lobby lobby = lobbyRepository.findById(lobbyId).get();

        lobby.setEventName(lobbyForm.getEventName());
        lobby.setBrieflyInfo(lobby.getBrieflyInfo());
        lobby.setEventDate(LocalDate.parse(lobbyForm.getDate()));
        lobby.setCapacity(lobbyForm.getCapacity());
        lobby.setAboutEvent(lobbyForm.getAboutEvent());
        lobby.setChatLink(lobbyForm.getChatLink());

        lobbyRepository.save(lobby);
    }

    @Override
    public void deleteUser(Long lobbyId, Long personId) {
        Lobby lobby = lobbyRepository.findById(lobbyId).get();
        Person person = personRepository.findById(personId).get();

        if(lobby.getCountOfMembers().equals(lobby.getCapacity())){
            lobby.setFull(true);
        }
        lobby.setCountOfMembers(lobby.getCountOfMembers() - 1);

        lobby.getPersonSet().remove(person);
        lobbyRepository.save(lobby);
    }

    @Override
    public void deleteLobby(Long lobbyId) {
        Lobby lobby = lobbyRepository.findById(lobbyId).get();

        lobby.getPersonSet().clear();
        lobby.setActual(false);
        lobby.setCountOfMembers(0);

        lobbyRepository.save(lobby);
    }

    @Override
    public boolean isLobbyValid(String chatLink, Model model) {
        boolean res = true;

        if(!personService.isContactLinkValid(chatLink)){
            model.addAttribute("chatLinkErr", true);
            res = false;
        }

        return res;
    }

    @Override
    public boolean isUpdatableDataValid(Long lobbyId, String chatLink, Model model) {

        boolean res = true;
        if(!personService.isContactLinkValid(chatLink)){
            model.addAttribute("chatLinkErr", true);
            res = false;
        }

        return res;
    }

}
