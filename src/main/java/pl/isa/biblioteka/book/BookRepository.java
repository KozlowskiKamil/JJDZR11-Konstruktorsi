package pl.isa.biblioteka.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class BookRepository {

    public static void saveBooks() {
        ObjectMapper mapper = new ObjectMapper();
        List<Book> booksList = BookService.booksList;
        try {
            mapper.writeValue(new File("booksFile.json"), booksList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> readBooks() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("booksFile.json"));
            ObjectMapper folderBooks = new ObjectMapper();
            return Arrays.asList(folderBooks.readValue(jsonData, Book[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}