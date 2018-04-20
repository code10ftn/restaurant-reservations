package com.code10.isa.service;

import com.code10.isa.model.Menu;
import com.code10.isa.model.MenuItem;
import com.code10.isa.model.MenuItemType;
import com.code10.isa.repository.MenuRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class MenuItemServiceTest {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuRepository menuRepository;

    private static final long EXISTING_MENU_ITEM_ID = 1;

    private static final long EXISTING_MENU_ID = 1;

    @Test
    public void createMenuItemtWithValidDataShouldReturnCreatedMenuItem() {
        MenuItem menuItem = new MenuItem(MenuItemType.FOOD, "test", "test", 500);
        Menu menu = menuRepository.findById(EXISTING_MENU_ID).get();

        MenuItem created = menuItemService.create(menuItem, menu);
        assertThat(created).isNotNull();
    }
}
