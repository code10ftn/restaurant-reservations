package com.code10.isa.repository;

import com.code10.isa.model.user.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface WaiterRepository extends JpaRepository<Waiter, Long> {

    List<Waiter> findByRestaurantId(long id);

    Optional<Waiter> findById(long id);
}
