package pl.isa.biblioteka.menu;

import pl.isa.biblioteka.books.Book;
import pl.isa.biblioteka.books.BooksEdit;
import pl.isa.biblioteka.books.FolderBooks;
import pl.isa.biblioteka.user.LogUser;
import pl.isa.biblioteka.user.Person;
import pl.isa.biblioteka.user.PersonService;
import pl.isa.biblioteka.user.Users;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void userMenu(String login, String password) {
        List<Person> users = Users.getUsers();
        BooksEdit booksEdit = new BooksEdit();
        BooksBorrowMenu booksBorrowMenu = new BooksBorrowMenu();
        boolean findUser = false;
        for (Person user : users) {
            if (login.equalsIgnoreCase(user.getLogin()) && password.equalsIgnoreCase(user.getPassword())) {
                System.out.println("Witamy Cię : " + user.getFirstName().toUpperCase() + " " + user.getSecondName().toUpperCase()
                        + "\nTwój login to " + user.getLogin() + " Twoje ID: " + user.getId() + " użytkownikiem");
                List<Book> personBooks = user.getPersonBooks();
                boolean isContinue = true;
                while (isContinue) {
                    System.out.println("\nWybierz numer menu");
                    System.out.println("1 - Widok książek");
                    System.out.println("2 - Operacje na książkach");
                    System.out.println("3 - Zmień użytkownika");
                    System.out.println("4 - Zakończ program");
                    try {
                        int userChoose = scanner.nextInt();
                        switch (userChoose) {
                            case 1 -> booksEdit.showAllBooks();
                            case 2 -> booksBorrowMenu.mainLoop();
                            case 3 -> {
                                scanner.skip("\n");
                                LogUser.selectUser();
                            }
                            case 4 -> {
                                PersonService.saveUsers();
                                FolderBooks.saveBooks();
                                System.out.println("Bazay użytkowników i książek zostały zapisane.");
                                isContinue = false;
                                System.exit(0);
                            }
                            default -> System.out.println("Został wprowadzony niewłaściwy numer.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Został wprowadzony niewłaściwy znak.");
                        scanner.next();
                    }
                }
            }
        }
        if (!findUser) {
            System.out.println("Brak użytkownika: " + login + " w naszej bazie");
        }
    }
}