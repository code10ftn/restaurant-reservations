package com.code10.isa.repository;

import com.code10.isa.model.user.Guest;
import com.code10.isa.model.user.Role;
import com.code10.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    <E extends User> Optional<E> findById(long id);

    <E extends User> Optional<E> findByEmail(String email);

    <E extends User> Optional<E> findByEmailAndPassword(String email, String password);

    <E extends User> E save(E user);

    Optional<Guest> findByVerificationCode(String verificationCode);

    List<User> findByRole(Role role);
}
