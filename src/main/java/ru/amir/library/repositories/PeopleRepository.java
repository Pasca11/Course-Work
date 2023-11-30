package ru.amir.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.amir.library.models.Person;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);

    @Query("select p from Person p where p.firstName like %:pattern% or p.secondName like %:pattern% or p.username like %:pattern%")
    List<Person> findAllByNameLike(@Param("pattern") String pattern);

}
