package ru.amir.library.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amir.library.models.*;
import ru.amir.library.repositories.AuthorsRepository;
import ru.amir.library.repositories.BookingsRepository;
import ru.amir.library.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BooksService {
    private final BooksRepository booksRepository;
    private final BookingsRepository bookingsRepository;
    private final AuthorsRepository authorsRepository;
    private final GenresService genresService;


    public BooksService(BooksRepository booksRepository, BookingsRepository bookingsRepository, AuthorsRepository authorsRepository, GenresService genresService) {
        this.booksRepository = booksRepository;
        this.bookingsRepository = bookingsRepository;
        this.authorsRepository = authorsRepository;
        this.genresService = genresService;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Book> findById(int id) {
        return booksRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book book1 = booksRepository.findById(id).get();
        book1.setTitle(book.getTitle());
        book1.setAmount(book.getAmount());
        book1.setReleaseDate(book.getReleaseDate());
    }

    @Transactional(readOnly = true)
    public List<Person> getBookOwners(int id) {
        return booksRepository.findById(id).get().getBookings().stream().map(
                Booking::getPerson
        ).collect(Collectors.toList());
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> findAllNotAssignedToPerson(int id) {
        List<Booking> bookings = bookingsRepository.findAllAssignedToPerson(id);
        return booksRepository.findAll().stream().filter(
                o -> !bookings.stream().map(Booking::getBook).toList().contains(o)
                && o.getAmount() > 0
        ).toList();
    }

    @Transactional(readOnly = true)
    public List<Book> findAllByTitleLike(String pattern) {
        return booksRepository.findAllByTitleLike(pattern);
    }

    @Transactional
    public void removeAuthor(int id, Author author) {
        Author author1 = authorsRepository.findById(author.getId()).get();
        Book book = booksRepository.findById(id).get();
        author1.getBooks().remove(book);
        book.getAuthors().remove(author1);
    }

    @Transactional
    public void addAuthor(int id, Author author) {
        Author author1 = authorsRepository.findById(author.getId()).get();
        Book book = booksRepository.findById(id).get();
        author1.getBooks().add(book);
        book.getAuthors().add(author1);
    }

    @Transactional
    public void decreaseAmountById(int id) {
        booksRepository.findById(id).get().decreaseAmount();
    }

    @Transactional
    public void increaseAmountById(int id) {
        booksRepository.findById(id).get().increaseAmount();
    }

    @Transactional
    public void changeGenre(int id, Book book) {
        Book book1 = booksRepository.findById(id).get();
        book1.setGenre(book.getGenre());
    }
}

