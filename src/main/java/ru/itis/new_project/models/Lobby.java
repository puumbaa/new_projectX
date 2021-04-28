package ru.itis.new_project.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Calendar;

@Data
@Entity(name = "lobby")
public class Lobby {
    @Id
    private Integer id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    public Integer getId() {
        return id;
    }
}
