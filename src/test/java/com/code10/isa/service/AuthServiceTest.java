package com.code10.isa.service;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.dto.GuestRegisterDto;
import com.code10.isa.model.dto.LoginDto;
import com.code10.isa.model.dto.RegisterDto;
import com.code10.isa.model.user.Guest;
import com.code10.isa.model.user.Role;
import com.code10.isa.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    public void emailTakenWithAvailableEmailShouldReturnFalse() {
        final RegisterDto registerDto = new GuestRegisterDto(
                "test@test", "testPassword", "testPassword", "testFirstName", "testLastName");
        final boolean emailTaken = authService.emailTaken(registerDto);

        assertThat(emailTaken).isEqualTo(false);
    }

    @Test
    public void emailTakenWithUnavailableEmailShouldReturnTrue() {
        final RegisterDto registerDto =
                new GuestRegisterDto("admin@admin", "testPassword", "testPassword", "testFirstName", "testLastName");
        final boolean emailTaken = authService.emailTaken(registerDto);

        assertThat(emailTaken).isEqualTo(true);
    }

    @Test
    public void createGuestShouldReturnCreatedGuest() {
        final GuestRegisterDto guestRegisterDto =
                new GuestRegisterDto("test@test", "testPassword", "testPassword", "testFirstName", "testLastName");
        final Guest createdGuest = authService.createGuest(guestRegisterDto);

        assertThat(createdGuest).isNotNull();
        assertThat(createdGuest.getRole()).isEqualTo(Role.GUEST);
        assertThat(createdGuest.getEmail()).isEqualTo(guestRegisterDto.getEmail());
        assertThat(createdGuest.getPassword()).isEqualTo(guestRegisterDto.getPassword());
        assertThat(createdGuest.getFirstName()).isEqualTo(guestRegisterDto.getFirstName());
        assertThat(createdGuest.getLastName()).isEqualTo(guestRegisterDto.getLastName());
    }

    @Test
    public void findWithExistingUserShouldReturnUser() {
        final LoginDto loginDto = new LoginDto("admin@admin", "admin");
        final User foundUser = authService.find(loginDto);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(loginDto.getEmail());
        assertThat(foundUser.getPassword()).isEqualTo(loginDto.getPassword());
    }

    @Test(expected = NotFoundException.class)
    public void findWithNonexistentUserShouldThrowException() {
        final LoginDto loginDto = new LoginDto("test@test", "testPassword");
        authService.find(loginDto);
    }
}
