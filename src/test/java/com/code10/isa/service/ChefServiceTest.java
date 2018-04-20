package com.code10.isa.service;

import com.code10.isa.model.dto.EmployeeRegisterDto;
import com.code10.isa.model.user.Chef;
import com.code10.isa.model.user.Role;
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
public class ChefServiceTest {

    @Autowired
    private ChefService chefService;

    @Autowired
    private RestaurantService restaurantService;

    private static final long EXISTING_RESTAURANT_ID = 1;

    @Test
    public void createShouldReturnSavedChef() {
        final EmployeeRegisterDto testEntity = new EmployeeRegisterDto("testChef@test", "test",
                "test", "First", "Last", new Date(), "XL", 45);

        final Chef createdTestEntity = chefService.create(testEntity, restaurantService.findById(EXISTING_RESTAURANT_ID));
        assertThat(createdTestEntity).isNotNull();

        assertThat(createdTestEntity.getRole()).isEqualTo(Role.CHEF);
        assertThat(createdTestEntity.getEmail()).isEqualTo(testEntity.getEmail());
        assertThat(createdTestEntity.getPassword()).isEqualTo(testEntity.getPassword());
        assertThat(createdTestEntity.getBirthDate()).isEqualTo(testEntity.getBirthDate());
        assertThat(createdTestEntity.getClothingSize()).isEqualTo(testEntity.getClothingSize());
        assertThat(createdTestEntity.getShoeSize()).isEqualTo(testEntity.getShoeSize());

    }
}
