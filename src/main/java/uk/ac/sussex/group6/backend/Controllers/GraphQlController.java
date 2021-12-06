package uk.ac.sussex.group6.backend.Controllers;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.sussex.group6.backend.Utilities.GraphQlUtility;

import java.io.IOException;

@RestController
@RequestMapping("query")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class GraphQlController {

    private GraphQL graphQL;
    private GraphQlUtility graphQlUtility;

    @Autowired
    public GraphQlController(GraphQlUtility graphQlUtility) throws IOException {
        this.graphQlUtility = graphQlUtility;
        this.graphQL = this.graphQlUtility.createGraphQLObject();
    }

    @PostMapping("/search")
    public ResponseEntity<?> queryDb(@RequestBody String query) {
        ExecutionResult executionResult = graphQL.execute(query);
        System.out.println(executionResult.getErrors());
        return ResponseEntity.ok(executionResult.getData());
    }


}
