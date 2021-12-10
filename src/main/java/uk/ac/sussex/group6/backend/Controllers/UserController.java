package uk.ac.sussex.group6.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.sussex.group6.backend.Payloads.UpdateUserRequest;
import uk.ac.sussex.group6.backend.Security.CurrentUser;
import uk.ac.sussex.group6.backend.Security.UserDetailsImpl;
import uk.ac.sussex.group6.backend.Services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("update")
    public ResponseEntity<?> updateUserInformation(@CurrentUser UserDetailsImpl userDetails, @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(userDetails.getId(), updateUserRequest));
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteUser(@CurrentUser UserDetailsImpl userDetails) {
        return userService.deleteUser(userDetails.getId());
    }

}
