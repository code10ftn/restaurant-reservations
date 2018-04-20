package com.code10.isa.repository;

import com.code10.isa.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByChefId(long id);

    Optional<OrderItem> findById(long id);

    List<OrderItem> findByBartenderId(long id);
}
