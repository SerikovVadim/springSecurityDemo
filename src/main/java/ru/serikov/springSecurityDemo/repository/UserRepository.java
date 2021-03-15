package ru.serikov.springSecurityDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serikov.springSecurityDemo.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
