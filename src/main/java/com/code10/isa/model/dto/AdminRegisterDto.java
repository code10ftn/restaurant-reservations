package com.code10.isa.model.dto;

import com.code10.isa.model.user.Admin;

public class AdminRegisterDto extends RegisterDto {

    public AdminRegisterDto() {
    }

    public AdminRegisterDto(String email, String password, String passwordConfirmation) {
        super(email, password, passwordConfirmation);
    }

    public Admin createAdmin() {
        return new Admin(email, password);
    }
}
