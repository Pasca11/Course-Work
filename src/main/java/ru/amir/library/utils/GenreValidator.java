package ru.amir.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.amir.library.models.Genre;
import ru.amir.library.services.GenresService;

import java.util.Optional;

@Component
public class GenreValidator implements Validator {
    private final GenresService genresService;

    public GenreValidator(GenresService genresService) {
        this.genresService = genresService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Genre.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Genre genre1 = (Genre)target;
        Optional<Genre> genre = genresService.findByName(genre1.getName());
        if (genre.isPresent()) {
            errors.rejectValue("name", "", "Жанр с таким названием уже существует");
        }
    }
}
