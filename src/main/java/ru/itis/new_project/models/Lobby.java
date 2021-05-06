package ru.itis.new_project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;
import ru.itis.new_project.models.enums.Categories;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "lobby")
@Table(name = "lobby")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_lobby",
            joinColumns = @JoinColumn(name = "lobby_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> personSet;

    @Column(name = "event_name")
    private String eventName;
    @Column(name = "briefly_info")
    private String brieflyInfo;
    @Column(name = "event_category")
    @Enumerated(value = EnumType.STRING)
    public Categories eventCategory;
    @Column(name = "event_date")
    private LocalDate eventDate;
    @Column(name = "count_of_members")
    private Integer countOfMembers;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "about_event")
    private String aboutEvent;
    @Column(name = "chat_link")
    private String chatLink;
    @Column(name = "admin_id")
    private Long adminId;
    @Column(name = "is_actual")
    private boolean actual;
    @Column(name = "is_full")
    private boolean isFull;

    @Override
    public String toString(){
        return "Lobby: " + id;
    }



    public String getSimpleDate(){
        String[] split = this.eventDate.toString().split("-");
        return split[2]+"."+split[1]+"."+split[0];
    }

}
