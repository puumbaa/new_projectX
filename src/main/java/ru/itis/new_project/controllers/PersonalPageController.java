package ru.itis.new_project.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.repositories.PersonRepository;
import ru.itis.new_project.security.details.PersonDetailsImpl;
import ru.itis.new_project.transfer.PersonDto;

import java.util.Optional;

@Controller
public class PersonalPageController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("/my_page")
    public String goPersonalPage(Model model, Authentication authentication){
        if(authentication == null){
            return "redirect:/login";
        }
        PersonDetailsImpl principal = (PersonDetailsImpl) authentication.getPrincipal();
        model.addAttribute(PersonDto.from(principal.getPerson()));
        return "myPage";
    }
}
