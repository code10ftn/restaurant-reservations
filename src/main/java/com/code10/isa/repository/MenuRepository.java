package com.code10.isa.repository;

import com.code10.isa.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findById(long id);

    Optional<Menu> findByRestaurantId(long id);
}
