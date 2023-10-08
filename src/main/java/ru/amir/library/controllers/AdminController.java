package ru.amir.library.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.amir.library.models.Person;
import ru.amir.library.security.PersonDetails;
import ru.amir.library.services.PeopleService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PeopleService peopleService;

    public AdminController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String homePage(@RequestParam(value = "pattern", required = false) Optional<String> pattern,
                           Model model) {
        model.addAttribute("hasPattern", pattern.isPresent());
        if (pattern.isPresent()) {
            List<Person> people = peopleService.findByFullNameLike(pattern.get());
            System.out.println(people);
            model.addAttribute("people", people);
        }
        return "admin/admin";
    }
}
