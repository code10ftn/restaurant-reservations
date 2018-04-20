package com.code10.isa.service;

import com.code10.isa.model.dto.AdminRegisterDto;
import com.code10.isa.model.user.Admin;
import com.code10.isa.model.user.Role;
import com.code10.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Admin create(AdminRegisterDto registerDto) {
        final Admin admin = registerDto.createAdmin();
        admin.setVerified(true);
        return userRepository.save(admin);
    }


    public List<Admin> findAll() {
        return userRepository.findByRole(Role.ADMIN).stream().map(user -> (Admin) user).collect(Collectors.toList());
    }
}
