package ru.amir.library.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.amir.library.models.Author;
import ru.amir.library.models.Book;
import ru.amir.library.services.AuthorsService;
import ru.amir.library.services.BooksService;
import ru.amir.library.services.GenresService;

import java.util.Optional;

@Controller
@RequestMapping("books")
public class BooksController {
    private final BooksService booksService;
    private final GenresService genresService;
    private final AuthorsService authorsService;

    public BooksController(BooksService booksService, GenresService genresService, AuthorsService authorsService) {
        this.booksService = booksService;
        this.genresService = genresService;
        this.authorsService = authorsService;
    }

    @GetMapping
    public String books(@RequestParam(value = "pattern", required = false) Optional<String> pattern,
                        Model model) {
        if (pattern.isPresent()) {
            model.addAttribute("books", booksService.findAllByTitleLike("%" + pattern.get() + "%"));
        } else {
            model.addAttribute("books", booksService.findAll());
        }
        return "book/books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {

        return "book/new";
    }

    @PostMapping
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable("id") Book book,
                       Model model) {
        model.addAttribute("book", book);
        model.addAttribute("genres", genresService.findAll());
        return "book/bookPage";
    }

    @GetMapping("/{id}/authors")
    public String authors(@PathVariable("id") Book book,
                          Model model) {
        model.addAttribute("bookAuthors", book.getAuthors());
        model.addAttribute("allAuthors", authorsService.findAllNotAssignedTo(book.getId()));
        model.addAttribute("book", book);
        return "book/authors";
    }

    @DeleteMapping("/{id}/authors")
    public String delAuthor(@ModelAttribute Author author,
                            @PathVariable("id") int id) {
        booksService.removeAuthor(id, author);
        return "redirect:/books/" + id + "/authors";
    }

    @PatchMapping("/{id}/authors")
    public String addAuthor(@ModelAttribute Author author,
                            @PathVariable("id") int id) {
        booksService.addAuthor(id, author);
        return "redirect:/books/" + id + "/authors";
    }
}
