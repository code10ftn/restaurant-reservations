package com.code10.isa.service;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.user.Employee;
import com.code10.isa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserRepository userRepository;

    private static final long EXISTING_NON_WAITER_ID = 1;

    private static final long NON_EXISTING_WAITER_ID = 100;

    @Test(expected = NotFoundException.class)
    public void updateWithNonExistentIdShouldThrowNotFoundException() {
        Employee employee = (Employee) userRepository.findById(2).get();
        employee.setId(NON_EXISTING_WAITER_ID);
        employeeService.update(NON_EXISTING_WAITER_ID, employee, employee);
    }

    @Test(expected = ClassCastException.class)
    public void updateWithNonEmployeeIdShouldThrowBadRequestException() {
        Employee employee = (Employee) userRepository.findById(2).get();
        employee.setId(EXISTING_NON_WAITER_ID);
        employeeService.update(EXISTING_NON_WAITER_ID, employee, employee);
    }
}
