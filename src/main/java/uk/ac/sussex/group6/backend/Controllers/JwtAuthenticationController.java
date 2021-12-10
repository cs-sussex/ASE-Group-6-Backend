package uk.ac.sussex.group6.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Exceptions.ResourceNotFoundException;
import uk.ac.sussex.group6.backend.Models.User;
import uk.ac.sussex.group6.backend.Payloads.ChangePasswordRequest;
import uk.ac.sussex.group6.backend.Payloads.JwtResponse;
import uk.ac.sussex.group6.backend.Payloads.LoginRequest;
import uk.ac.sussex.group6.backend.Payloads.SignupRequest;
import uk.ac.sussex.group6.backend.Repositories.UserRepository;
import uk.ac.sussex.group6.backend.Security.CurrentUser;
import uk.ac.sussex.group6.backend.Security.JwtTokenUtil;
import uk.ac.sussex.group6.backend.Security.JwtUserDetailsService;
import uk.ac.sussex.group6.backend.Security.UserDetailsImpl;
import uk.ac.sussex.group6.backend.Services.UserService;

import java.nio.file.attribute.UserPrincipal;

/**
 * JWT Entry controller
 */
@RestController
@RequestMapping("/auth/")
@CrossOrigin
public class JwtAuthenticationController {


    @Autowired
    private UserService userService;


    @PostMapping("signin")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(userService.signInUser(authenticationRequest));
    }

    @PostMapping("signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.createUser(signupRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyself(@CurrentUser UserDetailsImpl userPrincipal) {
        return ResponseEntity.ok(userService.getById(userPrincipal.getId()));
    }

    @PutMapping("/user/changepassword")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,@CurrentUser UserDetailsImpl userDetails) {
        return userService.changePassword(changePasswordRequest, userDetails.getId());

    }

}
