package pl.isa.biblioteka.menu;

import pl.isa.biblioteka.books.BooksEdit;
import pl.isa.biblioteka.books.FolderBooks;
import pl.isa.biblioteka.user.Users;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LibrarianMenu {
    private static Scanner scanner = new Scanner(System.in);
    public static void librarianMenu() {
        Users users = new Users();
        BooksEdit booksEdit = new BooksEdit();
        boolean isContinue = true;
        while (isContinue) {
            System.out.println("Witaj Bibliotekarzu");
            System.out.println("Wybierz numer menu aby przystąpić do pracy");
            System.out.println("1 - Widok książek");
            System.out.println("2 - Dodaj użytkownika");
            System.out.println("3 - Usuń użytkownika");
            System.out.println("4 - Lista użytkowników");
            System.out.println("5 - Dodaj książkę");
            System.out.println("6 - Usuń książkę");
            System.out.println("7 - Lista dostępnych książek");
            System.out.println("8 - Lista wypożyczonych książek książek");
            System.out.println("9 - Zmień użytkownika");
            System.out.println("10 - Zakończ program");
            try {
                int userChoose = scanner.nextInt();
                switch (userChoose) {
                    case 1 -> booksEdit.showAllBooks();
                    case 2 -> users.addUser();
                    case 3 -> users.deleteUser();
                    case 4 -> users.listsUsers();
                    case 5 -> booksEdit.addBook();
                    case 6 -> booksEdit.deleteBookByTitle();
                    case 7 -> booksEdit.showAllAvailableBooks();
                    case 8 -> booksEdit.showAllBorrowedBooks();
                    case 9 -> {
                        scanner.skip("\n");
                        LogUser.selectUser();;
                    }
                    case 10 -> {
                        PersonService.saveUsers();
                        FolderBooks.saveBooks();
                        System.out.println("Bazy użytkowników i książek zostały zapisane.");
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