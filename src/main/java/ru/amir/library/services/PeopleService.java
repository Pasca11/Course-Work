package ru.amir.library.services;

import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amir.library.models.Booking;
import ru.amir.library.models.Person;
import ru.amir.library.repositories.PeopleRepository;
import ru.amir.library.repositories.StatusRepository;
import ru.amir.library.utils.Role;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder, StatusRepository statusRepository) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Person> findById(int id) {
        return peopleRepository.findById(id);
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Role.ROLE_USER);
        peopleRepository.save(person);
    }

    @Transactional(readOnly = true)
    public List<Person> findByFullNameLike(String pattern) {
        return peopleRepository.findAllByNameLike(pattern);
    }

    @Transactional
    public void changeRole(int id, Person person) {
        Optional<Person> person1 = peopleRepository.findById(id);
        person1.get().setRole(person.getRole());
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookingsById(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBookings());
            person.get().getBookings()
                .forEach(b -> {
                    if (b.getIssueDate() != null) {
                        long diff = Math.abs(b.getIssueDate().getTime() - new Date().getTime());
                        if (diff > 864_000_000) {
                            b.setOverdue(true);
                        }
                    }
                });
            return person.get().getBookings().stream().filter(o -> o.getStatus().getId() != 3).toList();
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Person> findByName(String name) {
        return peopleRepository.findByUsername(name);
    }
}
