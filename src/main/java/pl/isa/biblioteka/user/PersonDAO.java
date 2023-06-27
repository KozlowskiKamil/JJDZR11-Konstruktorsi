package pl.isa.biblioteka.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Person savePerson(Person person) {
        entityManager.merge(person);
        return person;
    }



}