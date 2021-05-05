package ru.itis.new_project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.new_project.models.enums.Categories;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "lobby")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lobby")
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
    @Column(name = "event_date")
    private LocalDate eventDate;
    @Column(name = "count_of_members")
    private Integer countOfMembers;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "about_event")
    private String aboutEvent;
    @Column(name = "event_category")
    @Enumerated(value = EnumType.STRING)
    private Categories eventCategory;
    @Column(name = "chat_link")
    private String chatLink;
    @Column(name = "is_actual")
    private boolean isActual;
    @Column(name = "is_full")
    private boolean isFull;

    public String getSimpleDate() {
        String[] split = this.eventDate.toString().split("-");
        return split[2] + "." + split[1] + "." + split[0];
    }
}