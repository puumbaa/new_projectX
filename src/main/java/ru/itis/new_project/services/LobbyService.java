package ru.itis.new_project.services;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.models.forms.LobbyForm;


public interface LobbyService {
    void createLobby(LobbyForm lobbyForm, Person person);
    void enterToLobby(Long lobbyId, Person person);
    void checkLobbyDate();
}
