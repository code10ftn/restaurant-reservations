package com.code10.isa.service;

import com.code10.isa.model.Area;
import com.code10.isa.model.Table;
import com.code10.isa.repository.RestaurantRepository;
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
public class TableServiceTest {

    @Autowired
    private TableService tableService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private static final long EXISTING_RESTAURANT_ID = 1;

    @Test
    public void createTableWIthValidDataShouldReturnCreatedTable() {
        Table table = new Table(Area.INSIDE_NONSMOKING, 1, 1, 5);

        Table created = tableService.create(table, restaurantRepository.findById(EXISTING_RESTAURANT_ID).get());
        assertThat(created).isNotNull();

    }
}
