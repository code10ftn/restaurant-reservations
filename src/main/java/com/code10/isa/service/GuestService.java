package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.ForbiddenException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.user.Guest;
import com.code10.isa.model.user.Role;
import com.code10.isa.model.user.User;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final UserRepository userRepository;

    @Autowired
    public GuestService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Guest> findAll() {
        return userRepository.findByRole(Role.GUEST).stream().map(user -> (Guest) user).collect(Collectors.toList());
    }

    public User update(long id, Guest updated, Guest currentGuest) {
        final Guest guest = (Guest) userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));

        if (currentGuest.getId() != guest.getId()) {
            throw new ForbiddenException();
        }

        if (guest.getId() != updated.getId()) {
            throw new BadRequestException("Wrong user id!");
        }

        guest.update(updated);
        return userRepository.save(guest);
    }
}
