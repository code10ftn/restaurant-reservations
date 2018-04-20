package com.code10.isa.controller;

import com.code10.isa.model.Menu;
import com.code10.isa.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = menuService.findAll();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Menu> getMenuByRestaurant(@PathVariable long id) {
        final Menu menu = menuService.findMenuByRestaurant(id);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Menu> update(@PathVariable long id, @RequestBody Menu menu) {
        final Menu newMenu = menuService.update(menu);
        return new ResponseEntity<>(newMenu, HttpStatus.OK);
    }
}
