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

    @Transactional(readOnly = true)
    public Genre findById(int id) {
        return genresRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Genre findByName(String name) {
        return genresRepository.findByName(name).get();
    }

    public void save(Genre genre) {
        genresRepository.save(genre);
    }

    public void delete(int id) {
        genresRepository.deleteById(id);
    }

    public void edit(int id, Genre genre) {
        genre.setId(id);
        genresRepository.save(genre);
    }
}
