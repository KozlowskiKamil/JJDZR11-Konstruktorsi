package pl.isa.biblioteka.servises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.isa.biblioteka.model.Book;
import pl.isa.biblioteka.model.User;
import pl.isa.biblioteka.repositories.BookRepository;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BookService {
    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());
    public static List<Book> booksList2;

    private final BookRepository bookRepository;
    private final UserService userService;

    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }


    public void saveBooks(){
        booksList2 = getBooks();
        bookRepository.saveAll(booksList2);
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


    public List<Book> searchByText(String text) {
        List<Book> searchText = getBooks().stream().filter(book -> book.getTitle().contains(text) || book.getAuthor().contains(text)).collect(Collectors.toList());

        return searchText;
    }

    public List<Book> showAllAvailableBooks() {
        return getBooks().stream().filter(Book::isState).toList();
    }

    public List<Book> showAllBorrowedBooks() {
        return getBooks().stream().filter(book -> !book.isState()).toList();
    }


    public List<Book> showAllBooks() {
        return getBooks();
    }

    public List<Book> findBookByTitle(String title) {
        List<Book> books = getBooks().stream().filter(Book::isState).toList();
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
        List<Book> books = getBooks().stream().filter(Book::isState).toList();
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
        List<Book> books = getBooks().stream().filter(Book::isState).toList();
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
        Set<String> availableCategory = getBooks().stream().map(book1 -> book1.getCategory().toLowerCase()).collect(Collectors.toSet());
        return availableCategory;
    }

    public String addBook(Book book) {
        bookRepository.save(book);
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

    public boolean addBookToPerson(User person, Book book) {
        for (Book books : getBooks()) {
            if (books.getId().equals(book.getId()) && book.isState()) {
                book.setState(false);
                person.getPersonBooks().add(book);
                return true;
            }
        }
        return false;
    }


    public boolean returnBook(User person, Book book) {
        for (Book personBook :  person.getPersonBooks()) {
            if (personBook.getTitle().equalsIgnoreCase(book.getTitle()) && !personBook.isState()) {
                for (Book books : getBooks()) {
                    if (books.getTitle().equalsIgnoreCase(book.getTitle())) {
                        books.setState(true);
                    }
                }
                person.getPersonBooks().removeIf(book1 -> book1.getTitle().equalsIgnoreCase(book.getTitle()));
                return true;
            }
        }
        return false;
    }
}
