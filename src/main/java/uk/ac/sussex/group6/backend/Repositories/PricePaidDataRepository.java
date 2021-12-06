package uk.ac.sussex.group6.backend.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.sussex.group6.backend.Models.PricePaidData;

public interface PricePaidDataRepository extends MongoRepository<PricePaidData, String> {
}
