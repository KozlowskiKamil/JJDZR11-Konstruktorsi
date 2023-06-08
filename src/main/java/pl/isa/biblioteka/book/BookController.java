package pl.isa.biblioteka.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.isa.biblioteka.book.BookService.booksList;
import static pl.isa.biblioteka.book.BookService.extracted;

@Controller
public class BookController {

    private final BookService bookService;

    List<Book> bookListByAuthor;
    List<Book> searchCategoryBook;
    List<Book> searchBook;
    List<Book> localSearchBook;

    public BookController(BookService bookService) {
        this.bookService = bookService;
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
            return "list";
        } else return "list";
    }

    @PostMapping("/filterTitle")
    String showByTitle(@ModelAttribute("book") Book book) {
        searchBook = bookService.findBookByTitle(book.getTitle());
        return "redirect:bookByTitle";
    }

//    @GetMapping("/bookByTitle")
//    String bookTitle(Model model) {
//        model.addAttribute("books", searchBook);
//        return "list";
//    }

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
            return "list";
        } else return "list";
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
            return "list";
        } else return "list";
    }

/*    @GetMapping("/searchText")
    public String searchByText(Model model) {
                model.addAttribute("books", localSearchBook);

        return "list";
    }*/

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
//        model.addAttribute("books", searchBook);
        return "redirect:searchText";
    }

}
