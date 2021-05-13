package ru.itis.new_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.repositories.PersonRepository;
import ru.itis.new_project.services.PersonService;
import ru.itis.new_project.services.facades.IAuthenticationFacade;

@Controller
@RequestMapping("/profile")
public class PersonalPageController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    private IAuthenticationFacade authFacade;
    @Autowired
    private PersonService personService;


    @GetMapping
    public String goPersonalPage(Model model) {
        Authentication auth = authFacade.getAuthentication();
        if (!personService.isAuthenticated(auth)) return "redirect:/login";

        Person person = personRepository.findPersonByEmail(auth.getName()).get();
        model.addAttribute("person", person);
        return "profile";
    }

    @PostMapping("/edit")
    public String editProfile(Long personId, String newContactLink, String oldPass, String newPass, Model model) {
        model.addAttribute("contactErr", false);
        model.addAttribute("passErr", false);

        if(!personService.isUpdatableDataValid(personId, oldPass, newContactLink, model)){
            model.addAttribute("person",personRepository.findById(personId).get());
            return "profile";
        }

        personService.updateInfo(personId, newContactLink, newPass);

        return "redirect:/profile";
    }
}
