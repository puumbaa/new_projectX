package ru.itis.new_project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private Integer id;

    @ManyToMany(mappedBy = "lobbySet")
    private Set<Person> personSet = new HashSet<>();

    @Column(name = "event_name")
    private String eventName;
    @Column(name = "briefly_info")
    private String brieflyInfo;
    @Column(name = "date_time")
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

}
