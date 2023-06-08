package pl.isa.biblioteka.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {


    private final PersonService personService;

    @Autowired
    public UserController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/users")
    public String getUsers(Model model) {
        List<Person> users = PersonService.readUsers();
        model.addAttribute("users", users);
        personService.readUsers();
        personService.saveUsers();
        return "users";
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
