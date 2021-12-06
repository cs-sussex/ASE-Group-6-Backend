package uk.ac.sussex.group6.backend.Datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import uk.ac.sussex.group6.backend.Models.PricePaidData;

@Component
public class PricePaidDataDataFetcher implements DataFetcher<PricePaidData> {
    @Override
    public PricePaidData get(DataFetchingEnvironment dataFetchingEnvironment) {
        return null;
    }
}
