package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Friendship;
import com.code10.isa.model.user.Guest;
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
public class FriendshipServiceTest {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserRepository userRepository;

    private static final long EXISTING_GUEST_ID_SENDER = 4;
    private static final long EXISTING_GUEST_ID_FRIEND = 6;
    private static final long EXISTING_GUEST_ID_RECEIVER = 7;

    @Test
    public void createFriendshipWithValidDataShouldReturnCreatedFriendship() {
        final Guest sender = (Guest) userRepository.findById(EXISTING_GUEST_ID_SENDER).get();
        final Friendship createdFriendship = friendshipService.create(sender, EXISTING_GUEST_ID_RECEIVER);
        assertThat(createdFriendship).isNotNull();
    }

    @Test(expected = BadRequestException.class)
    public void createFriendshipAlreadyFriendsShouldThrowBadRequest() {
        final Guest sender = (Guest) userRepository.findById(EXISTING_GUEST_ID_SENDER).get();
        friendshipService.create(sender, EXISTING_GUEST_ID_FRIEND);
    }

    @Test
    public void acceptExistingFriendshipShouldReturnUpdatedFriendship() {
        final Friendship updatedFriendship = friendshipService.accept(EXISTING_GUEST_ID_SENDER, EXISTING_GUEST_ID_FRIEND);
        assertThat(updatedFriendship).isNotNull();
        assertThat(updatedFriendship.isAccepted()).isTrue();
    }

    @Test(expected = NotFoundException.class)
    public void acceptNonExistingFriendshipShouldThrowNotFound() {
        final Friendship updatedFriendship = friendshipService.accept(EXISTING_GUEST_ID_SENDER, EXISTING_GUEST_ID_RECEIVER);
        assertThat(updatedFriendship).isNotNull();
        assertThat(updatedFriendship.isAccepted()).isTrue();
    }
}
