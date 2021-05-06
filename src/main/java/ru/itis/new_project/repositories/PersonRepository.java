package ru.itis.new_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.new_project.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    //TODO как-то решить 500 проблему
    @Query(nativeQuery = true, value = "Update person set about=? where id=?")
    @ResponseStatus(HttpStatus.OK)
    void updateAboutById(String updatedAbout, Long id);

    Optional<Person> findPersonByEmail(String email);
}
