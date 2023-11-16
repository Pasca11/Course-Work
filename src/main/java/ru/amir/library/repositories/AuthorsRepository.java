package ru.amir.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.amir.library.models.Author;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {
    void deleteById(int id);
}
