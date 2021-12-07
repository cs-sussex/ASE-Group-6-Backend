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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/me")
    public User getMyself(@CurrentUser UserDetailsImpl userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @PutMapping("/user/{id}/changepassword")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
        try {
            authenticate(user.getEmail(), changePasswordRequest.getOldPassword());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.changePassword(changePasswordRequest, user);
        return ResponseEntity.ok().build();
    }

}
