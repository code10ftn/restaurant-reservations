package com.code10.isa.service;

import com.code10.isa.controller.exception.AuthorizationException;
import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.dto.GuestRegisterDto;
import com.code10.isa.model.dto.LoginDto;
import com.code10.isa.model.dto.RegisterDto;
import com.code10.isa.model.user.Guest;
import com.code10.isa.model.user.User;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean emailTaken(RegisterDto registerDto) {
        return userRepository.findByEmail(registerDto.getEmail()).isPresent();
    }

    public Guest createGuest(GuestRegisterDto guestRegisterDto) {
        final Guest guest = guestRegisterDto.createGuest();
        return userRepository.save(guest);
    }

    public User find(LoginDto loginDto) {
        return userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                .orElseThrow(() -> new NotFoundException("Wrong email/password!"));
    }

    public void setCurrentUser(User user) {
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        final Authentication authentication = new PreAuthenticatedAuthenticationToken(user.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public User getCurrentUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            return userRepository.findById((Long) auth.getPrincipal())
                    .orElseThrow(() -> new NotFoundException("User not found!"));
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    public Guest verifyGuest(String verificationCode) {
        final Guest guest = (Guest) userRepository
                .findByVerificationCode(verificationCode).orElseThrow(() -> new AuthorizationException("Invalid verification code!"));
        guest.setVerified(true);
        return userRepository.save(guest);
    }
}
