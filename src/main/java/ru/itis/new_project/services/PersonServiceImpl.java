package ru.itis.new_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.repositories.PersonRepository;

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
        person.setPassword(passwordEncoder.encode(newPass));

        personRepository.save(person);
    }

    @Override
    public boolean isAuthenticated(Authentication auth) {
        return !auth.getName().equals("anonymousUser");
    }
}
