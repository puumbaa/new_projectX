package ru.itis.new_project.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data

@Table(name = "person_lobby")
public class PersonLobby {
    @Id
    private Integer id;
    @Column(name = "person_id")
    private Integer personId;
    @Column(name = "lobby_id")
    private Integer lobbyId;
    @Column(name = "is_admin")
    private boolean is_admin;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    public Integer getId() {
        return id;
    }
}
