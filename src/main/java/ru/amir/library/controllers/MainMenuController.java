package ru.amir.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.amir.library.models.Genre;
import ru.amir.library.services.GenresService;

@Controller
@RequestMapping
public class MainMenuController {
    @GetMapping
    public String mainPage() {
        return "main/main";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
