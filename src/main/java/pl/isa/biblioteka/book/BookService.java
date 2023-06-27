package pl.isa.biblioteka.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.isa.biblioteka.file.FolderBooks;
import pl.isa.biblioteka.user.Person;
import pl.isa.biblioteka.user.PersonService;

import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BookService {
    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());
    public static List<Book> booksList = new ArrayList<>(FolderBooks.readBooks());


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

    public void deleteBookByTitle(String title) {
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

    public List<Book> showAllAvailableBooks() {
        return booksList.stream().filter(Book::isState).toList();
    }

    public List<Book> showAllBorrowedBooks() {
        return booksList.stream().filter(book -> !book.isState()).toList();
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

    public static String addBook(Book book) {
        booksList.add(book);
        LOGGER.info("Dodano książkę: " + book.getTitle() + " autora: " + book.getAuthor());
        return "Dodano nową książkę " + book.getTitle() + " autora: " + book.getAuthor();
    }


    public static void extracted(Model model, int currentPage, int pageSize, List<Book> bookListCheck) {
        Page<Book> bookPage = findPaginated(PageRequest.of(currentPage - 1, pageSize),bookListCheck);
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
            List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

    public boolean addBookToPerson(String bookTitle) {
        Person person = PersonService.currentLogUser();
        for (Book book : booksList) {
            if (book.getTitle().equalsIgnoreCase(bookTitle) && book.isState()) {
                book.setState(false);
                person.getPersonBooks().add(book);
//                PersonService.personBooks.add(book);
                return true;
            }
        }
        return false;
    }


    private static Predicate<Book> foundBookByTitle(String bookReturnTitle) {
        return book -> book.getTitle().equalsIgnoreCase(bookReturnTitle);
    }

    public boolean returnBook(String bookTitleToReturn) {
        for (Book personBook :  PersonService.currentLogUser().getPersonBooks()) {
            if (personBook.getTitle().equalsIgnoreCase(bookTitleToReturn) && !personBook.isState()) {
                for (Book book : booksList) {
                    if (book.getTitle().equalsIgnoreCase(bookTitleToReturn)) {
                        book.setState(true);
//                        return true;
                    }
                }
                PersonService.personBooks.removeIf(foundBookByTitle(bookTitleToReturn));
                return true;
            }
        }
        return false;
    }


}
