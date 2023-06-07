package pl.isa.biblioteka.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.isa.biblioteka.book.Book;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PersonService {


    public List<Book> personBooks = new ArrayList<>();

    public static List<Person> readUsers() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("users.json"));
            ObjectMapper folderPerson = new ObjectMapper();
            return Arrays.asList(folderPerson.readValue(jsonData, Person[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

//    public static void saveUsers() {
//        ObjectMapper mapper = new ObjectMapper();
////        List<Person> personList = Users.users;
//        try {
//            mapper.writeValue(new File("users.json"), personList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}