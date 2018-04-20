package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.dto.ChangePasswordDto;
import com.code10.isa.model.user.User;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updatePassword(ChangePasswordDto changePasswordDto, User currentUser) {
        if (!currentUser.getPassword().equals(changePasswordDto.getOldPassword())) {
            throw new BadRequestException("Old passwords must match!");
        }

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
            throw new BadRequestException("New password wasn't confirmed!");
        }

        currentUser.setPassword(changePasswordDto.getNewPassword());
        currentUser.setVerified(true);
        return userRepository.save(currentUser);
    }
}
