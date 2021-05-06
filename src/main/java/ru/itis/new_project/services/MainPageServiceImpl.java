package ru.itis.new_project.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class MainPageServiceImpl implements MainPageService{
    @Override
    public boolean isAuthenticated(Authentication auth) {
        return !auth.getName().equals("anonymousUser");
    }
}
