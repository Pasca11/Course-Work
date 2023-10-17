package ru.amir.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // OPEN = 1, ISSUED = 2, CLOSED = 3
    @Column(name = "name", unique = true)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "status")
    private List<Booking> bookings;

    public Status() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
