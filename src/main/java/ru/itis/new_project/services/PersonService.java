package ru.itis.new_project.services;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface PersonService {
    boolean isAuthenticated(Authentication auth);
    boolean isEqualPasswords(Long personId, String oldPass);

    boolean isEmailValid(String email);
    boolean isContactLinkValid(String contactLink);
    boolean isUpdatableDataValid(Long personId, String oldPass, String contactLink, Model model);

    void updateInfo(Long personId, String newContactLink, String newPass);
}
