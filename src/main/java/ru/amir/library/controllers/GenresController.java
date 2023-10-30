package ru.amir.library.controllers;

import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.amir.library.models.Genre;
import ru.amir.library.services.GenresService;

@Controller
@RequestMapping("genres")
public class GenresController {
    private final GenresService genresService;

    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("genres", genresService.findAll());
        return "genre/mainPage";
    }

    @GetMapping("/{id}")
    public String getGenre(@PathVariable("id") int id,
                           Model model) {
        model.addAttribute("genre", genresService.findById(id));
        model.addAttribute("books", genresService.findById(id).getBooks());
        return "genre/show";
    }

    @PatchMapping("{id}")
    public String editGenre(@Valid @ModelAttribute("genre") Genre genre,
                            BindingResult bindingResult,
                            @PathVariable("id") int id,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("books", genresService.findById(id).getBooks());
            return "genre/show";
        }
        genresService.edit(id, genre);
        return "redirect:/genres/" + id;
    }


    @GetMapping("/new")
    public String newGenre(@ModelAttribute("genre") Genre genre) {
        return "genre/new";
    }

    @PostMapping
    public String genre(@ModelAttribute Genre genre) {
        genresService.save(genre);
        return "redirect:/genres";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        genresService.delete(id);
        return "redirect:/genres";
    }
}
