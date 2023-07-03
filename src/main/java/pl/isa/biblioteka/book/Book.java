package pl.isa.biblioteka.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.type.YesNoConverter;
import pl.isa.biblioteka.model.User;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @JsonProperty("kind")
    @Column(name = "category", nullable = false)
    private String category;

    @Convert(converter = YesNoConverter.class)
    private boolean state = true;

    @CreationTimestamp
    @Column(name = "borrowing_date", updatable = false)
    private LocalDateTime borrowingDate;

    @CreationTimestamp
    @Column(name = "return_date", updatable = false)
    private LocalDateTime returnDate;


    @ManyToOne
    private User user;

    //mennyToOne
    //User person

    public Book() {
    }

    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.state = true;
    }

    public Book(String title, String author, String category, boolean state) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Book{" + "title='" + title + '\'' + ", author='" + author + '\'' + ", category='" + category + '\'' + ", state=" + state + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}