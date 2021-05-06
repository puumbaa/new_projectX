package ru.itis.new_project.models;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "person_lobby")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "person_lobby")
@IdClass(PersonLobby.PersonLobbyKey.class)
public class PersonLobby {

    @Id
    @Column(name = "person_id")
    private Long personId;

    @Id
    @Column(name = "lobby_id")
    private Long lobbyId;

    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class PersonLobbyKey implements Serializable {
        @Getter
        @Setter
        private Long personId;
        @Getter
        @Setter
        private Long lobbyId;
    }
}
