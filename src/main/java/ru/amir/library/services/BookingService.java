package ru.amir.library.services;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amir.library.models.Book;
import ru.amir.library.models.Booking;
import ru.amir.library.models.Person;
import ru.amir.library.repositories.BookingsRepository;
import ru.amir.library.repositories.StatusRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingsRepository bookingsRepository;
    private final StatusRepository statusRepository;
    private final BooksService booksService;

    public BookingService(BookingsRepository bookingsRepository, StatusRepository statusRepository, BooksService booksService) {
        this.bookingsRepository = bookingsRepository;
        this.statusRepository = statusRepository;
        this.booksService = booksService;
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllNotIssued() {
        return bookingsRepository.findAll().stream().filter(
                o -> o.getStatus().getId() == 1
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllNotClosed() {
        return bookingsRepository.findAll().stream().filter(
                o -> o.getStatus().getId() != 3
        ).collect(Collectors.toList());
    }

    @Transactional
    public void issue(int id) {
        Booking booking = bookingsRepository.findById(id).get();
        booking.setIssueDate(new Date());
        bookingsRepository.findById(id).get().setStatus(statusRepository.findById(2).get());
    }

    @Transactional
    public void close(int id) {
        Booking booking = bookingsRepository.findById(id).get();
        booking.setStatus(statusRepository.findById(3).get());

        booking.getBook().increaseAmount();

        bookingsRepository.save(booking);

    }

    @Transactional
    public void assign(Person person, Book book) {
        Booking booking = new Booking();
        booking.setBook(book);
        booking.setPerson(person);
        booking.setBookinDate(new Date());
        booking.setStatus(statusRepository.findById(1).get());

        booksService.decreaseAmountById(book.getId());

        bookingsRepository.save(booking);
    }

}
