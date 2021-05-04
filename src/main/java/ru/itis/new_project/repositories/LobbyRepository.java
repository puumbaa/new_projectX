package ru.itis.new_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.new_project.models.Lobby;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby,Long> {

}
