package com.code10.isa.repository;

import com.code10.isa.model.Area;
import com.code10.isa.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, Long> {

    Optional<Table> findById(long id);

    List<Table> findByRestaurantId(long id);

    List<Table> findByAreaAndRestaurantId(Area area, long restaurantId);
}
