package ru.amir.library.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.amir.library.models.Booking;
import ru.amir.library.models.Person;
import ru.amir.library.security.PersonDetails;
import ru.amir.library.services.BookingService;
import ru.amir.library.services.PeopleService;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {
    private final BookingService bookingService;
    private final PeopleService peopleService;

    public LibrarianController(BookingService bookingService, PeopleService peopleService) {
        this.bookingService = bookingService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String redirectToHomePage() {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = peopleService.findByName(personDetails.getUsername()).get();
        return "redirect:/librarian/" + person.getId();
    }

    @GetMapping("/{id}")
    public String homePage(@PathVariable int id,
            @ModelAttribute("someBooking") Booking booking,
            Model model) {
        model.addAttribute("person", peopleService.findById(id).get());
        model.addAttribute("bookingsToIssue", bookingService.getAllNotIssued());
        model.addAttribute("bookingsToClose", bookingService.getAllNotClosed());
        return "librarian/librarian";
    }

    @PostMapping("/{id}/issueBook")
    public String issueBook(@ModelAttribute("someBooking") Booking booking,
                            @PathVariable int id) {
        bookingService.issue(booking.getId());
        return "redirect:/librarian/" + id;
    }

    @PostMapping("/{id}/closeBooking")
    public String closeBooking(@ModelAttribute("someBooking") Booking booking,
                               @PathVariable int id) {
        bookingService.close(booking.getId());
        return "redirect:/librarian/" + id;
    }
}
