package pl.isa.biblioteka.menu;

import pl.isa.biblioteka.books.FolderBooks;
import pl.isa.biblioteka.user.Users;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);

    public void start() {
        Users users = new Users();
        boolean isContinue = true;
        while (isContinue) {
            System.out.println("Witamy w naszej bibliotece wybierz numer menu");
            System.out.println("1 - Zaloguj się");
            System.out.println("2 - Załóż kartę biblioteczną");
            System.out.println("3 - Zakończ program");
            try {
                int userChoose = scanner.nextInt();
                switch (userChoose) {
                    case 1 -> {
                        scanner.skip("\n");
                        LogUser.selectUser();
                    }
                    case 2 -> users.addUser();
                    case 3 -> {
                        PersonService.saveUsers();
                        FolderBooks.saveBooks();
                        System.out.println("Bazy użytkowników i książek zapisane poprawnie");
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