package pl.isa.biblioteka.user;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import pl.isa.biblioteka.book.Book;

import java.util.ArrayList;
import java.util.List;

@Component
@Entity
public class Person {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    public List<Book> personBooks = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "login", unique = true, nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;

    //OneToMeny
//    @Transient
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    public Person(String login, String password, String firstName, String secondName, String email) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
    }

    public Person() {
    }

    public Person(String firstName, String secondName, Integer id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
    }

    public Person(String firstName, String secondName, List<Book> personBooks, Integer id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.personBooks = personBooks;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getPersonBooks() {
        return personBooks;
    }


    public void setPersonBooks(List<Book> personBooks) {
        this.personBooks = personBooks;
    }

    public void addBookToPersonList(Book book) {
        personBooks.add(book);
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\'' + ", firstName='" + firstName + '\'' + ", secondName='" + secondName + '\'' + ", personBooks=" + personBooks + '}';
    }
}