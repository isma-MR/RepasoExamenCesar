INSERT INTO publisher (name, slug) VALUES ('Publisher 1', 'publisher-1');
INSERT INTO publisher (name, slug) VALUES ('Publisher 2', 'publisher-2');
INSERT INTO publisher (name, slug) VALUES ('Publisher 3', 'publisher-3');

INSERT INTO author (name, slug) VALUES ('Author 1', 'author-1');
INSERT INTO author (name, slug) VALUES ('Author 2', 'author-2');
INSERT INTO author (name, slug) VALUES ('Author 3', 'author-3');

INSERT INTO book (title_es, slug, publisher_id, price, isbn)
VALUES ('Book 1', 'book-1', 1, 10.00, '1234567890');

INSERT INTO book (title_es, slug, publisher_id, price, isbn)
VALUES ('Book 2', 'book-2', 2, 20.00, '0987654321');

INSERT INTO book (title_es, slug, publisher_id, price, isbn)
VALUES ('Book 3', 'book-3', 3, 30.00, '1122334455');

INSERT INTO book_author (book_id, author_id) VALUES (1, 1);
INSERT INTO book_author (book_id, author_id) VALUES (1, 2);
INSERT INTO book_author (book_id, author_id) VALUES (2, 2);
INSERT INTO book_author (book_id, author_id) VALUES (2, 3);
INSERT INTO book_author (book_id, author_id) VALUES (3, 3);
