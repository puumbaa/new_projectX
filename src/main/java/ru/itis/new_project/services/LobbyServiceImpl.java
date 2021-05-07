package ru.itis.new_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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
import java.util.Set;

@Service
public class LobbyServiceImpl implements LobbyService {

    @Autowired
    private PersonLobbyRepository plRepo;
    @Autowired
    private LobbyRepository lobbyRepository;
    @Autowired
    private PersonRepository personRepository;

    public static LocalDate getDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateTimeFormatter);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void checkLobbiesDate() {
        List<Lobby> allByActualTrue = lobbyRepository.findAllByActualTrue();
        for (Lobby lobby : allByActualTrue) {
            if (lobby.getEventDate().compareTo(LocalDate.now()) < 0) {
                lobbyRepository.updateActualTrueById(lobby.getId());
            }
        }
    }


    @Override
    public void createLobby(LobbyForm lobbyForm, Person person) {

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

        person = personRepository.findPersonByEmail(person.getEmail()).get();
        lobby.getPersonSet().add(person);

        lobbyRepository.save(lobby);
    }

    @Override
    public void enterToLobby(Long lobbyId, Person person) {
        Lobby lobby = lobbyRepository.findById(lobbyId).get();
        person = personRepository.findById(person.getId()).get();

        lobby.getPersonSet().add(person);
        lobby.setCountOfMembers(lobby.getCountOfMembers() + 1);
        if (lobby.getCountOfMembers().equals(lobby.getCapacity())) lobby.setFull(true);

        lobbyRepository.save(lobby);
    }

    @Override
    public boolean isInLobby(Long lobbyId, Long personId) {
        return plRepo.findPersonLobbyByLobbyIdAndPersonId(lobbyId, personId).isPresent();
    }

}