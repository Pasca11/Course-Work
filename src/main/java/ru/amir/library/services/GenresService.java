package ru.amir.library.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amir.library.models.Genre;
import ru.amir.library.repositories.GenresRepository;

import java.util.List;

@Service
public class GenresService {
    private final GenresRepository genresRepository;

    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genresRepository.findAll();
    }
}
