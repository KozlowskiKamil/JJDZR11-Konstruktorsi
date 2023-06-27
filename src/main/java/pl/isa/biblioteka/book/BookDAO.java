package pl.isa.biblioteka.book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Book saveBook(Book book) {
        entityManager.merge(book);
        return book;
    }
}
