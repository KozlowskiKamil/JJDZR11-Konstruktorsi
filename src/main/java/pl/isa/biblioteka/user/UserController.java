package pl.isa.biblioteka.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.isa.biblioteka.book.BookService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final BookService bookService;
    private final PersonService personService;

    public UserController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        personService.delete(id);
        return "redirect:/usersList";
    }
//    myBooksReturnByName

    @GetMapping("/myBooksReturnByName")
    public String delete(@RequestParam("name") String name) {
        bookService.returnBook(name);
        return "redirect:/returnBook";
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

    @GetMapping("/usersList")
    public String getUsers(Principal principal, Model model) {
        List<Person> users = PersonService.readUsers();
        model.addAttribute("users", users);
        personService.readUsers();
        personService.saveUsers();
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "usersList";
        } else return "usersList";
    }

    @GetMapping("/myBooks")
    public String myBooks(Principal principal, Model model) {
        List<Person> users = PersonService.readUsers();
        model.addAttribute("users", users);
        if (principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            return "myBooks";
        } else return "myBooks";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @PostMapping("/register")
    public String addUser(@RequestParam String login, @RequestParam String password, @RequestParam String firstName, @RequestParam String secondName, @RequestParam String email, Model model) {
        personService.readUsers();
        Person newPerson = new Person(login, password, firstName, secondName, email);
        personService.registerUser(newPerson);
        List<Person> persons = PersonService.readUsers();
        model.addAttribute("persons", persons);
        personService.saveUsers();
        return "register";
    }

}
