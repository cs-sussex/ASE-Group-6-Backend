package uk.ac.sussex.group6.backend.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private Date dateCreated;

    private Date dateUpdated;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    private String password;

}