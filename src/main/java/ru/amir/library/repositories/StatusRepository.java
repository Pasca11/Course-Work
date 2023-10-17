package ru.amir.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.amir.library.models.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
