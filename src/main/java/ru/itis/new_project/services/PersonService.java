package ru.itis.new_project.services;

import org.springframework.security.core.Authentication;

public interface PersonService {
    boolean isAuthenticated(Authentication auth);
    boolean isEqualPasswords(Long personId, String oldPass);

    void updateInfo(Long personId, String newContactLink, String newPass);
}
