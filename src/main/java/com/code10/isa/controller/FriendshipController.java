package com.code10.isa.controller;

import com.code10.isa.model.Friendship;
import com.code10.isa.model.Notification;
import com.code10.isa.model.user.Guest;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.FriendshipService;
import com.code10.isa.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.code10.isa.util.NotificationConstants.FRIEND_REQUEST_ACCEPTED;
import static com.code10.isa.util.NotificationConstants.FRIEND_REQUEST_SENT;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    private FriendshipService friendshipService;

    private AuthService authService;

    private NotificationService notificationService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService, AuthService authService, NotificationService notificationService) {
        this.friendshipService = friendshipService;
        this.authService = authService;
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping
    public ResponseEntity create(@RequestBody Guest receiver) {
        final Guest sender = (Guest) authService.getCurrentUser();
        final Friendship friendship = friendshipService.create(sender, receiver.getId());

        final Notification notification = new Notification(sender, String.valueOf(receiver.getId()), FRIEND_REQUEST_SENT);
        notificationService.saveAndSendNotification(notification);

        return new ResponseEntity<>(friendship, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PutMapping("/{senderId}")
    public ResponseEntity accept(@PathVariable long senderId) {
        final Guest receiver = (Guest) authService.getCurrentUser();
        final Friendship updatedFriendship = friendshipService.accept(senderId, receiver.getId());

        final Notification notification = new Notification(receiver, String.valueOf(senderId), FRIEND_REQUEST_ACCEPTED);
        notificationService.saveAndSendNotification(notification);

        return new ResponseEntity<>(updatedFriendship, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @DeleteMapping("/{senderId}")
    public ResponseEntity remove(@PathVariable long senderId) {
        final Guest receiver = (Guest) authService.getCurrentUser();
        friendshipService.remove(senderId, receiver.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
