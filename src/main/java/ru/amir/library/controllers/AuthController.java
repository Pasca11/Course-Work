package ru.amir.library.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.amir.library.models.Person;
import ru.amir.library.services.PeopleService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    //private final PersonValidator personValidator;
    private final PeopleService peopleService;

    public AuthController(PeopleService peopleService) {
        this.peopleService = peopleService;
//        this.personValidator = customersValidator;
    }

    @GetMapping("/registration")
    public String register(@ModelAttribute("person") Person person) {
        return "/auth/registration";
    }

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        //personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        peopleService.register(person);
        return "redirect:/auth/login";
    }
}
