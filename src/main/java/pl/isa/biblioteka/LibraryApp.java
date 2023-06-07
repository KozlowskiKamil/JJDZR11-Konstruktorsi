package pl.isa.biblioteka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication/*(exclude = SecurityAutoConfiguration.class)*/
public class LibraryApp {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApp.class, args);

    }

}