package uk.ac.sussex.group6.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class AveragePriceForPostcode {

    @Id
    private String id;

    private String postcode;

    private Double average;

    private Integer numberOfProperties;

    private Date DateChecked;

    public AveragePriceForPostcode(String postcode, Double average, Integer numberOfProperties, Date dateChecked) {
        this.postcode = postcode;
        this.average = average;
        this.numberOfProperties = numberOfProperties;
        DateChecked = dateChecked;
    }
}
