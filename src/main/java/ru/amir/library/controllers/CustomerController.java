package ru.amir.library.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.amir.library.models.Book;
import ru.amir.library.models.Booking;
import ru.amir.library.models.Person;
import ru.amir.library.security.PersonDetails;
import ru.amir.library.services.BookingService;
import ru.amir.library.services.BooksService;
import ru.amir.library.services.PeopleService;
import ru.amir.library.utils.Role;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final PeopleService peopleService;
    private final BooksService booksService;
    private final BookingService bookingService;

    public CustomerController(PeopleService peopleService, BooksService booksService, BookingService bookingService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public String dirToHomePage() {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = peopleService.findByName(personDetails.getUsername()).get();
        return "redirect:/customer/" + person.getId();
    }

    @GetMapping("/{id}")
    public String homePage(@PathVariable("id") int id,
                           Model model) {
        model.addAttribute("person", peopleService.findById(id).get());
        model.addAttribute("books", peopleService.getAllBookingsById(id).stream().map(
                Booking::getBook
        ).collect(Collectors.toList()));
        model.addAttribute("allBooks", booksService.findAllNotAssignedToPerson(id));
        model.addAttribute("roles", Arrays.stream(Role.values()).map(Enum::name).toList());
        System.out.println(booksService.findAllNotAssignedToPerson(id));
        return "user/homePage";
    }

    @GetMapping("/{id}/delete")
    public String deleteAccount(@PathVariable("id") Person person,
                                Model model) {
        model.addAttribute("person", person);
//        if (!peopleService.getAllBookingsById(person.getId()).isEmpty()) {
//            model.addAttribute("delError", "Сначала необходимо закрыть все заявки пользователя");
//            return "user/deletePage";
//        }
        return "user/deletePage";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id,
                               Model model) {
        List<Booking> bookingList = peopleService.getAllBookingsById(id);
        System.out.println("bookings " + bookingList);
        if (!bookingList.isEmpty()) {
            model.addAttribute("person", peopleService.findById(id).get());
            model.addAttribute("delError", "Сначала необходимо закрыть все заявки пользователя");
            return "user/deletePage";
        }
        peopleService.delete(id);
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Person person,
                       Model model) {
        model.addAttribute("person", person);
        return "user/edit";
    }

    @PatchMapping("/{id}")
    public String edition(@PathVariable int id,
                          @ModelAttribute("person") @Valid Person person,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation failed");
            return "user/edit";
        }
        peopleService.update(id, person);
        return "redirect:/customer/" + id;
    }

    @PatchMapping("/{id}/changeRole")
    public String changeRole(@PathVariable int id,
                             @ModelAttribute Person person) {
        peopleService.changeRole(id, person);
        return "redirect:/customer/" + id;
    }

    @PostMapping("/{id}/assign")
    public String assign(@ModelAttribute("freeBook") Book book,
                         @PathVariable("id") Person person) {
        System.out.println("ASSIGN " + book.getId() + " " + person.getId());
        bookingService.assign(person, book);
        return "redirect:/customer/" + person.getId();
    }
}
