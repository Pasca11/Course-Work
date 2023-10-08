package ru.amir.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
