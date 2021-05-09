package ru.itis.new_project.services;

import org.springframework.ui.Model;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.models.forms.LobbyForm;

import java.time.LocalDate;

public interface LobbyService {
    void createLobby(LobbyForm lobbyForm, String email);
    void enterToLobby(Long lobbyId, Person person);
    boolean isInLobby(Long lobbyId, Long personId);

    void checkLobbiesDate();

    boolean isLobbyValid(String chatLink, String name, Model model);
    void updateLobbyInfo(LobbyForm lobbyForm, Long lobbyId);

    void deleteUser(Long lobbyId, Long personId);
    void deleteLobby(Long lobbyId);
}

