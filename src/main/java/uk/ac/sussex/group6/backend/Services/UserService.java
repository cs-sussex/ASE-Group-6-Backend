package uk.ac.sussex.group6.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Models.User;
import uk.ac.sussex.group6.backend.Payloads.ChangePasswordRequest;
import uk.ac.sussex.group6.backend.Payloads.SignupRequest;
import uk.ac.sussex.group6.backend.Payloads.UpdateUserRequest;
import uk.ac.sussex.group6.backend.Repositories.UserRepository;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;


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

    public void changePassword(ChangePasswordRequest changePasswordRequest, User user) {
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        user.setDateUpdated(new Date());
        userRepository.save(user);
    }
}
