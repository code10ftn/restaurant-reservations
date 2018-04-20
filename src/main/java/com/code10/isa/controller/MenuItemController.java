package com.code10.isa.controller;

import com.code10.isa.model.Menu;
import com.code10.isa.model.MenuItem;
import com.code10.isa.service.MenuItemService;
import com.code10.isa.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menuItems")
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final MenuService menuService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService, MenuService menuService) {
        this.menuItemService = menuItemService;
        this.menuService = menuService;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity create(@RequestBody MenuItem menuItem) {
        long menuId = menuItem.getMenu().getId();
        Menu menu = menuService.findById(menuId);
        MenuItem createdMenItem = menuItemService.create(menuItem, menu);
        return new ResponseEntity<>(createdMenItem, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody MenuItem menuItem) {
        menuItem.setId(id);
        MenuItem updatedMenuItem = menuItemService.update(menuItem);
        return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        menuItemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
