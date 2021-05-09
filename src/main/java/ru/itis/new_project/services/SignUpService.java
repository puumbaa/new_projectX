package ru.itis.new_project.services;


import org.springframework.ui.Model;
import ru.itis.new_project.models.forms.PersonForm;

public interface SignUpService {
    void signUp(PersonForm personForm);
    boolean isPersonValid(PersonForm personForm, Model model);
}
