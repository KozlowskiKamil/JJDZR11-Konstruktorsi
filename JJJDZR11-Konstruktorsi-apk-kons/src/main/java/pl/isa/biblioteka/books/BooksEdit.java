package pl.isa.biblioteka.books;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class BooksEdit {
    Scanner sc = new Scanner(System.in);
    public static List<Book> booksList = new ArrayList<>(FolderBooks.readBooks());

    public void addBook() {
        System.out.println("Podaj tytuł:");
        String title = sc.nextLine();
        System.out.println("Podaj autora:");
        String author = sc.nextLine();
        System.out.println("Podaj kategorię:");
        String category = sc.nextLine();
        Book book = new Book(title,author,category,true);
        booksList.add(book);
        System.out.println("Dodano książkę: " + book.getTitle() + " autora: "+ book.getAuthor());
    }

    public void deleteBookByTitle() {
        System.out.println("wpisz tytul ksiazki do usuniecia");
        String title = sc.nextLine();
        boolean findBook = false;
        for (Book book : booksList) {
            if(foundBookByTitle(title).test(book)){
                booksList.removeIf(foundBookByTitle(title));
                findBook = true;
                break;
            }
        }
        if(!findBook){
            System.out.println("W naszej bazie nie ma takiej książki o tytule: " + title);
        }
    }

    private static Predicate<Book> foundBookByTitle(String title) {
        return book -> book.getTitle().equalsIgnoreCase(title);
    }
    public void findBookByTitle() {
        System.out.println("Wpisz tytul szukanej ksiazki");
        String title = sc.nextLine().toLowerCase();
        boolean findBook = false;
        for (Book book : booksList) {
            if (foundBookByTitle(title).test(book)) {
                System.out.println("W bazie istnieje ksiazka " + book.getTitle() +  " " + "autora " + book.getAuthor());
                findBook=true;
                break;
            }
        }
        if (!findBook) {
            System.out.println("W naszej bazie nie ma ksiazki o tytule: " + title);
        }
    }


    public void showAllAvailableBooks(){
        booksList.stream().filter(Book::isState)
                .forEach(el -> System.out.printf("Tytuł: %s, Autor: %s, Kategoria: %s %n",
                        el.getTitle(),
                        el.getAuthor(),
                        el.getCategory()));
    }
    public void showAllBorrowedBooks(){
        booksList.stream().filter(book -> !book.isState())
                .forEach(el -> System.out.printf("Tytuł: %s, Autor: %s, Kategoria: %s  %n",
                        el.getTitle(),
                        el.getAuthor(),
                        el.getCategory()));
    }
    public void showAllBooks(){
        booksList.forEach(el -> System.out.printf("Tytuł: %s, Autor: %s, Kategoria: %s %n",
                el.getTitle(),
                el.getAuthor(),
                el.getCategory()));
    }

}
