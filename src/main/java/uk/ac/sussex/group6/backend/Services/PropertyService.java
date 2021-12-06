package uk.ac.sussex.group6.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Models.Property;
import uk.ac.sussex.group6.backend.Repositories.PropertyRepository;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    public List<Property> findAllWherePostcodeContains(String postcode) {
        return propertyRepository.findAllByPostcodeContains(postcode);
    }

    public Property findById(String id) {
        return propertyRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    }
}
