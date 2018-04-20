package com.code10.isa.service;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.user.User;
import com.code10.isa.repository.UserRepository;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final long EXISTING_USER_ID = 1;

    private static final long NON_EXISTING_USER_ID = 100;

    @Test
    public void findWithExistingIdShouldReturnUser() {
        final User user = userService.findById(EXISTING_USER_ID);
        assertThat(user).isNotNull();
    }

    @Test(expected = NotFoundException.class)
    public void findWithNonExistentIdShouldThrowNotFoundException() {
        userService.findById(NON_EXISTING_USER_ID);
    }
}
