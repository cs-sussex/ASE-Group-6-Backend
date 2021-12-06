package uk.ac.sussex.group6.backend.Datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import uk.ac.sussex.group6.backend.Models.Property;
import uk.ac.sussex.group6.backend.Services.PropertyService;

import java.util.Map;

@Component
public class PropertyDataFetcher implements DataFetcher<Property> {

    @Autowired
    private PropertyService propertyService;

    @Override
    public Property get(DataFetchingEnvironment dataFetchingEnvironment) {
        Map args = dataFetchingEnvironment.getArguments();
        Property property = propertyService.findById((String) args.get("id"));
        return property;
    }
}
