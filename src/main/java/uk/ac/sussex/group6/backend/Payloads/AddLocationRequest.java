package uk.ac.sussex.group6.backend.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.sussex.group6.backend.Models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLocationRequest {

    private String userId;

    private String location_name;

    private double longitude;

    private double latitude;

    private String colour;

}
