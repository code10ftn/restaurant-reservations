package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Shift;
import com.code10.isa.model.user.Employee;
import com.code10.isa.repository.RestaurantRepository;
import com.code10.isa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class ShiftServiceTest {

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private static final long EXISTING_EMPLOYEE_ID = 3;

    private static final long EXISTING_RESTAURANT_ID = 1;

    private static final long EXISTING_SHIFT_ID = 1;

    private static final long EXISTING_SHIFT_ID_FOR_DELETE = 2;

    private static final long NON_EXISTING_SHIFT_ID = 100;

    @Test
    public void createShiftWithValidDataShouldReturnCreatedShift() {
        final Shift shift = new Shift((Employee) userRepository.findById(EXISTING_EMPLOYEE_ID).get(),
                restaurantRepository.findById(EXISTING_RESTAURANT_ID).get(), new Date(), new Date());
        final Shift newShift = shiftService.create(shift, null);
        assertThat(newShift).isNotNull();
    }

    @Test(expected = BadRequestException.class)
    public void createShiftWithExistingShiftIdShouldThrowBadRequestException() {
        final Shift shift = new Shift((Employee) userRepository.findById(EXISTING_EMPLOYEE_ID).get(),
                restaurantRepository.findById(EXISTING_RESTAURANT_ID).get(), new Date(), new Date());
        shift.setId(EXISTING_SHIFT_ID);
        shiftService.create(shift, null);
    }

    @Test(expected = BadRequestException.class)
    public void createShiftWithBadDatesIdShouldThrowBadRequestException() {
        final Shift shift = new Shift((Employee) userRepository.findById(EXISTING_EMPLOYEE_ID).get(),
                restaurantRepository.findById(EXISTING_RESTAURANT_ID).get(), new Date(), new Date(1494170000));
        shiftService.create(shift, null);
    }

    @Test
    public void updateWithValidDataShouldReturnUpdatedShift() {
        final Shift shift = new Shift((Employee) userRepository.findById(EXISTING_EMPLOYEE_ID).get(),
                restaurantRepository.findById(EXISTING_RESTAURANT_ID).get(), new Date(), new Date());
        shift.setId(EXISTING_SHIFT_ID);
        final Shift updatedShift = shiftService.update(EXISTING_SHIFT_ID, shift);

        assertThat(updatedShift).isNotNull();
        assertThat(updatedShift.getStartTime()).isEqualTo(shift.getStartTime());
    }

    @Test(expected = NotFoundException.class)
    public void updateWithNonExistentShiftIdShouldThrowNotFoundException() {
        final Shift shift = new Shift((Employee) userRepository.findById(EXISTING_EMPLOYEE_ID).get(),
                restaurantRepository.findById(EXISTING_RESTAURANT_ID).get(), new Date(), new Date());
        shiftService.update(NON_EXISTING_SHIFT_ID, shift);
    }

    @Test(expected = BadRequestException.class)
    public void updateWithNonMatchingShiftIdShouldThrowNotFoundException() {
        final Shift shift = new Shift((Employee) userRepository.findById(EXISTING_EMPLOYEE_ID).get(),
                restaurantRepository.findById(EXISTING_RESTAURANT_ID).get(), new Date(), new Date());
        shift.setId(2);
        shiftService.update(EXISTING_SHIFT_ID, shift);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWithExistingShiftIdShouldPass() {
        shiftService.delete(EXISTING_SHIFT_ID_FOR_DELETE);
        shiftService.findById(EXISTING_SHIFT_ID_FOR_DELETE);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWithNonExistingShiftIdShouldThrowNotFoundException() {
        shiftService.delete(NON_EXISTING_SHIFT_ID);
    }
}
