package ru.itis.new_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.models.enums.Role;
import ru.itis.new_project.models.forms.PersonForm;
import ru.itis.new_project.repositories.PersonRepository;

@Service
public class SignUpServiceImpl implements SignUpService{

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
    public boolean validateEmail(String email) {
        return personRepository.findPersonByEmail(email.toLowerCase()).isEmpty();
    }
}
