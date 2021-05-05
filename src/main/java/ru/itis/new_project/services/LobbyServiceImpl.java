package ru.itis.new_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.enums.Categories;
import ru.itis.new_project.models.forms.LobbyForm;
import ru.itis.new_project.repositories.LobbyRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class LobbyServiceImpl implements LobbyService{

    @Autowired
    private LobbyRepository lobbyRepository;

    public static LocalDate getDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateTimeFormatter);
    }

    @Override
    public void createLobby(LobbyForm lobbyForm) {

        Lobby lobby = Lobby.builder()
                .eventName(lobbyForm.getEventName())
                .brieflyInfo(lobbyForm.getBrieflyInfo())
                .eventDate(LocalDate.parse(lobbyForm.getDate()))
                .eventCategory(Categories.valueOf(lobbyForm.getCategory()))
                .countOfMembers(1)
                .capacity(lobbyForm.getCapacity())
                .aboutEvent(lobbyForm.getAboutEvent())
                .chatLink(lobbyForm.getChatLink())
                .actual(true)
                .isFull(false)
                .build();

        lobbyRepository.save(lobby);
    }
}
