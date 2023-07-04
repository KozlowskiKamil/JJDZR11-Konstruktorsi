package pl.isa.biblioteka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.isa.biblioteka.dto.BookDAO;
import pl.isa.biblioteka.model.Book;
import pl.isa.biblioteka.model.User;
import pl.isa.biblioteka.repositories.BookRepository;
import pl.isa.biblioteka.servises.BookService;
import pl.isa.biblioteka.servises.PersonService;
import pl.isa.biblioteka.user.PersonDAO;

import java.util.List;

@SpringBootTest
class LibraryAppTests {

    @Autowired
    PasswordEncoder passwordEncoder;

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
        User user = new User(

                "KamilLogin",
                "KamilPass",
                "Kamil",
                "Koz",
                "k@k.pl");
        personDAO.savePerson(user);
    }

    @Test
    void readAndSavePerson() {
        List<User> people = PersonService.readUsers();
        for (User user : people) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_ADMIN");
            personDAO.savePerson(user);
        }
    }

    @Test
    void saveBook() {
        var book = new Book(
                "ISA-Junit",
                "TomaszD",
                "Nauka");
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