package uk.ac.sussex.group6.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    private String id;

    private User user;

    private String location_name;

    private double longitude;

    private double latitude;

    private String colour;

    public Location(User user, String location_name, double longitude, double latitude, String colour) {
        this.user = user;
        this.location_name = location_name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.colour = colour;
    }

}
