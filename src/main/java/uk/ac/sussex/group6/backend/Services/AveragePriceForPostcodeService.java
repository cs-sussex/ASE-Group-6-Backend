package uk.ac.sussex.group6.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Models.AveragePriceForPostcode;
import uk.ac.sussex.group6.backend.Repositories.AveragePriceForPostcodeRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class AveragePriceForPostcodeService {

    @Autowired
    private AveragePriceForPostcodeRepository repository;

    public AveragePriceForPostcode getAverage(String postcode) {
        return repository.findByPostcode(postcode).isPresent() ? repository.findByPostcode(postcode.toUpperCase()).get() : null;
    }

    public AveragePriceForPostcode createNewAverage(AveragePriceForPostcode averagePriceForPostcode) {
        Optional<AveragePriceForPostcode> postcodeOptional = repository.findByPostcode(averagePriceForPostcode.getPostcode());
        if (postcodeOptional.isPresent()) {
            postcodeOptional.get().setAverage(averagePriceForPostcode.getAverage());
            postcodeOptional.get().setNumberOfProperties(averagePriceForPostcode.getNumberOfProperties());
            postcodeOptional.get().setDateChecked(new Date());
            repository.save(postcodeOptional.get());
        }
        return repository.save(averagePriceForPostcode);
    }
}
