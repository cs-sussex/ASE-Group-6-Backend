package uk.ac.sussex.group6.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Models.PricePaidData;
import uk.ac.sussex.group6.backend.Models.Property;
import uk.ac.sussex.group6.backend.Payloads.AllOnScreenRequest;
import uk.ac.sussex.group6.backend.Payloads.SpecificLocationResponse;
import uk.ac.sussex.group6.backend.Repositories.PropertyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    public SpecificLocationResponse findAllWherePostcodeContains(String postcode) {

        List<Property> properties = propertyRepository.findAllByPostcodeContains(postcode.toUpperCase());

        long totalValue = 0;
        int numberOfProperties = 0;

        for (Property p : properties) {
            for (PricePaidData PPD : p.getPricePaidData()) {
                totalValue = totalValue + PPD.getPricePaid();
                numberOfProperties++;
            }
        }
        Double average;
        if (numberOfProperties != 0) {
            BigDecimal bigDecimal = BigDecimal.valueOf((double) (totalValue / numberOfProperties));
            bigDecimal.setScale(2, RoundingMode.HALF_UP);
            average = bigDecimal.doubleValue();
        } else {
            average = 0.00;
        }

        return new SpecificLocationResponse(postcode, average, numberOfProperties);
    }

    public Property findById(String id) {
        return propertyRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public List<SpecificLocationResponse> allOnScreen(AllOnScreenRequest allOnScreenRequest) {
        List<SpecificLocationResponse> specificLocationResponses = new ArrayList<>();
        for (String s : allOnScreenRequest.getOutcodes()) {
            specificLocationResponses.add(findAllWherePostcodeContains(s));
        }
        return specificLocationResponses;
    }
}
