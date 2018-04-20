package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.dto.GuestRegisterDto;
import com.code10.isa.model.dto.LoginDto;
import com.code10.isa.model.user.Guest;
import com.code10.isa.model.user.User;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.MailService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final MailService mailService;

    private final HttpServletRequest request;

    @Autowired
    public AuthController(AuthService authService, MailService mailService, HttpServletRequest request) {
        this.authService = authService;
        this.mailService = mailService;
        this.request = request;
    }

    @PostMapping("/signup")
    public ResponseEntity<Guest> signup(@RequestBody GuestRegisterDto registerDto) {
        if (authService.emailTaken(registerDto)) {
            throw new BadRequestException("Email taken!");
        }

        if (!registerDto.fieldsPopulated()) {
            throw new BadRequestException("Please enter all fields!");
        }

        if (!registerDto.passwordsMatch()) {
            throw new BadRequestException("Passwords not matching!");
        }

        final Guest guest = authService.createGuest(registerDto);
        mailService.sendVerificationMail(request.getRequestURL().toString(), guest.getVerificationCode(), guest.getEmail());
        return new ResponseEntity<>(guest, HttpStatus.CREATED);
    }

    @GetMapping("/signup/{verificationCode}")
    public ModelAndView verify(@PathVariable String verificationCode) {
        authService.verifyGuest(verificationCode);
        return new ModelAndView(new RedirectView("/", true));
    }

    @PostMapping("/signin")
    public ResponseEntity<User> signin(@RequestBody LoginDto loginDto) {
        final User user = authService.find(loginDto);

        if (!user.isVerified()) {
            return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
        }

        authService.setCurrentUser(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/signout")
    public ResponseEntity signout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<User> authenticate() {
        final User currentUser = authService.getCurrentUser();
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
}
