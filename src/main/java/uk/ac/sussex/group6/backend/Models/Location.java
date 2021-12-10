package uk.ac.sussex.group6.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    private String id;

    private User user;

    private String location_name;

    private String longitude;

    private String latitude;

    private String colour;

    private ArrayList<AveragePriceDate> averagePriceDates;

    public Location(User user, String location_name, String longitude, String latitude, Double averagePrice, Date averagePriceTakenOnDate, String colour) {
        this.user = user;
        this.location_name = location_name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.colour = colour;
        this.averagePriceDates = new ArrayList<>();
        this.averagePriceDates.add(new AveragePriceDate(averagePrice, averagePriceTakenOnDate));
    }

}
