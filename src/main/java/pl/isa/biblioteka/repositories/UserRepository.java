package pl.isa.biblioteka.repositories;

import pl.isa.biblioteka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String userName);

  boolean existsByUsername(String userName);

  boolean existsByEmail(String email);
}
