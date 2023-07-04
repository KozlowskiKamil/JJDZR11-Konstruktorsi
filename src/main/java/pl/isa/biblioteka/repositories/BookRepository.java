package pl.isa.biblioteka.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.isa.biblioteka.model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

//    static void saveBooks() {
//        ObjectMapper mapper = new ObjectMapper();
//        List<Book> booksList = BookService.booksList;
//        try {
//            mapper.writeValue(new File("booksFile.json"), booksList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    static List<Book> readBooks() {
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