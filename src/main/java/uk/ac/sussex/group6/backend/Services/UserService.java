package uk.ac.sussex.group6.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Exceptions.ResourceNotFoundException;
import uk.ac.sussex.group6.backend.Models.User;
import uk.ac.sussex.group6.backend.Payloads.*;
import uk.ac.sussex.group6.backend.Repositories.UserRepository;
import uk.ac.sussex.group6.backend.Security.JwtTokenUtil;
import uk.ac.sussex.group6.backend.Security.JwtUserDetailsService;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public JwtResponse signInUser(LoginRequest loginRequest) throws Exception {
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginRequest.getEmail());
        User user = getByEmail(loginRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(token,
                userDetails.getUsername(), user.getId());
    }


    public User createUser(SignupRequest signupRequest) {
        User u = new User();
        u.setFirstname(signupRequest.getFirstname());
        u.setLastname(signupRequest.getLastname());
        u.setDateCreated(new Date());
        u.setDateUpdated(new Date());
        u.setEmail(signupRequest.getEmail());
        u.setPassword(encoder.encode(signupRequest.getPassword()));
        return userRepository.save(u);
    }

    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(()->new BadRequestException("User not found"));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new BadRequestException("Could not find user by email"));
    }

    public User updateUser(String id, UpdateUserRequest updateUserRequest) {
        User u = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
        u.setFirstname(updateUserRequest.getFirstname());
        u.setLastname(updateUserRequest.getLastname());
        u.setDateUpdated(new Date());
        return userRepository.save(u);
    }

    public ResponseEntity<Void> changePassword(ChangePasswordRequest changePasswordRequest, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
        try {
            authenticate(user.getEmail(), changePasswordRequest.getOldPassword());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        user.setDateUpdated(new Date());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("Could not find user"));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<Void> deleteUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Could not find user"));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
