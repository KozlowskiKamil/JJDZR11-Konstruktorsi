package pl.isa.biblioteka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.isa.biblioteka.dto.BookDAO;
import pl.isa.biblioteka.dto.PersonDAO;
import pl.isa.biblioteka.model.Book;
import pl.isa.biblioteka.model.User;
import pl.isa.biblioteka.repositories.BookRepository;
import pl.isa.biblioteka.servises.BookService;
import pl.isa.biblioteka.servises.PersonService;
import pl.isa.biblioteka.servises.UserService;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Autowired
    UserService userService;


    @Test
    void savePerson() {
        User user = new User(

                "KamilLogin",
                "KamilPass",
                "Kamil",
                "Koz",
                "k@k.pl");
        personDAO.savePerson(user);
        assertTrue(userService.existsByUserName(user.getUsername()));
        personDAO.delate(user.getId());
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
    void contextLoads() {
    }
}
