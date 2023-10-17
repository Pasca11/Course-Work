package ru.amir.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.amir.library.models.Booking;

import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT b from Booking b where b.person.id = :person_id and b.status.id <> 3")
    List<Booking> findAllAssignedToPerson(@Param("person_id") Integer id);
}
