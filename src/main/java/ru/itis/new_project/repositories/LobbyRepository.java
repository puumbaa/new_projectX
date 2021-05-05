package ru.itis.new_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.enums.Categories;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby,Long> {
    List<Lobby> findAllByCapacityBetweenAndEventDateBetweenAndEventCategory(Integer capacity, Integer capacity2,
                                                                            LocalDate eventDate, LocalDate eventDate2,
                                                                            Categories eventCategory
    );
    List<Lobby> findAllByCapacityBetweenAndEventDateBetween(Integer capacity, Integer capacity2,
                                                            LocalDate eventDate, LocalDate eventDate2);
}
