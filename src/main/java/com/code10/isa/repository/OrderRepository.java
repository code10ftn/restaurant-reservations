package com.code10.isa.repository;

import com.code10.isa.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByWaiterId(long id);

    List<Order> findByRestaurantId(long id);

    Optional<Order> findById(long id);
}
