package ru.itis.new_project.services;

import ru.itis.new_project.models.Person;
import ru.itis.new_project.models.forms.LobbyForm;

import java.time.LocalDate;

public interface LobbyService {
    void createLobby(LobbyForm lobbyForm, Person person);
    void enterToLobby(Long lobbyId, Person person);
}
