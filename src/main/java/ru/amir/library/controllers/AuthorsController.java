package ru.amir.library.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.amir.library.models.Author;
import ru.amir.library.services.AuthorsService;

@Controller
@RequestMapping("authors")
public class AuthorsController {
    private final AuthorsService authorsService;

    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("allAuthors", authorsService.findAll());
        return "authors/mainPage";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id,
                       Model model) {
        model.addAttribute("author", authorsService.findById(id));
        return "authors/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Author author,
                       Model model) {
        model.addAttribute("author", author);
        return "authors/edit";
    }

    @GetMapping("/new")
    public String newPage(@ModelAttribute Author author) {
        return "authors/new";
    }

    @PatchMapping("/{id}")
    public String edition(@PathVariable int id,
                          @ModelAttribute @Valid Author author,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/authors/edit";
        }
        authorsService.update(id, author);
        return "redirect:/authors";
    }

    @PostMapping
    public String save(@ModelAttribute Author author,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authors/new";
        }
        authorsService.save(author);
        return "redirect:/authors";
    }
}
