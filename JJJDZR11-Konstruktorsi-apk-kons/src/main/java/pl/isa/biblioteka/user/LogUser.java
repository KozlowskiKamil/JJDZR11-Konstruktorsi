package pl.isa.biblioteka.user;

import pl.isa.biblioteka.books.Book;
import pl.isa.biblioteka.menu.LibrarianMenu;
import pl.isa.biblioteka.menu.UserMenu;

import java.util.List;
import java.util.Scanner;

public class LogUser {
    private static Scanner scanner = new Scanner(System.in);
    public static Person logPerson;

    public static void setLogPerson(Person logPerson) {
        LogUser.logPerson = logPerson;
    }

    public List<Book> getPersonBooks() {
        return logPerson.personBooks;
    }

    public static void selectUser() {
        List<Person> persons = Users.getUsers();
        System.out.println("Podaj swój login");
        String login = scanner.nextLine().trim();
        System.out.println("Podaj swoje hasło");
        String password = scanner.nextLine().trim();
        boolean found = false;
        for (Person person1 : persons) {
            if (login.equalsIgnoreCase(persons.get(0).getLogin()) && password.equals(persons.get(0).getPassword())) {
                LibrarianMenu.librarianMenu();
                LogUser.setLogPerson(person1);
            } else if (person1.getLogin().equalsIgnoreCase(login) && person1.getPassword().equals(password)) {
                found = true;
                LogUser.setLogPerson(person1);
            }
        }
        if (found) {
            UserMenu.userMenu(login, password);
        } else {
            System.out.println("Brak użytkownika: " + login + " lub niepoprawne hasło");
        }
    }
}
