package uk.ac.sussex.group6.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.sussex.group6.backend.Payloads.LongLatCoordinates;
import uk.ac.sussex.group6.backend.Services.PoliceService;

@RestController
@RequestMapping("/police")
@CrossOrigin(maxAge = 3600L, origins = "*")
public class PoliceReportController {

    @Autowired
    private PoliceService policeService;

    @GetMapping()
    public ResponseEntity<?> getPoliceReportsInArea(@RequestBody LongLatCoordinates longLatCoordinates) {
        return ResponseEntity.ok(policeService.generateData(longLatCoordinates));
    }

}
