package com.code10.isa.model.dto;

public abstract class RegisterDto {

    protected String email;

    protected String password;

    protected String passwordConfirmation;

    public RegisterDto() {
    }

    public RegisterDto(String email, String password, String passwordConfirmation) {
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public boolean fieldsPopulated() {
        return !("".equals(email) || "".equals(password) || "".equals(passwordConfirmation));
    }

    public boolean passwordsMatch() {
        return password.equals(passwordConfirmation);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
