package pl.isa.biblioteka.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.isa.biblioteka.model.Book;
import pl.isa.biblioteka.repositories.UserRepository;
import pl.isa.biblioteka.servises.BookService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.isa.biblioteka.dto.PersonDTO;
import pl.isa.biblioteka.model.User;
import pl.isa.biblioteka.servises.BookService;
import pl.isa.biblioteka.servises.PersonService;
import pl.isa.biblioteka.servises.UserService;
import pl.isa.biblioteka.user.PersonDAO;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {


    private final BookService bookService;
    private final UserService userService;
    private final PersonService personService;
    private final PersonDAO personDAO;
    private final UserRepository userRepository;

    public UserController(BookService bookService, UserService userService, PersonService personService, PersonDAO personDAO, UserRepository userRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.personService = personService;
        this.personDAO = personDAO;
        this.userRepository = userRepository;
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
    public String myBooks(Authentication principal, Model model) {
        List<User> users = userService.getAllPerson();
        User user = userRepository.findByUsername(principal.getName());
        List<Book> personBooks = user.getPersonBooks();
        model.addAttribute("users", users);
        model.addAttribute("books", personBooks);

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

            existUser.setUsername(personDTO.getUsername());
            existUser.setFirstName(personDTO.getFirstName());
            existUser.setSecondName(personDTO.getSecondName());
            existUser.setEmail(personDTO.getEmail());
            personDAO.editUserId(existUser, personDTO.getPassword());
        }
        return "redirect:/usersList";
    }
}