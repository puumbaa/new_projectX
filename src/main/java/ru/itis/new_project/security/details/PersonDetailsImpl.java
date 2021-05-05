package ru.itis.new_project.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.new_project.models.Person;

import java.util.Collection;
import java.util.Collections;


// Приводит написанный нами класс Person к виду, удобному для Spring Security

public class PersonDetailsImpl implements UserDetails {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public PersonDetailsImpl(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRole = person.getRole().name();
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole);
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
