package com.code10.isa.controller;

import com.code10.isa.model.dto.ChangePasswordDto;
import com.code10.isa.model.user.User;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable long id) {
        final User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updatePassword(@RequestBody ChangePasswordDto changePasswordDto, @PathVariable long id) {
        User currentUser;
        boolean loggedIn = true;

        try {
            currentUser = authService.getCurrentUser();
        } catch (Exception ignored) {
            // Unverified employee changing password on first login
            loggedIn = false;
            currentUser = userService.findById(id);
        }

        final User updatedUser = userService.updatePassword(changePasswordDto, currentUser);
        if (!loggedIn) {
            authService.setCurrentUser(updatedUser);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
