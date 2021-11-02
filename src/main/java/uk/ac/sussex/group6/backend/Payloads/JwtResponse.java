package uk.ac.sussex.group6.backend.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String email;
    private String userID;

    public JwtResponse(String token, String email, String userID) {
        this.token = token;
        this.email = email;
        this.userID = userID;
    }
}