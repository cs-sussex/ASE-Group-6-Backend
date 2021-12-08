package uk.ac.sussex.group6.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvRecord {

    private String paon;

    private String saon;

    private String postcode;

    private PropertyType propertyType;

    private String street;

    private String locality;

    private String town;

    private String district;

    private String county;

    private String transactionUniqueIdentifier;

    private Integer pricePaid;

    private Date dateOfTransfer;

    private PropertyAge propertyAge;

    private PropertyDuration propertyDuration;

    private PropertyPPDCategory propertyPPDCategory;

    private RecordStatus recordStatus;
}
