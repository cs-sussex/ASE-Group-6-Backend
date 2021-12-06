package uk.ac.sussex.group6.backend.Datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.sussex.group6.backend.Models.Property;
import uk.ac.sussex.group6.backend.Services.PropertyService;
import uk.ac.sussex.group6.backend.Services.UserService;

import java.util.List;
import java.util.Map;

@Component
public class AllPropertiesDataFetcher implements DataFetcher<List<Property>> {

    @Autowired
    private PropertyService propertyService;

    @Override
    public List<Property> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Map args = dataFetchingEnvironment.getArguments();
        List<Property> properties;
        if (args.containsKey("postcode")) {
            properties = propertyService.findAllWherePostcodeContains((String) args.get("postcode"));
        } else {
            properties = propertyService.findAll();
        }
        return properties;
    }
}
