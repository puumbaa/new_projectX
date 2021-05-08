package ru.itis.new_project.services;


import ru.itis.new_project.models.forms.PersonForm;

public interface SignUpService {
    void signUp(PersonForm personForm);
    boolean validateEmail(String email);
}
