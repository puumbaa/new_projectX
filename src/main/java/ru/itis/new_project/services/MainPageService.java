package ru.itis.new_project.services;

import org.springframework.security.core.Authentication;

public interface MainPageService {
    boolean isAuthenticated(Authentication auth);
}
