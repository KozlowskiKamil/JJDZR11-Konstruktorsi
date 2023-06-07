package pl.isa.biblioteka.menu;

import pl.isa.biblioteka.user.LogUser;
import pl.isa.biblioteka.user.Person;

import java.util.Scanner;

import static pl.isa.biblioteka.books.BorrowBooks.*;

public class BooksBorrowMenu {
    public static Scanner scanner = new Scanner(System.in);

    public void mainLoop() {
        System.out.println("Co chcesz zrobić");
        Option option = null;
        do {
            printMenu();
            try {
                option = chooseOption();
                executeOption(option);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (option != Option.EXIT);
    }

    private Option chooseOption() {
        int option = scanner.nextInt();
        scanner.nextLine();
        return Option.fromInt(option);
    }

    private void executeOption(Option option) {
        switch (option) {
            case BORROW_BOOK -> borrowBookMethod();
            case RETURN_BOOK -> returnMethod();
            case SHOW_AVAILABLE_BOOK -> booksEdit.showAllAvailableBooks();
            case SHOW_BORROWED_BOOK -> checkBorrowedBooks();
            case SHOW_SORTED_BOOK -> sortByCategory();
            case SHOW_SORTED_BOOK_BY_AUTHOR -> sortByAuthor();
            case FIND_BOOK_BY_TITLE -> booksEdit.findBookByTitle();
            case EXIT -> {}
        }
    }

    private static void checkBorrowedBooks() {
        if (!LogUser.logPerson.getPersonBooks().isEmpty()) {
            LogUser.logPerson.getPersonBooks().forEach(
                    (book) -> System.out.println("Tytuł: " + book.getTitle() + " Autor: " + book.getAuthor())
            );
        } else {
            System.out.println("Nie wypożyczono jeszcze żadnej książki");
        }
    }

    private static void returnMethod() {
        System.out.println("Podaj tytuł książki do zwrócenia: ");
        String bookTitleToReturn = scanner.nextLine();
        if (returnBook(bookTitleToReturn)) {
            System.out.println("Książka została zwrócona");
        } else {
            System.out.println("Użytkowniku nie posiadasz takiej kiążki");
        }
    }

    private static void borrowBookMethod() {
        System.out.println("Podaj tytuł książki do wypożyczenia: ");
        String bookTitle = scanner.nextLine();
        if ((addBookToPerson(bookTitle))) {
            System.out.println("Książka została wypożyczona");
        } else {
            System.out.println("Nie posiadamy książki o podanym tytule");
        }
    }

    private void printMenu() {
        System.out.println("Wybierz opcję:");
        for (Option option : Option.values()) {
            System.out.println(option.toString());
        }
    }

    private enum Option {
        BORROW_BOOK(1, "Wypożycz książkę"),
        RETURN_BOOK(2, "Oddaj książkę"),
        SHOW_AVAILABLE_BOOK(3, "Zobacz dostępne książki"),
        SHOW_BORROWED_BOOK(4, "Pokaż moje wypożyczone książki"),
        SHOW_SORTED_BOOK(5, "Wyszukaj dostępne książki po kategorii"),
        SHOW_SORTED_BOOK_BY_AUTHOR(6, "Wyszukaj dostepne ksiazki po autorze"),
        FIND_BOOK_BY_TITLE(7, "Wyszukaj dostepna ksiazke po tytule"),
        EXIT(8, "Wróć do głównego menu");

        private final int optionNumber;
        private final String description;

        Option(int optionNumber, String description) {
            this.optionNumber = optionNumber;
            this.description = description;
        }

        static Option fromInt(int option) {
            if (option < 0 || option > values().length) {
                throw new IllegalArgumentException("Opcja o takim numerze nie istnieje");
            }
            return values()[option - 1];
        }

        @Override
        public String toString() {
            return String.format("%d - %s", optionNumber, description);
        }

    }
}
