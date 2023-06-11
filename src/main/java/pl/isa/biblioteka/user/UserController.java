package pl.isa.biblioteka.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

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
//        personService.readUsers();
        Person newPerson = new Person(login, password, firstName, secondName, email);
        personService.registerUserId(newPerson);
/*        List<Person> persons = PersonService.readUsers();
        model.addAttribute("persons", persons);*/
        personService.saveUsers();
        return "register";
    }


    @PostMapping("/edit")
    public String editUser(@RequestParam String login, @RequestParam String password, @RequestParam String firstName, @RequestParam String secondName, @RequestParam String email, Model model) {
        personService.readUsers();
        List<Person> persons = PersonService.readUsers();
        model.addAttribute("persons", persons);
        Person newPerson = new Person(login, password, firstName, secondName, email);
        personService.registerUserId(newPerson);
        personService.saveUsers();
        return "usersList";
    }

}
