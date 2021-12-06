package uk.ac.sussex.group6.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.sussex.group6.backend.Payloads.UpdateUserRequest;
import uk.ac.sussex.group6.backend.Services.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("{id}/update")
    public ResponseEntity<?> updateUserInformation(@PathVariable String id, @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserRequest));
    }

}
