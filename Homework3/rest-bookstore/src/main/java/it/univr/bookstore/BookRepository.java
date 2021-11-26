package it.univr.bookstore;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
	Optional<Book> findByTitle(String title);

	Optional<List<Book>> findByAuthor(String author);
}
