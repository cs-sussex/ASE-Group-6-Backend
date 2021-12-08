package uk.ac.sussex.group6.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Property {

    @Id
    private String id;

    private String paon;

    private String saon;

    private String postcode;

    private PropertyType propertyType;

    private String street;

    private String locality;

    private String town;

    private String district;

    private String county;

    @DBRef
    private ArrayList<PricePaidData> pricePaidData = new ArrayList<>();

    public Property(String paon, String saon, String postcode, PropertyType propertyType, String street, String locality, String town, String district, String county) {
        this.paon = paon;
        this.saon = saon;
        this.postcode = postcode;
        this.propertyType = propertyType;
        this.street = street;
        this.locality = locality;
        this.town = town;
        this.district = district;
        this.county = county;
    }

    public void addToPricePaidDataArrayList(PricePaidData PPD) {
        pricePaidData.add(PPD);
    }
}
