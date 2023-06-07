package pl.isa.biblioteka.user;

import pl.isa.biblioteka.books.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Users {
    public static Person person;
    public static List<Person> users = new ArrayList<>(PersonService.readUsers());
    Scanner scanner = new Scanner(System.in);

    public void addUser() {
        List<Person> persons = Users.getUsers();
        Scanner input = new Scanner(System.in);
        System.out.println("Podaj imię nowego użytkownika: ");
        String firstName = input.nextLine().trim();
        System.out.println("Podaj nazwisko nowego użytkownika: ");
        String lastName = input.nextLine().trim();
        System.out.println("Podaj login nowego użytkownika: ");
        String login = input.nextLine().trim();
        System.out.println("Podaj hasło nowego użytkownika: ");
        String password = input.nextLine().trim();
        boolean found = false;
        for (Person person1 : persons) {
            if (person1.getLogin().equalsIgnoreCase(login)) {
                found = true;
            }
        }
        if (found) {
            System.out.println("Użytkownik: " + login + " istnieje w naszej bazie, wprowadź inny login\n");
        } else {
            int id = 1;
            if (!users.isEmpty()) {
                Person lastPerson = users.get(users.size() - 1);
                id = lastPerson.getId() + 1;
            }
            Person addPerson = new Person(firstName, lastName, id, login, password);
            users.add(addPerson);
            System.out.printf("Dodano nowego użytkownika: %d%n %s %s ", id, firstName, lastName);
        }
    }

    public boolean deleteUser() {
        System.out.println("Podaj ID użytkownika: ");
        Integer id = scanner.nextInt();
        System.out.println("Użytkownik o nr " + id + " został usunięty");
        return users.removeIf(findByID(id));
    }

    public static Predicate<Person> findByID(Integer id) {
        return users -> users.getId() == id;
    }

    public void listsUsers() {
        System.out.println("Uzytkownicy w naszej aplikacji: ");
        System.out.println("---------------------------------");
        int index = 1;
        for (Person user : users) {
            System.out.println("ID: " + user.getId() + ", Login: " + user.getLogin() + ", Imię: " + user.getFirstName() + ", Nazwisko: " + user.getSecondName());
            List<Book> personBooks = user.personBooks;
            if (!personBooks.isEmpty()) {
                System.out.println("    Wypożyczone książki");
                for (Book personBook : personBooks) {
                    System.out.println("        Tytuł: " + personBook.getTitle() + ", Autor: " + personBook.getAuthor());
                    index++;
                }
            } else {
                System.out.println("        - Brak wypożyczonych książek");
            }
            System.out.println("---------------------------------");
        }
    }

    public static List<Person> getUsers() {
        return users;
    }
}