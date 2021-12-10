package uk.ac.sussex.group6.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.sussex.group6.backend.Payloads.AddLocationRequest;
import uk.ac.sussex.group6.backend.Payloads.AllOnScreenRequest;
import uk.ac.sussex.group6.backend.Services.LocationService;
import uk.ac.sussex.group6.backend.Services.PropertyService;

@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/{postcode}")
    public ResponseEntity<?> getAllWhere(@PathVariable String postcode) {
        return ResponseEntity.ok(propertyService.findAllWherePostcodeContains(postcode));
    }

    @PostMapping()
    public ResponseEntity<?> getAllValuesFor(@RequestBody AllOnScreenRequest allOnScreenRequest) {
        return ResponseEntity.ok(propertyService.allOnScreen(allOnScreenRequest));
    }
}
