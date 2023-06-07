package pl.isa.biblioteka.user;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.isa.biblioteka.book.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Person {
    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private String secondName;

    public List<Book> personBooks = new ArrayList<>();

    public Person() {
    }

    public Person(String firstName, String secondName, Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Person(String firstName, String secondName, List<Book> personBooks, Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.personBooks = personBooks;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

/*    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
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
        return "Person{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", personBooks=" + personBooks +
                '}';
    }
}