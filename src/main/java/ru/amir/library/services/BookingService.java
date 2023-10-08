package ru.amir.library.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amir.library.models.Book;
import ru.amir.library.models.Booking;
import ru.amir.library.models.Person;
import ru.amir.library.repositories.BookingsRepository;
import ru.amir.library.utils.Status;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingsRepository bookingsRepository;

    public BookingService(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllNotIssued() {
        return bookingsRepository.findAll().stream().filter(
                o -> o.getStatus() == Status.OPEN
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllNotClosed() {
        return bookingsRepository.findAll().stream().filter(
                o -> o.getStatus() != Status.CLOSED
        ).collect(Collectors.toList());
    }

    @Transactional
    public void issue(int id) {
        Booking booking = bookingsRepository.findById(id).get();
        booking.setIssueDate(new Date());
        bookingsRepository.findById(id).get().setStatus(Status.ISSUED);
    }

    @Transactional
    public void close(int id) {
        bookingsRepository.findById(id).get().setStatus(Status.CLOSED);
    }

    @Transactional
    public void assign(Person person, Book book) {
        Booking booking = new Booking();
        booking.setBook(book);
        booking.setPerson(person);
        booking.setBookinDate(new Date());
        booking.setStatus(Status.OPEN);

        book.decreaseAmount();

        bookingsRepository.save(booking);
    }

}
