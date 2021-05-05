package ru.itis.new_project.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.new_project.models.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "person")
@Table(name = "person")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Lobby.class)
    @JoinTable(
            name = "person_lobby",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "lobby_id")
    )
    private Set<Lobby> lobbySet = new HashSet<>();


    @Column(name = "name")
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    @Column(name = "surname")
    @NotBlank
    @Size(min = 2, max = 50)
    private String surname;
    @Column(name = "about")
    @NotBlank
    private String about;
    @Column(name = "email")
    @Email
    @NotBlank
    private String email;
    @Column(name = "password")
    @NotBlank
    @Size(min = 8, max = 60, message = "Минимальная длина 8 символов, максимальная 60!")
    private String password;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "contacts")
    private String contacts;

}
