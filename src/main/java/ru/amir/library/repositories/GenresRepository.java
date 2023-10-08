package ru.amir.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.amir.library.models.Genre;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Integer> {
}
