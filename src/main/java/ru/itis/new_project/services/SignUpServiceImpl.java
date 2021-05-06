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

        Person person = Person.builder()
                .name(personForm.getName())
                .surname(personForm.getSurname())
                .password(hashPass)
                .email(personForm.getEmail())
                .about(personForm.getAbout())
                .role(Role.USER)
                .build();

        personRepository.save(person);
    }

    @Override
    public boolean validateSuccess(PersonForm personForm) {
        if(personForm.getName().length()<2 || personForm.getName().length()>60){
            return false;
        }
        if(!personForm.getEmail().matches("[a-zA-Z0-9]*@.[a-zA-Z]*[.].[a-zA-Z]*")){
           return false;
        }
        if(personForm.getPassword().length()<8) return false;
        return true;
    }
}
