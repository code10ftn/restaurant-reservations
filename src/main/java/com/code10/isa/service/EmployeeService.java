package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.ForbiddenException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.user.Employee;
import com.code10.isa.model.user.User;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final UserRepository userRepository;

    @Autowired
    public EmployeeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Employee> findAll() {
        final List<User> users = userRepository.findAll();
        final List<Employee> employees = new ArrayList<>();

        for (User user : users) {
            if (user instanceof Employee) {
                final Employee employee = (Employee) user;
                employees.add(employee);
            }
        }
        return employees;
    }

    public Employee findById(long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee not found!"));
        if (user instanceof Employee) {
            return (Employee) user;
        }
        throw new NotFoundException("Employee not found!");
    }

    public List<Employee> findByRestaurant(long restaurantId) {
        final List<Employee> allEmployees = findAll();
        final List<Employee> employees = new ArrayList<>();

        for (Employee employee : allEmployees) {
            if (employee.getRestaurant().getId() == restaurantId) {
                employees.add(employee);
            }
        }
        return employees;
    }

    public User update(long id, Employee updated, Employee currentEmployee) {
        final Employee employee = (Employee) userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));

        if (currentEmployee.getId() != employee.getId()) {
            throw new ForbiddenException();
        }

        if (employee.getId() != updated.getId()) {
            throw new BadRequestException("Wrong user id!");
        }

        employee.update(updated);
        return userRepository.save(employee);
    }
}
