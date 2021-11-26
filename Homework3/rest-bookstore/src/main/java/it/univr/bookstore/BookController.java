package it.univr.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/books")
	public Iterable<Book> getBooks() {
		return bookRepository.findAll();
	}

	@PostMapping("/book")
	public Book createBook(@RequestParam("title") String title, @RequestParam("author") String author,
						   @RequestParam("price") Float price) {
		Book book = new Book(title, author, price);
		bookRepository.save(book);
		return book;
	}

	@GetMapping("/book/id/{bookId}")
	public Optional<Book> readBookById(@PathVariable("bookId") Long id) {
		return bookRepository.findById(id);
	}

	@GetMapping("/book/title/{bookTitle}")
	public Optional<Book> readBookByTitle(@PathVariable("bookTitle") String title) {
		return bookRepository.findByTitle(title);
	}

	@GetMapping("/book/author/{bookAuthor}")
	public Optional<List<Book>> readBookByAuthor(@PathVariable("bookAuthor") String author) {
		return bookRepository.findByAuthor(author);
	}

	@PutMapping("/book")
	public Book updateBook(@RequestBody Book book) {
		bookRepository.save(book);
		return book;
	}

	@DeleteMapping("/book")
	public void deleteBook(@RequestParam("id") Long id) {
		bookRepository.deleteById(id);
	}
}
