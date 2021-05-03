package ru.itis.new_project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "lobby")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "lobbySet")
    private Set<Person> personSet = new HashSet<>();

    @Column(name = "event_name")
    private String eventName;
    @Column(name = "briefly_info")
    private String brieflyInfo;
    @Column(name = "date_time")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Timestamp dateTime;
    @Column(name = "count_of_members")
    private Integer countOfMembers;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "about_event")
    private String aboutEvent;
    @Column(name = "chat_link")
    private String chatLink;
    @Column(name = "is_actual")
    private boolean isActual;
    @Column(name = "is_full")
    private boolean isFull;

    public String getSimpleDate(){
        return this.dateTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getSimpleTime(){
        return this.dateTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
