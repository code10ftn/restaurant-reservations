package com.code10.isa.service;

import com.code10.isa.model.dto.ManagerRegisterDto;
import com.code10.isa.model.user.Manager;
import com.code10.isa.model.user.Role;
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
public class ManagerServiceTest {

    @Autowired
    private ManagerService managerService;

    @Test
    public void createShouldReturnSavedManager() {
        final ManagerRegisterDto testEntity = new ManagerRegisterDto("testManager@test", "test", "test", "Test", "Test", 1);

        final Manager createdTestEntity = managerService.create(testEntity);
        assertThat(createdTestEntity).isNotNull();

        assertThat(createdTestEntity.getRole()).isEqualTo(Role.MANAGER);
        assertThat(createdTestEntity.getEmail()).isEqualTo(testEntity.getEmail());
        assertThat(createdTestEntity.getPassword()).isEqualTo(testEntity.getPassword());
        assertThat(createdTestEntity.getFirstName()).isEqualTo(testEntity.getFirstName());
        assertThat(createdTestEntity.getLastName()).isEqualTo(testEntity.getLastName());

        assertThat(createdTestEntity.getRestaurant()).isNotNull();
    }
}
