package pl.isa.biblioteka.servises;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import pl.isa.biblioteka.model.Book;
import pl.isa.biblioteka.model.User;
import pl.isa.biblioteka.user.PersonDAO;

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

    private static final Logger LOGGER = Logger.getLogger(PersonService.class.getName());
    public static List<User> users = new ArrayList<>(PersonService.readUsers());
    public static List<Book> personBooks = new ArrayList<>();
    private final PersonDAO personDAO;

    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
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

    public static List<User> readUsers() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("users.json"));
            ObjectMapper folderUser = new ObjectMapper();
            return Arrays.asList(folderUser.readValue(jsonData, User[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Book> getPersonBooks() {
        return personBooks;
    }

    public void setPersonBooks(List<Book> personBooks) {
        PersonService.personBooks = personBooks;
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
}