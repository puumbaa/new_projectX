package ru.itis.new_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.models.enums.Role;
import ru.itis.new_project.models.forms.PersonForm;
import ru.itis.new_project.repositories.PersonRepository;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void signUp(PersonForm personForm) {
        String hashPass = passwordEncoder.encode(personForm.getPassword());
        personForm.setContacts(personForm.getContacts().replaceAll("https://", ""));
        Person person = Person.builder()
                .name(personForm.getName())
                .surname(personForm.getSurname())
                .password(hashPass)
                .contacts(personForm.getContacts())
                .email(personForm.getEmail().toLowerCase())
                .role(Role.USER)
                .build();

        personRepository.save(person);
    }



    @Override
    public boolean isPersonValid(PersonForm personForm, Model model) {
        boolean res = true;
        if (!personService.isEmailValid(personForm.getEmail())) {
            model.addAttribute("emailErr", true);
            res = false;
        }
        if (!personService.isContactLinkValid(personForm.getContacts())) {
            res = false;
            model.addAttribute("contactErr", true);
        }
        return res;
    }
}
