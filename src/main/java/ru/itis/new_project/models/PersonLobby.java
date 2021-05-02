package ru.itis.new_project.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "person_lobby")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(PersonLobby.PersonLobbyKey.class)
public class PersonLobby {

    @Id
    @Column(name = "person_id")
    private Integer personId;

    @Id
    @Column(name = "lobby_id")
    private Integer lobbyId;

    @Column(name = "is_admin")
    private boolean is_admin;

    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class PersonLobbyKey implements Serializable {
        @Getter
        @Setter
        private Integer personId;
        @Getter
        @Setter
        private Integer lobbyId;
    }
}
