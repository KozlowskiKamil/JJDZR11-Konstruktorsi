package pl.isa.biblioteka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.isa.biblioteka.book.Book;
import pl.isa.biblioteka.book.BookDAO;
import pl.isa.biblioteka.book.BookRepository;
import pl.isa.biblioteka.book.BookService;
import pl.isa.biblioteka.user.Person;
import pl.isa.biblioteka.user.PersonDAO;
import pl.isa.biblioteka.user.PersonService;

import java.util.List;

@SpringBootTest
class LibraryAppTests {

	@Autowired
	PersonDAO personDAO;

	@Autowired
	BookDAO bookDAO;

	@Autowired
	PersonService personService;

	@Autowired
	BookService bookService;

	@Autowired
	BookRepository bookRepository;

	@Test
	void savePerson() {
		Person person = new Person(

				"KamilLogin",
				"KamilPass",
				"Kamil",
				"Koz",
				"k@k.pl");
		personDAO.savePerson(person);
	}


	@Test
	void readAndSavePerson() {
		List<Person> people = PersonService.readUsers();
		for (Person person : people) {
			personDAO.savePerson(person);
		}
	}

	@Test
	void saveBook() {
		Book book = new Book(
				"ISA-Junit",
				"TomaszD",
				"Nauka" );
		bookDAO.saveBook(book);
	}
	@Test
	void readAndSaveBook() {
		List<Book> books = BookRepository.readBooks();
		for (Book book : books) {
			bookDAO.saveBook(book);
		}
	}


	@Test
	void contextLoads() {
	}

}
