package pl.isa.biblioteka.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.isa.biblioteka.file.FolderBooks;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BookService {
    public static Scanner sc = new Scanner(System.in);
    public static List<Book> booksList = new ArrayList<>(FolderBooks.readBooks());

    public void addBook() {
        System.out.println("Podaj tytuł:");
        String title = sc.nextLine();
        System.out.println("Podaj autora:");
        String author = sc.nextLine();
        System.out.println("Podaj kategorię:");
        String category = sc.nextLine();
        Book book = new Book(title, author, category, true);
        booksList.add(book);
        System.out.println("Dodano książkę: " + book.getTitle() + " autora: " + book.getAuthor());
    }

    public void deleteBookByTitle() {
        System.out.println("wpisz tytul ksiazki do usuniecia");
        String title = sc.nextLine();
        boolean findBook = false;
        for (Book book : booksList) {
            if (foundBookByTitle(title).test(book)) {
                booksList.removeIf(foundBookByTitle(title));
                findBook = true;
                break;
            }
        }
        if (!findBook) {
            System.out.println("W naszej bazie nie ma takiej książki o tytule: " + title);
        }
    }

    public List<Book> searchByText(String text) {
        List<Book> searchText = booksList.stream().filter(book -> book.getTitle().contains(text) || book.getAuthor().contains(text)).collect(Collectors.toList());

        return searchText;
    }

    private static Predicate<Book> foundBookByTitle(String title) {
        return book -> book.getTitle().equalsIgnoreCase(title);
    }

    public List<Book> showAllAvailableBooks() {
        return booksList.stream().filter(Book::isState).toList();
    }

    public void showAllBorrowedBooks() {
        booksList.stream().filter(book -> !book.isState()).forEach(el -> System.out.printf("Tytuł: %s, Autor: %s, Kategoria: %s  %n", el.getTitle(), el.getAuthor(), el.getCategory()));
    }

    public List<Book> showAllBooks() {
        return booksList;
    }

    public List<Book> findBookByTitle(String title) {
        List<Book> books = booksList.stream().filter(Book::isState).toList();
        String searchAuthor = title.toLowerCase().replaceAll("\\s", "");

        List<Book> searchedBooks;
        if (!title.equals("")) {
            searchedBooks = books.stream().filter(book -> book.getTitle().toLowerCase().replaceAll("\\s", "").contains(searchAuthor)).collect(Collectors.toList());
        } else {
            searchedBooks = Collections.emptyList();
        }
        return searchedBooks;

    }

    public List<Book> filterByAuthor(String author) {
        List<Book> books = booksList.stream().filter(Book::isState).toList();
        String searchAuthor = author.toLowerCase().replaceAll("\\s", "");
        List<Book> searchedBooks;
        if (!author.equals("")) {
            searchedBooks = books.stream().filter(book -> book.getAuthor().toLowerCase().replaceAll("\\s", "").contains(searchAuthor)).collect(Collectors.toList());
        } else {
            searchedBooks = Collections.emptyList();
        }
        return searchedBooks;
    }

    public List<Book> sortByCategory(String category) {
        List<Book> books = booksList.stream().filter(Book::isState).toList();
        Set<String> availableCategory = availableCategory();
        List<Book> sortedBook;
        if (availableCategory.contains(category)) {
            sortedBook = books.stream().filter(book -> book.getCategory().equalsIgnoreCase(category)).toList();
        } else {
            sortedBook = Collections.emptyList();
        }
        return sortedBook;
    }

    public Set<String> availableCategory() {
        Set<String> availableCategory = booksList.stream().map(book1 -> book1.getCategory().toLowerCase()).collect(Collectors.toSet());
        return availableCategory;
    }

    public static Page<Book> findPaginated(Pageable pageable, List<Book> bookList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Book> list;
        if (bookList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, bookList.size());
            list = bookList.subList(startItem, toIndex);
        }
        Page<Book> bookPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), bookList.size());
        return bookPage;
    }

    public static void extracted(Model model, int currentPage, int pageSize, List<Book> bookListCheck) {
        Page<Book> bookPage = findPaginated(PageRequest.of(currentPage - 1, pageSize), bookListCheck);
        model.addAttribute("books", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            int maxVisiblePages = 5; // Maksymalna liczba widocznych numerów stron
            int startPage = Math.max(1, currentPage - maxVisiblePages);
            int endPage = Math.min(totalPages, currentPage + maxVisiblePages);

            if (currentPage > 6) {
                model.addAttribute("firstPage", 1);
            }

            if (currentPage < totalPages - 5) {
                model.addAttribute("lastPage", totalPages);
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}