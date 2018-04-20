package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Friendship;
import com.code10.isa.model.user.Guest;
import com.code10.isa.model.user.Role;
import com.code10.isa.repository.FriendshipRepository;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    private FriendshipRepository friendshipRepository;

    private UserRepository userRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public List<Guest> findNonFriends(Guest guest) {
        List<Guest> guests = userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.GUEST)
                .map(user -> (Guest) user).collect(Collectors.toList());

        final List<Friendship> friendships = friendshipRepository.findBySenderIdOrReceiverId(guest.getId(), guest.getId());
        for (Friendship friendship : friendships) {
            if (guest.getId() == friendship.getSender().getId()) {
                guests.remove(friendship.getReceiver());
            } else {
                guests.remove(friendship.getSender());
            }
        }
        guests.remove(guest);

        return guests;
    }

    public List<Guest> findFriends(Guest guest) {
        final List<Friendship> friendships = friendshipRepository.findBySenderIdOrReceiverId(guest.getId(), guest.getId());
        return friendships.stream()
                .filter(Friendship::isAccepted)
                .map(fs -> guest.getId() == fs.getSender().getId() ? fs.getReceiver() : fs.getSender())
                .collect(Collectors.toList());
    }

    public List<Guest> findPending(Guest guest) {
        final List<Friendship> friendships = friendshipRepository.findBySenderIdOrReceiverId(guest.getId(), guest.getId());
        return friendships.stream()
                .filter(fs -> !fs.isAccepted() && guest.getId() == fs.getSender().getId())
                .map(Friendship::getReceiver)
                .collect(Collectors.toList());
    }

    public List<Guest> findRequests(Guest guest) {
        final List<Friendship> friendships = friendshipRepository.findBySenderIdOrReceiverId(guest.getId(), guest.getId());
        return friendships.stream()
                .filter(fs -> !fs.isAccepted() && guest.getId() == fs.getReceiver().getId())
                .map(Friendship::getSender)
                .collect(Collectors.toList());
    }

    public Friendship create(Guest sender, long receiverId) {
        final Guest receiver = (Guest) userRepository.findById(receiverId).orElseThrow(() -> new NotFoundException("User not found!"));
        if (friendshipRepository.findBySenderIdAndReceiverId(sender.getId(), receiver.getId()).isPresent() ||
                friendshipRepository.findBySenderIdAndReceiverId(receiver.getId(), sender.getId()).isPresent()) {
            throw new BadRequestException("Already friends!");
        }
        final Friendship friendship = new Friendship(sender, receiver);
        return friendshipRepository.save(friendship);
    }

    public Friendship accept(long senderId, long receiverId) {
        final Friendship friendship = friendshipRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new NotFoundException("Friendship not found!"));
        friendship.setAccepted(true);
        return friendshipRepository.save(friendship);
    }

    public void remove(long senderId, long receiverId) {
        Friendship friendship;
        if (friendshipRepository.findBySenderIdAndReceiverId(senderId, receiverId).isPresent()) {
            friendship = friendshipRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                    .orElseThrow(() -> new NotFoundException("Friendship not found!"));
        } else {
            friendship = friendshipRepository.findBySenderIdAndReceiverId(receiverId, senderId)
                    .orElseThrow(() -> new NotFoundException("Friendship not found!"));
        }
        friendshipRepository.delete(friendship);
    }
}
