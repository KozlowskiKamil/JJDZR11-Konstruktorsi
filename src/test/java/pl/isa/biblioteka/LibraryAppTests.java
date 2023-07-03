package pl.isa.biblioteka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.isa.biblioteka.book.Book;
import pl.isa.biblioteka.book.BookDAO;
import pl.isa.biblioteka.book.BookRepository;
import pl.isa.biblioteka.book.BookService;
import pl.isa.biblioteka.model.User;
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


/*
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
*/

	@Test
	void savePerson() {
		User user = new User(

				"KamilLogin",
				"KamilPass",
				"Kamil",
				"Koz",
				"k@k.pl");
		personDAO.savePerson(user);
	}


//	@Test   //bCryptPasswordEncoder
//	void readAndSavePerson() {
//		List<User> people = PersonService.readUsers();
//		for (User user : people) {
//			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//			personDAO.savePerson(user);
//		}
//	}

	@Test
	void readAndSavePerson() {
		List<User> people = PersonService.readUsers();
		for (User user : people) {
			personDAO.savePerson(user);
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
