package uk.ac.sussex.group6.backend.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.sussex.group6.backend.Models.AveragePriceForPostcode;

import java.util.Optional;

public interface AveragePriceForPostcodeRepository extends MongoRepository<AveragePriceForPostcode, String> {
    Optional<AveragePriceForPostcode> findByPostcode(String postcode);
}
