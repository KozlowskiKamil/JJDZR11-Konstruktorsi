package pl.isa.biblioteka.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import pl.isa.biblioteka.book.Book;

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


    public static List<Person> users = new ArrayList<>(PersonService.readUsers());

    public final  List<Person> personList;
    public List<Book> personBooks = new ArrayList<>();

    public PersonService(List<Person> personList) {
        this.personList = personList;
    }

    public void registerUser(Person person) {
/*        int nextId = users.size() + 1;
        person.setId(nextId);*/
        users.add(person);
        saveUsers();
    }

    public void delete(Integer id) {
        users.removeIf(s -> s.getId().equals(id));
        saveUsers();
    }


    public static List<Person> readUsers() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("users.json"));
            ObjectMapper folderPerson = new ObjectMapper();
            LOGGER.info("------User read correctly------");
            return Arrays.asList(folderPerson.readValue(jsonData, Person[].class));
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("------User not read error------");
            return Collections.emptyList();
        }
    }

    public static void saveUsers() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<Person> personList = users;
        try {
            mapper.writeValue(new File("users.json"), personList);
            LOGGER.info("------User saved correctly------");
        } catch (IOException e) {
            LOGGER.info("------User not saved error------");
            e.printStackTrace();
        }
    }
}