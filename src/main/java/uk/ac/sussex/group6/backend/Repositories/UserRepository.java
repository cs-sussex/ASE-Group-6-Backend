package uk.ac.sussex.group6.backend.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.sussex.group6.backend.Models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
