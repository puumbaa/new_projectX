package ru.itis.new_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.new_project.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findPersonByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM person p INNER JOIN person_lobby pl on p.id = pl.person_id WHERE " +
            "(pl.lobby_id = ? and pl.person_id != ?)")
    List<Person> findAllInLobby(Long lobbyId, Long personId);
}
