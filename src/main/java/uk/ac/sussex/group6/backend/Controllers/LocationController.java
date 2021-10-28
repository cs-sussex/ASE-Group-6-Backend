package uk.ac.sussex.group6.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.sussex.group6.backend.Payloads.AddLocationRequest;
import uk.ac.sussex.group6.backend.Services.LocationService;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;
    @GetMapping("/all")
    public ResponseEntity<?> getLocation() {
        return ResponseEntity.ok(locationService.getAll());
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findLocation(@PathVariable String id) {
        return ResponseEntity.ok(locationService.find(id));
    }
    @PostMapping("/add")
    public ResponseEntity<?> addLocation(@RequestBody AddLocationRequest addLocationRequest) {
        return ResponseEntity.ok(locationService.create(addLocationRequest));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable String id) {
        locationService.delete(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/{loc_id}")
    public ResponseEntity<?> updateLocation(@RequestBody AddLocationRequest addLocationRequest, @PathVariable String loc_id) {
        return ResponseEntity.ok(locationService.update(addLocationRequest, loc_id));
    }
    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<?> findByUser(@PathVariable String id) {
        return ResponseEntity.ok(locationService.findByUserId(id));
    }
}
