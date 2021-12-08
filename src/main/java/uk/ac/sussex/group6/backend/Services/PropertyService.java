package uk.ac.sussex.group6.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Models.AveragePriceForPostcode;
import uk.ac.sussex.group6.backend.Models.PricePaidData;
import uk.ac.sussex.group6.backend.Models.Property;
import uk.ac.sussex.group6.backend.Payloads.AllOnScreenRequest;
import uk.ac.sussex.group6.backend.Payloads.SpecificLocationResponse;
import uk.ac.sussex.group6.backend.Repositories.PropertyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private AveragePriceForPostcodeService APPS;

    public SpecificLocationResponse findAllWherePostcodeContains(String postcode) {
        AveragePriceForPostcode app = APPS.getAverage(postcode);
        if (app != null && TimeUnit.DAYS.convert(Math.abs(new Date().getTime() - app.getDateChecked().getTime()), TimeUnit.MILLISECONDS) < 14) {
            return new SpecificLocationResponse(app.getPostcode(), app.getAverage(), app.getNumberOfProperties());
        } else {
            List<Property> properties;
            if (postcode.length() <= 2) {
                properties = propertyRepository.findAllByPostcodeStartingWith(postcode.toUpperCase());
            } else {
                properties = propertyRepository.findAllByPostcodeContains(postcode.toUpperCase());
            }

            long totalValue = 0;
            int numberOfProperties = 0;

            for (Property p : properties) {
                for (PricePaidData PPD : p.getPricePaidData()) {
                    totalValue = totalValue + PPD.getPricePaid();
                    numberOfProperties++;
                }
            }
            double average;
            if (numberOfProperties != 0) {
                BigDecimal bigDecimal = BigDecimal.valueOf((double) (totalValue / numberOfProperties));
                bigDecimal.setScale(2, RoundingMode.HALF_UP);
                average = bigDecimal.doubleValue();
            } else {
                average = 0.00;
            }
            APPS.createNewAverage(new AveragePriceForPostcode(postcode, average, numberOfProperties, new Date()));
            return new SpecificLocationResponse(postcode, average, numberOfProperties);
        }

    }

    public List<SpecificLocationResponse> allOnScreen(AllOnScreenRequest allOnScreenRequest) {
        List<SpecificLocationResponse> specificLocationResponses = new ArrayList<>();
        for (String s : allOnScreenRequest.getOutcodes()) {
            specificLocationResponses.add(findAllWherePostcodeContains(s));
        }
        return specificLocationResponses;
    }
}
