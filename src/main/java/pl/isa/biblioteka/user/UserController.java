package pl.isa.biblioteka.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.isa.biblioteka.book.BookService;
import pl.isa.biblioteka.model.User;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//import static pl.isa.biblioteka.user.PersonService.editUserId;
//import static pl.isa.biblioteka.user.PersonService.registerUserId;


@Controller
public class UserController {


    private final BookService bookService;
    private final PersonService personService;
    private final PersonDAO personDAO;

    public UserController(BookService bookService, PersonService personService, PersonDAO personDAO) {
        this.bookService = bookService;
        this.personService = personService;
        this.personDAO = personDAO;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        personDAO.delate(id);
        return "redirect:/usersList";
    }

    @GetMapping("/usersList")
    public String getUsers(Principal principal, Model model) {
        List<User> users = personDAO.findAll();
        Collections.sort(users, Comparator.comparing(User::getId));
        model.addAttribute("users", users);

        return "usersList";
    }

    @GetMapping("/myBooks")
    public String myBooks(Principal principal, Model model) {
        List<User> users = PersonService.readUsers();
        model.addAttribute("users", users);

        return "myBooks";

    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@RequestParam String login, @RequestParam String password, @RequestParam String firstName, @RequestParam String secondName, @RequestParam String email, Model model) {
        User newUser = new User(login, password, firstName, secondName, email);
        String result = personService.registerUserId(newUser);
        model.addAttribute("mesage", result);
        PersonService.saveUsers();
        return "register";
    }

    @GetMapping("/edit/{id}")
    public String edit(Principal principal, @PathVariable("id") Integer id, Model model) {
        User userDTO = personDAO.findById(id);
        model.addAttribute("personDTO", userDTO);

        return "edit";

    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, @ModelAttribute PersonDTO personDTO, RedirectAttributes redirectAttributes, @RequestParam(value = "isAdmin", required = false) String isAdmin) {
        User existUser = personDAO.findById(id);
        if (existUser != null) {
            String newUsername = personDTO.getUsername();
            if (personDAO.isLoginTakenByOtherUser(existUser.getId(), newUsername)) {
                redirectAttributes.addFlashAttribute("mesage", "Użytkownik o podanym loginie jest już zajęty, wybierz inny login");
                return "redirect:/edit/" + id;
            }
            if (isAdmin != null) {
                existUser.setRole("ROLE_ADMIN");
            } else {
                existUser.setRole("ROLE_USER");
            }
            if (personDTO.getPassword() != "") {
                existUser.setPassword(personDTO.getPassword());
            }
            existUser.setUsername(personDTO.getUsername());
            existUser.setFirstName(personDTO.getFirstName());
            existUser.setSecondName(personDTO.getSecondName());
            existUser.setEmail(personDTO.getEmail());
            personDAO.editUserId(existUser);
        }
        return "redirect:/usersList";
    }
}