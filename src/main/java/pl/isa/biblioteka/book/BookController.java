package pl.isa.biblioteka.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.isa.biblioteka.file.FolderBooks;
import pl.isa.biblioteka.user.Person;
import pl.isa.biblioteka.user.PersonService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static pl.isa.biblioteka.book.BookService.booksList;
import static pl.isa.biblioteka.book.BookService.extracted;

@Controller
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    List<Book> bookListByAuthor;
    List<Book> searchCategoryBook;
    List<Book> searchBook;
    List<Book> localSearchBook;
    List<Book> availableBooks;
    List<Book> borrowedBooks;

    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }


    @GetMapping("/bookList")
    public String listBooks(Principal principal,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        extracted(model, currentPage, pageSize, booksList);
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "list";
        } else return "list";
    }

    @GetMapping("/search")
    String search(Principal principal, Model model, Book book) {
        model.addAttribute("book", book);
        model.addAttribute("category", bookService.availableCategory());

        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "search";
        } else return "search";
    }

    @PostMapping("/filterAuthor")
    String showByAuthor(@ModelAttribute("book") Book book) {
        bookListByAuthor = bookService.filterByAuthor(book.getAuthor());
        return "redirect:bookAuthorList";
    }


    @RequestMapping(value = "/bookAuthorList", method = RequestMethod.GET)
    public String listBooksAuthor(Principal principal,
            Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        extracted(model, currentPage, pageSize, bookListByAuthor);
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "listToBorrow";
        } else return "listToBorrow";
    }

    @PostMapping("/filterTitle")
    String showByTitle(@ModelAttribute("book") Book book) {
        searchBook = bookService.findBookByTitle(book.getTitle());
        return "redirect:bookByTitle";
    }

    @RequestMapping(value = "/bookByTitle", method = RequestMethod.GET)
    public String listBooksTitle(Principal principal,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        extracted(model, currentPage, pageSize, searchBook);
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "listToBorrow";
        } else return "listToBorrow";
    }

    @PostMapping("/filterCategory")
    String showByCategory(@ModelAttribute("book") Book book) {
        searchCategoryBook = bookService.sortByCategory(book.getCategory());
        return "redirect:bookByCategory";
    }


    @RequestMapping(value = "/bookByCategory", method = RequestMethod.GET)
    public String listBooksCategory(Principal principal,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        extracted(model, currentPage, pageSize, searchCategoryBook);
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "listToBorrow";
        } else return "listToBorrow";
    }

    @GetMapping("/searchText")
    public String searchByText(Principal principal,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        extracted(model, currentPage, pageSize, localSearchBook);
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "list";
        } else return "list";
    }

    @PostMapping("/searchByText")
    public String searchByText(@RequestParam("text") String text, Model model) {
        localSearchBook = bookService.searchByText(text);
        return "redirect:searchText";
    }

@GetMapping("/librarianDay")
public String librarianDay(Model model) {
    return "librarianDay";
    }

    @GetMapping("/addBook")
    public String addBook(Principal principal, Model model) {
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "addBook";
        } else return "addBook";
    }
    @GetMapping("/myBooksReturnByName")
    public String deleteBook(@RequestParam("name") String name) {
        bookService.returnBook(name);
        personService.saveUsers();
        return "redirect:/myBooksReturn";
    }

    @GetMapping("/myBooksReturn")
    public String returnMyBook(Principal principal,Model model){
        List<Person> users = PersonService.readUsers();
        model.addAttribute("users", users);
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "returnBook";
        } else return "returnBook";
    }



    @RequestMapping(value = "/borrowBooksList", method = RequestMethod.GET)
    public String listBooksToBorrow(Principal principal,
                                  Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        extracted(model, currentPage, pageSize, booksList);
        FolderBooks.saveBooks();
        PersonService.saveUsers();
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "listToBorrow";
        } else return "listToBorrow";
    }

    @GetMapping("/myBookBorrowByName")
    public String borrowBook(@RequestParam("name") String name) {
        bookService.addBookToPerson(name);
        return "redirect:/borrowBooksList";
    }


    @PostMapping("/addBook")
    public String addBook(Principal principal, @RequestParam String title, @RequestParam String author, @RequestParam String category, Model model) {
        Book book = new Book(title, author, category);
        String str = BookService.addBook(book);
        model.addAttribute("result", book);
        model.addAttribute("mesage", str);
        BookRepository.saveBooks();
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "addBook";
        } else return "addBook";
    }
    @GetMapping("/availableBooks")
    public String availableBooks (Principal principal, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        availableBooks = bookService.showAllAvailableBooks();

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        extracted(model, currentPage, pageSize, availableBooks);
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "availableBooks";
        } else return "availableBooks";

    }
    @GetMapping("/borrowedBooks")
    public String borrowedBooks (Principal principal, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        borrowedBooks = bookService.showAllBorrowedBooks();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        extracted(model, currentPage, pageSize, borrowedBooks);
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "borrowedBooks";
        } else return "borrowedBooks";

    }
}

