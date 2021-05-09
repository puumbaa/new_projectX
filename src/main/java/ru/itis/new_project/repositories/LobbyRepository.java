package ru.itis.new_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.new_project.models.Lobby;
import ru.itis.new_project.models.enums.Categories;

import javax.persistence.Lob;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LobbyRepository extends JpaRepository<Lobby,Long> {
    List<Lobby> findAllByCapacityBetweenAndEventDateBetweenAndEventCategoryAndActualTrueAndIsFullFalse(Integer capacity, Integer capacity2,
                                                                                                       LocalDate eventDate, LocalDate eventDate2,
                                                                                                       Categories eventCategory
    );
    List<Lobby> findAllByCapacityBetweenAndEventDateBetweenAndActualTrueAndIsFullFalse(Integer capacity, Integer capacity2,
                                                                                       LocalDate eventDate, LocalDate eventDate2);

    List<Lobby> findAllByActualTrue();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE lobby SET is_actual = false WHERE id = ?")
    @ResponseStatus(HttpStatus.OK)
    void updateActualTrueById(Long id);

    List<Lobby> findAllByAdminIdAndActualTrue(Long adminId);

    @Query(nativeQuery = true, value = "SELECT * FROM lobby l INNER JOIN person_lobby pl " +
            "ON l.id = pl.lobby_id WHERE (pl.person_id = ? and l.is_actual = true)")
    List<Lobby> findAllByInLobby(Long personId);

    Optional<Lobby> findByEventNameIgnoreCase(String eventName);
}
