package pl.isa.biblioteka.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static pl.isa.biblioteka.user.PersonService.editUserId;
import static pl.isa.biblioteka.user.PersonService.registerUserId;

@Controller
public class UserController {

    private final PersonService personService;

    @Autowired
    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        personService.delete(id);
        return "redirect:/usersList";
    }

    @GetMapping("/usersList")
    public String getUsers(Principal principal, Model model) {
        List<Person> users = PersonService.readUsers();
        Collections.sort(users, Comparator.comparing(Person::getId));
        model.addAttribute("users", users);
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
        Person newPerson = new Person(login, password, firstName, secondName, email);
        String result = registerUserId(newPerson);
        model.addAttribute("mesage", result);
        PersonService.saveUsers();
        return "register";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        PersonDTO personDTO = personService.findId(id);
        model.addAttribute("personDTO", personDTO);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, @ModelAttribute PersonDTO personDTO) {
        personService.delete(id);
        Person person = new Person(personDTO.getLogin(), personDTO.getPassword(), personDTO.getFirstName(), personDTO.getSecondName(), personDTO.getEmail());
        editUserId(person, id);
        PersonService.saveUsers();
        return "redirect:/usersList";
    }
}