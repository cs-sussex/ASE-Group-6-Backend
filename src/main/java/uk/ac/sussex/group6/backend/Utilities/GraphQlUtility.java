package uk.ac.sussex.group6.backend.Utilities;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import uk.ac.sussex.group6.backend.Datafetchers.AllPropertiesDataFetcher;
import uk.ac.sussex.group6.backend.Datafetchers.PricePaidDataDataFetcher;
import uk.ac.sussex.group6.backend.Datafetchers.PropertyDataFetcher;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class GraphQlUtility {

    @Value("classpath:models.graphqls")
    private Resource resource;
    @Autowired
    private AllPropertiesDataFetcher allPropertiesDataFetcher;
    @Autowired
    private PropertyDataFetcher propertyDataFetcher;
    @Autowired
    private PricePaidDataDataFetcher pricePaidDataDataFetcher;

    @PostConstruct
    public GraphQL createGraphQLObject() throws IOException {
        File schemas = resource.getFile();
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemas);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, wiring);
        return GraphQL.newGraphQL(schema).build();

    }

    public RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(
                        "Query",
                        typeWiring -> typeWiring
                                .dataFetcher("properties", allPropertiesDataFetcher)
                                .dataFetcher("property", propertyDataFetcher)
                )
                .type(
                        "Property",
                        typeWiring -> typeWiring
                                .dataFetcher("PricePaidData", pricePaidDataDataFetcher)
                                .dataFetcher("postcode", allPropertiesDataFetcher))
                .build();
    }



}
