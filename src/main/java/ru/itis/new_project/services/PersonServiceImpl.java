package ru.itis.new_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.repositories.PersonRepository;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonRepository personRepository;

    @Override
    public boolean isEqualPasswords(Long personId, String oldPass) {
        Person person = personRepository.findById(personId).get();
        return passwordEncoder.matches(oldPass, person.getPassword());
    }

    @Override
    public void updateInfo(Long personId, String newContactLink, String newPass) {
        Person person = personRepository.findById(personId).get();

        person.setContacts(newContactLink);
        if(!newPass.equals("")) {
            person.setPassword(passwordEncoder.encode(newPass));
        }
        personRepository.save(person);
    }

    @Override
    public boolean isAuthenticated(Authentication auth) {
        return !auth.getName().equals("anonymousUser");
    }

    @Override
    public boolean isEmailValid(String email) {
        return personRepository.findPersonByEmailIgnoreCase(email).isEmpty();
    }

    @Override
    public boolean isContactLinkValid(String contactLink) {
        try {
            URL url = new URL(contactLink.contains("https://") ? contactLink : "https://" + contactLink);

            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
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
    public boolean isUpdatableDataValid(Long personId, String oldPass, String contactLink, Model model) {
        boolean res = true;

        if (!isContactLinkValid(contactLink)) {
            model.addAttribute("contactErr", true);
            res = false;
        }

        if (!isEqualPasswords(personId, oldPass) && !oldPass.equals("")) {
            model.addAttribute("passErr", true);
            res = false;
        }

        return res;
    }
}
