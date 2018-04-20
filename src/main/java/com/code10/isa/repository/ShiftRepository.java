package com.code10.isa.repository;

import com.code10.isa.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    Optional<Shift> findById(Long id);

    List<Shift> findByRestaurantId(Long id);

    List<Shift> findByRestaurantIdAndEmployeeId(long restaurantId, long employeeId);
}
