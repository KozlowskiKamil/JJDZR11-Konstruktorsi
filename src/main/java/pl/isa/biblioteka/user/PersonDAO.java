package pl.isa.biblioteka.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.isa.biblioteka.model.User;

import java.util.List;

@Repository
public class PersonDAO {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    public PersonDAO(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }


    @Transactional
    public User savePerson(User user) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            entityManager.persist(user);
            return user;
        } else {
            entityManager.merge(user);
            return user;
        }
    }


    @Transactional
    public User editUserId(User user, String newPassword) {
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        entityManager.merge(user);
        return user;
    }


    @Transactional
    public void delate(Integer id) {
        entityManager.remove(findById(id));
    }

    public boolean isLoginTaken(String username) {
        return findAll().stream().anyMatch(person -> person.getUsername().equalsIgnoreCase(username));
    }


    public boolean isLoginTakenByOtherUser(int userId, String username) {
        return findAll().stream().anyMatch(person -> person.getUsername().equalsIgnoreCase(username) && person.getId() != userId);
    }

}