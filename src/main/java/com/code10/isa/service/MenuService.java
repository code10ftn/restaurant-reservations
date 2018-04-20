package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Menu;
import com.code10.isa.model.MenuItem;
import com.code10.isa.repository.MenuItemRepository;
import com.code10.isa.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu findById(long id) {
        return menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu not found!"));
    }

    public Menu findMenuByRestaurant(long id) {
        return menuRepository.findByRestaurantId(id).orElseThrow(BadRequestException::new);
    }

    public Menu update(Menu menu) {
        Menu originalMenu = findById(menu.getId());
        for (MenuItem menuItem : originalMenu.getMenuItems()) {
            menuItem.setMenu(null);
        }
        originalMenu.getMenuItems().clear();
        menuItemRepository.save(originalMenu.getMenuItems());
        for (MenuItem menuItem : menu.getMenuItems()) {
            menuItem.setMenu(originalMenu);
            originalMenu.getMenuItems().add(menuItem);
        }
        return menuRepository.save(originalMenu);
    }
}

