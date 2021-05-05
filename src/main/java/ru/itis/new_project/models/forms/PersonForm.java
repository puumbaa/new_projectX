package ru.itis.new_project.models.forms;

import lombok.Data;

@Data
public class PersonForm {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String about;
}
