package uk.ac.sussex.group6.backend.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.sussex.group6.backend.Models.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends MongoRepository<Property, String> {
    boolean existsByPostcodeAndStreetAndPaon(String postcode, String street, String paon);

    Optional<Property> findByPostcodeAndStreetAndPaon(String postcode, String street, String paon);

    List<Property> findAllByPostcodeContains(String postcode);
}
