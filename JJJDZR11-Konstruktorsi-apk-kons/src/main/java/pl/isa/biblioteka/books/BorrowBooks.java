package pl.isa.biblioteka.books;

import pl.isa.biblioteka.user.LogUser;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BorrowBooks {
    public static Scanner scanner = new Scanner(System.in);
    public static BooksEdit booksEdit = new BooksEdit();

//    public static LogUser logUser = new LogUser();

    public static boolean addBookToPerson(String bookTitle) {
        List<Book> booksList = BooksEdit.booksList;
        for (Book book : booksList) {
            if (book.getTitle().equalsIgnoreCase(bookTitle) && book.isState()) {
                book.setState(false);
                LogUser.logPerson.getPersonBooks().add(book);
                return true;
            }
        }
        return false;
    }

    public static boolean returnBook(String bookTitleToReturn) {
        List<Book> booksList = BooksEdit.booksList;
        for (Book personBook : LogUser.logPerson.getPersonBooks()) {
            if (personBook.getTitle().equalsIgnoreCase(bookTitleToReturn) && !personBook.isState()) {
                for (Book book : booksList) {
                    if (book.getTitle().equalsIgnoreCase(bookTitleToReturn)) {
                        book.setState(true);
                    }
                }
                LogUser.logPerson.getPersonBooks().removeIf(foundBookByTitle(bookTitleToReturn));
                return true;
            }
        }
        return false;
    }

    public static void sortByCategory() {
        List<Book> books = BooksEdit.booksList.stream().filter(Book::isState).toList();
        Set<String> availableCategory = showAvailableCategory(books);
        showFilterBookByCategory(books, availableCategory);

    }

    private static Set<String> showAvailableCategory(List<Book> books) {
        Set<String> availableCategory = books.stream().map(book1 -> book1.getCategory().toLowerCase()).collect(Collectors.toSet());
        for (String category : availableCategory) {
            System.out.print("'" + category + "'  ");
        }
        return availableCategory;
    }

    private static void showFilterBookByCategory(List<Book> books, Set<String> availableCategory) {
        System.out.println();
        System.out.println("Wybierz odpowiednia kategorię: ");
        String searchCategory = scanner.nextLine().toLowerCase();
        if (availableCategory.contains(searchCategory)) {
            List<Book> sortedBooks = books.stream().filter(book -> book.getCategory().equalsIgnoreCase(searchCategory)).toList();
            for (Book sortedBook : sortedBooks) {
                System.out.println("Książka: " + sortedBook.getTitle()
                        + " Autor: " + sortedBook.getAuthor()
                        + " Kategoria : " + sortedBook.getCategory());
            }
        } else {
            System.out.println("Brak kategorii " + searchCategory + ". Wybierz odpowiednią kategorię z listy");
        }
    }

    private static Predicate<Book> foundBookByTitle(String bookReturnTitle) {
        return book -> book.getTitle().equalsIgnoreCase(bookReturnTitle);
    }

    public static void sortByAuthor() {
        List<Book> books = BooksEdit.booksList.stream().filter(Book::isState).toList();
        Set<String> authorList = showAuthors(books);
        showFilterBookByAuthor(books, authorList);
    }

    private static Set<String> showAuthors(List<Book> books) {
        Set<String> authorList = books.stream().map(book2 -> book2.getAuthor().toLowerCase()).collect(Collectors.toSet());
        return authorList;
    }

    private static void showFilterBookByAuthor(List<Book> books, Set<String> authorList) {
        System.out.println("Podaj imie i nazwisko autora");
        String searchAuthor = scanner.nextLine().toLowerCase();
        if (authorList.contains(searchAuthor)) {
            List<Book> sortedBooks = books.stream().filter(book3 -> book3.getAuthor().equalsIgnoreCase(searchAuthor)).toList();
            for (Book sortedBook : sortedBooks) {
                System.out.println(sortedBook.getTitle());
            }
            } else {
                System.out.println("Brak ksiazek autora " + searchAuthor.toUpperCase());
            }
        }
    }

