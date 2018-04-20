package com.code10.isa.repository;

import com.code10.isa.model.user.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByRestaurantId(long id);
}
