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
public class User {

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private Date dateCreated;

    private Date dateUpdated;

    private String email;

    private String password;


}