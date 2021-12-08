package uk.ac.sussex.group6.backend.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.sussex.group6.backend.Models.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLocationRequest {

    private String userId;

    private String location_name;

    private String colour;

    private String postcode;

    private Integer averagePrice;

}
