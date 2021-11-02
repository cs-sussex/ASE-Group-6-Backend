package uk.ac.sussex.group6.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uk.ac.sussex.group6.backend.Models.User;
import uk.ac.sussex.group6.backend.Payloads.JwtResponse;
import uk.ac.sussex.group6.backend.Payloads.LoginRequest;
import uk.ac.sussex.group6.backend.Payloads.SignupRequest;
import uk.ac.sussex.group6.backend.Repositories.UserRepository;
import uk.ac.sussex.group6.backend.Security.JwtTokenUtil;
import uk.ac.sussex.group6.backend.Security.JwtUserDetailsService;
import uk.ac.sussex.group6.backend.Services.UserService;

/**
 * JWT Entry controller
 */
@RestController
@RequestMapping("/auth/")
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Working");
    }


    @PostMapping("signin")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        User user = userService.getByEmail(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token,
                userDetails.getUsername(), user.getId()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.createUser(signupRequest));
    }
}
