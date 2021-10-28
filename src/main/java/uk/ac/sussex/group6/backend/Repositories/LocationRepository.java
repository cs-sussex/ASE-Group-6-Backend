package uk.ac.sussex.group6.backend.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.sussex.group6.backend.Models.Location;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location, String> {

    List<Location> findAllByUserId(String id);

}
