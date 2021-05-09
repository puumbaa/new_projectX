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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private PersonRepository personRepository;

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
    public boolean isEmailValid(String email) {
        return personRepository.findPersonByEmailIgnoreCase(email).isEmpty();
    }

    @Override
    public boolean isContactLinkValid(String contactLink) {
        try {
            URL url = new URL(contactLink);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            huc.setInstanceFollowRedirects(false);
            String respMessage = huc.getResponseMessage();

            return ((HttpStatus.OK.getReasonPhrase().equals(respMessage) ||
                    (HttpStatus.FOUND.getReasonPhrase().equals(respMessage)) &&
                            contactLink.contains("vk.com")));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isPersonValid(PersonForm personForm, Model model) {
        boolean res = true;
        if (!isEmailValid(personForm.getEmail())) {
            model.addAttribute("emailError", true);
            res = false;
        }
        if (!isContactLinkValid(personForm.getContacts())) {
            res = false;
            model.addAttribute("contactError", true);
        }
        return res;
    }
}
