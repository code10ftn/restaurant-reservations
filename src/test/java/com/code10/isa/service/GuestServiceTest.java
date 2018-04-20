package com.code10.isa.service;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.user.Guest;
import com.code10.isa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class GuestServiceTest {

    @Autowired
    private GuestService guestService;

    @Autowired
    private UserRepository userRepository;

    private final long EXISING_NON_GUEST_ID = 1;

    private final long EXISTING_GUEST_ID = 4;

    private final long NON_EXISTING_GUEST_ID = 100;

    @Test(expected = NotFoundException.class)
    public void updateWithNonExistentIdShouldThrowNotFoundException() {
        final Guest guest = (Guest) userRepository.findById(EXISTING_GUEST_ID).get();
        guest.setId(NON_EXISTING_GUEST_ID);
        guestService.update(NON_EXISTING_GUEST_ID, guest, guest);
    }

    @Test(expected = ClassCastException.class)
    public void updateWithNonGuestIdShouldThrowClassCastException() {
        final Guest guest = (Guest) userRepository.findById(EXISTING_GUEST_ID).get();
        guest.setId(EXISING_NON_GUEST_ID);
        guestService.update(EXISING_NON_GUEST_ID, guest, guest);
    }
}
