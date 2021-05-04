package ru.itis.new_project.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.new_project.models.Person;
import ru.itis.new_project.repositories.PersonRepository;

import java.util.Optional;

@Controller
@RequestMapping("/my_page")
public class PersonalPageController {

    @Autowired
    PersonRepository personRepository;
    //TODO Сделать так, чтобы личный кабинет показывался только владельцу аккаунта
    //TODO Сделать смену личной информации(about и contacts)

    // Возвращает личный кабинет человека по его id
    @GetMapping("/{id}")
    public String getPage(@PathVariable("id") Long id, Model model){
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()) {
            model.addAttribute("person", person.get());
            return "personalPage";
        }else{
            return "redirect:/";
        }
    }

    @PostMapping("/{id}")
    public String changeAbout(@PathVariable("id") Long id, @RequestParam(value = "text", required = true) String text){
        Person person = personRepository.findById(id).get();
        if(!text.equals(person.getAbout())) {
            personRepository.updateAboutById(text, id);
        }
        return "redirect:/my_page/"+id;
    }
}
