package ru.itis.new_project.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.new_project.repositories.PersonRepository;

@Service
public class PersonDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;


    //TODO Адаптировать к нормальной работе. Убрать выбрасывание исключения
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new PersonDetailsImpl(
                personRepository.findPersonByEmail(email)
                        .orElseThrow(IllegalArgumentException::new));
    }
}
