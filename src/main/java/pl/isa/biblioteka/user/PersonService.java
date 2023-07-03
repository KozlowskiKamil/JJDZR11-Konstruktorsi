package pl.isa.biblioteka.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.isa.biblioteka.book.Book;
import pl.isa.biblioteka.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final PersonDAO personDAO;

    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    private static final Logger LOGGER = Logger.getLogger(PersonService.class.getName());

    public static List<User> users = new ArrayList<>(PersonService.readUsers());

    public static List<Book> personBooks = new ArrayList<>();

    public void setPersonBooks(List<Book> personBooks) {
        this.personBooks = personBooks;
    }

    public List<Book> getPersonBooks() {
        return personBooks;
    }

    public static User currentLogUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                for (User user : users) {
                    if (user.getUsername().equalsIgnoreCase(username)) {
                        personBooks = user.getPersonBooks();
                        return user;
                    }
                }
            }
        }
        return null;
    }

    public String registerUserId(User user) {
        boolean userExist = personDAO.isLoginTaken(user.getUsername());
        if (userExist) {
            return "Login jest już zajęty, wybierz inny login";
        }

        User savedUser = personDAO.savePerson(user);
        if (savedUser != null) {
            return "Dodano użytkownika: " + savedUser.getUsername() + ", możesz się zalogować";
        } else {
            return "Wystąpił problem podczas rejestracji użytkownika";
        }
    }

    public String editUserId(User user, Integer id) {
        boolean userExist = personDAO.isLoginTaken(user.getUsername());
        if (userExist) {
            return "Użytkownik edytowany";
        }
        user.setId(id);
        personDAO.savePerson(user);
        return "Nie ma takiego użytkownika";
    }

    public static List<User> readUsers() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("users.json"));
            ObjectMapper folderPerson = new ObjectMapper();
            LOGGER.info("------User read correctly------");
            return Arrays.asList(folderPerson.readValue(jsonData, User[].class));
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("------User not read error------");
            return Collections.emptyList();
        }
    }

    public static void saveUsers() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<User> userList = users;
        try {
            mapper.writeValue(new File("users.json"), userList);
            LOGGER.info("------User saved correctly------");
        } catch (IOException e) {
            LOGGER.info("------User not saved error------");
            e.printStackTrace();
        }
    }

    public PersonDTO findId(Integer id) {
        User user = users.stream().filter(user1 -> user1.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Nie ma takiego użytkownika"));
        return new PersonDTO(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(), user.getSecondName(), user.getEmail());
    }

    public void delete(Integer id) {
        users.removeIf(s -> s.getId().equals(id));
        saveUsers();
    }
}