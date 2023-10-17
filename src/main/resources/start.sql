insert into author(first_name, second_name, patronymic, birthday)  values ('Лев', 'Толстой', 'Николаевич', '09-27-1828'),
                                                                          ('Артур', 'Дойл', NULL, '05-22-1859'),
                                                                          ('Плутарх', NULL, NULL, NULL),
                                                                          ('Александр', 'Пушкин', 'Сергееевич', '06-06-1799'),
                                                                          ('Михаил', 'Лермонтов', 'Юрьевич', '10-15-1814');

insert into genre(name) VALUES ('повесть'),
                               ('рассказ'),
                               ('роман'),
                               ('проза'),
                               ('эпопея');
insert into book(title, release_date, genre_id, amount) VALUES ('Детство', '01-01-1852', 3, 12),
                                                        ('Война и мир', '01-01-1867', 5, 64),
                                                        ('Вечера на хуторе близ Диканьки','12-15-1961', 1, 1);
insert into author_book values (1, 1),
                               (1, 2),
                               (5, 3);
insert into status(name) values ('OPENED'),
                                ('ISSUED'),
                                ('CLOSED');
insert into booking(book_id, person_id, booking_date, status, issue_date) VALUES (1, 1, '01-02-2023', 2, null),
                                                                                 (2, 1, '6-23-2022', 3, '12-23-2022'),
                                                                                 (2, 2, '12-12-2021', 1, null),
                                                                                 (3, 2, '04-11-2021', 3, '07-11-2021');

