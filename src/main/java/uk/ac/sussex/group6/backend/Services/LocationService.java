package uk.ac.sussex.group6.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Models.Location;
import uk.ac.sussex.group6.backend.Payloads.AddLocationRequest;
import uk.ac.sussex.group6.backend.Repositories.LocationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserService userService;

    public List<Location> getAll() { return locationRepository.findAll();}

    public Location find(String id) {
        Location locationOptional = locationRepository.findById(id).orElseThrow(()->new BadRequestException("Location not found"));
        return locationOptional;
    }

    public Location create(AddLocationRequest addLocationRequest) {
        return locationRepository.save(new Location(userService.getById(addLocationRequest.getUserId()), addLocationRequest.getLocation_name(),
                addLocationRequest.getLongitude(), addLocationRequest.getLatitude(), addLocationRequest.getColour()));
    }

    public void delete(String id) {
        Location locationOptional = locationRepository.findById(id).orElseThrow(()->new BadRequestException("Location not found"));
        locationRepository.delete(locationOptional);
    }

    public Location update(AddLocationRequest addLocationRequest, String id) {
        Location locationOptional = locationRepository.findById(id).orElseThrow(()->new BadRequestException("Location not found"));

        locationOptional.setLocation_name(addLocationRequest.getLocation_name());
        locationOptional.setLongitude(addLocationRequest.getLongitude());
        locationOptional.setLatitude(addLocationRequest.getLatitude());
        locationOptional.setColour(addLocationRequest.getColour());

        return locationRepository.save(locationOptional);
    }

    public List<Location> findByUserId(String id) {
        return locationRepository.findAllByUserId(id);
    }
}
