package uk.ac.sussex.group6.backend.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

    private String email;
    private String password;

}
