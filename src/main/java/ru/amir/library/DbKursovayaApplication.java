package ru.amir.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.amir.library.models.Book;
import ru.amir.library.models.Genre;
import ru.amir.library.services.BooksService;

import java.time.LocalDateTime;

@SpringBootApplication
public class DbKursovayaApplication {
	public static void main(String[] args) {
		SpringApplication.run(DbKursovayaApplication.class, args);
	}

}
