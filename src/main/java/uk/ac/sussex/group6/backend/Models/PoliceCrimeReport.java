package uk.ac.sussex.group6.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliceCrimeReport {

    private String category;
    private String location_type;
    private String longitude;
    private String latitude;
    private String streetID;
    private String streetName;
    private String context;
    private String outcome_status;
    private String persistent_id;
    private Long id;
    private String location_subtype;
    private String month;

}
