package uk.ac.sussex.group6.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Exceptions.ForbiddenException;
import uk.ac.sussex.group6.backend.Models.AveragePriceDate;
import uk.ac.sussex.group6.backend.Models.Location;
import uk.ac.sussex.group6.backend.Payloads.AddLocationRequest;
import uk.ac.sussex.group6.backend.Repositories.LocationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserService userService;

    public Location find(String id, String userID) {
        Location locationOptional = locationRepository.findById(id).orElseThrow(()->new BadRequestException("Location not found"));
        if (!locationOptional.getUser().getId().equals(userID)) {
            throw new ForbiddenException("Not authorised to view this object");
        }
        return locationOptional;
    }

    public Location create(AddLocationRequest addLocationRequest) {
        return locationRepository.save(new Location(userService.getById(
                addLocationRequest.getUserId()),
                addLocationRequest.getLocation_name(),
                addLocationRequest.getLongitude(),
                addLocationRequest.getLatitude(),
                addLocationRequest.getAveragePrice(),
                new Date(),
                addLocationRequest.getColour())
        );
    }

    public void delete(String id, String userID) {
        Location locationOptional = locationRepository.findById(id).orElseThrow(()->new BadRequestException("Location not found"));
        if (!locationOptional.getUser().getId().equals(userID)) {
            throw new ForbiddenException("Not authorised to delete this object");
        }
        locationRepository.delete(locationOptional);
    }

    public Location update(AddLocationRequest addLocationRequest, String id) {
        Location locationOptional = locationRepository.findById(id).orElseThrow(()->new BadRequestException("Location not found"));

        locationOptional.setLocation_name(addLocationRequest.getLocation_name());
        locationOptional.setColour(addLocationRequest.getColour());

        return locationRepository.save(locationOptional);
    }

    public Location addNewAverage(String id, AveragePriceDate averagePriceDate, String userID) {
        Location locationOptional = locationRepository.findById(id).orElseThrow(()->new BadRequestException("Location not found"));
        if (!locationOptional.getUser().getId().equals(userID)) {
            throw new ForbiddenException("Not authorised to edit this object");
        }
        locationOptional.getAveragePriceDates().add(averagePriceDate);
        return locationRepository.save(locationOptional);
    }

    public List<Location> findByUserId(String id) {
        return locationRepository.findAllByUserId(id);
    }
}
