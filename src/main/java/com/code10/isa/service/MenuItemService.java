package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Menu;
import com.code10.isa.model.MenuItem;
import com.code10.isa.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem findById(long id) {
        return menuItemRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu item not found!"));
    }

    public MenuItem create(MenuItem menuItem, Menu menu) {
        menuItem.setMenu(menu);

        try {
            return menuItemRepository.save(menuItem);
        } catch (Exception e) {
            throw new BadRequestException("Data not valid");
        }
    }

    public MenuItem update(MenuItem updatedMenuItem) {
        MenuItem menuItem = findById(updatedMenuItem.getId());

        menuItem.update(updatedMenuItem);
        return menuItemRepository.save(menuItem);
    }

    public void delete(long id) {
        MenuItem menuItem = findById(id);
        menuItemRepository.delete(id);
    }
}
