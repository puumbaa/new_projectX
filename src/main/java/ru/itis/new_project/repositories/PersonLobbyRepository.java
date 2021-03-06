package ru.itis.new_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.PersonLobby;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonLobbyRepository extends JpaRepository<PersonLobby, PersonLobby.PersonLobbyKey> {
    Optional<PersonLobby> findPersonLobbyByLobbyIdAndPersonId(Long lobbyId, Long personId);

}
