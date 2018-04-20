package com.code10.isa.controller;

import com.code10.isa.model.user.Guest;
import com.code10.isa.model.user.User;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.FriendshipService;
import com.code10.isa.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private GuestService guestService;

    private FriendshipService friendshipService;

    private AuthService authService;

    @Autowired
    public GuestController(GuestService guestService, FriendshipService friendshipService, AuthService authService) {
        this.guestService = guestService;
        this.friendshipService = friendshipService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity findALl() {
        List<Guest> guests = guestService.findAll();
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody Guest guest) {
        final Guest currentGuest = (Guest) authService.getCurrentUser();
        final User updatedGuest = guestService.update(id, guest, currentGuest);
        return new ResponseEntity<>(updatedGuest, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/nonFriends")
    public ResponseEntity findNonFriends() {
        final Guest currentGuest = (Guest) authService.getCurrentUser();
        final List<Guest> friends = friendshipService.findNonFriends(currentGuest);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/friends")
    public ResponseEntity findFriends() {
        final Guest currentGuest = (Guest) authService.getCurrentUser();
        final List<Guest> friends = friendshipService.findFriends(currentGuest);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/pending")
    public ResponseEntity findPending() {
        final Guest currentGuest = (Guest) authService.getCurrentUser();
        final List<Guest> pending = friendshipService.findPending(currentGuest);
        return new ResponseEntity<>(pending, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/requests")
    public ResponseEntity findRequests() {
        final Guest currentGuest = (Guest) authService.getCurrentUser();
        final List<Guest> pending = friendshipService.findRequests(currentGuest);
        return new ResponseEntity<>(pending, HttpStatus.OK);
    }
}
