package uk.ac.sussex.group6.backend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricePaidData {

    @Id
    private String id;

    private String transactionUniqueIdentifier;

    private Integer pricePaid;

    private Date dateOfTransfer;

    private PropertyAge propertyAge;

    private PropertyDuration propertyDuration;

    private PropertyPPDCategory propertyPPDCategory;

    private RecordStatus recordStatus = RecordStatus.ADDITION;

    public PricePaidData(String transactionUniqueIdentifier, Integer pricePaid, Date dateOfTransfer, PropertyAge propertyAge, PropertyDuration propertyDuration, PropertyPPDCategory propertyPPDCategory, RecordStatus recordStatus) {
        this.transactionUniqueIdentifier = transactionUniqueIdentifier;
        this.pricePaid = pricePaid;
        this.dateOfTransfer = dateOfTransfer;
        this.propertyAge = propertyAge;
        this.propertyDuration = propertyDuration;
        this.propertyPPDCategory = propertyPPDCategory;
        this.recordStatus = recordStatus;
    }

    public PricePaidData(String transactionUniqueIdentifier, Integer pricePaid, Date dateOfTransfer, PropertyAge propertyAge, PropertyDuration propertyDuration, PropertyPPDCategory propertyPPDCategory) {
        this.transactionUniqueIdentifier = transactionUniqueIdentifier;
        this.pricePaid = pricePaid;
        this.dateOfTransfer = dateOfTransfer;
        this.propertyAge = propertyAge;
        this.propertyDuration = propertyDuration;
        this.propertyPPDCategory = propertyPPDCategory;
    }
}
