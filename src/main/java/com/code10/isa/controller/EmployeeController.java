package com.code10.isa.controller;

import com.code10.isa.model.user.Employee;
import com.code10.isa.model.user.Manager;
import com.code10.isa.model.user.User;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthService authService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AuthService authService) {
        this.employeeService = employeeService;
        this.authService = authService;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity findAll(@PathVariable long restaurantId) {
        final List<Employee> employeeList = employeeService.findByRestaurant(restaurantId);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping
    public ResponseEntity findRestaurantEmployees() {
        final Manager manager = (Manager) authService.getCurrentUser();
        final List<Employee> employeeList = employeeService.findByRestaurant(manager.getRestaurant().getId());
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Employee employee = employeeService.findById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('WAITER', 'BARTENDER', 'CHEF')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody Employee employee) {
        final Employee currentEmployee = (Employee) authService.getCurrentUser();
        final User updatedEmployee = employeeService.update(id, employee, currentEmployee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
}
