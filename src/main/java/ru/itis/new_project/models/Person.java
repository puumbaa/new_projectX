package ru.itis.new_project.models;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Data
@Entity(name = "person")
@Builder
@NoArgsConstructor
public class Person {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "about")
    private String about;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    public Integer getId() {
        return id;
    }
}
