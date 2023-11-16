package ru.amir.library.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amir.library.models.Author;
import ru.amir.library.models.Book;
import ru.amir.library.repositories.AuthorsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorsService {
    private final AuthorsRepository authorsRepository;
    private final BooksService booksService;

    public AuthorsService(AuthorsRepository authorsRepository, BooksService booksService) {
        this.authorsRepository = authorsRepository;
        this.booksService = booksService;
    }

    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Author> findAll(Sort sort) {
        return authorsRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Optional<Author> findById(int id) {
        return authorsRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Author> findAllNotAssignedTo(int id) {
        Book book = booksService.findById(id).get();
        return authorsRepository.findAll().stream().filter(o -> !book.getAuthors().contains(o)).toList();
    }

    @Transactional
    public void update(int id, Author author) {
        author.setId(id);
        authorsRepository.save(author);
    }

    @Transactional
    public void save(Author author) {
        authorsRepository.save(author);
    }

    @Transactional
    public void delete(Author author) {authorsRepository.deleteById(author.getId());}
}
