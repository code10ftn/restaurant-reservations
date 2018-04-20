package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.Area;
import com.code10.isa.model.Reservation;
import com.code10.isa.model.Restaurant;
import com.code10.isa.model.Table;
import com.code10.isa.model.dto.TableDto;
import com.code10.isa.repository.ReservationRepository;
import com.code10.isa.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.code10.isa.util.DateUtil.addHoursToDate;

@Service
public class TableService {

    private final TableRepository tableRepository;

    private final ReservationRepository reservationRepository;

    @Autowired
    public TableService(TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Table> findByRestaurant(Restaurant restaurant) {
        return tableRepository.findByRestaurantId(restaurant.getId());
    }

    public List<TableDto> findAvailableByRestaurant(Restaurant restaurant, Date reservationStart, int reservationLength) {
        final List<Reservation> reservations = reservationRepository.findByRestaurantId(restaurant.getId());
        final List<TableDto> tables = tableRepository.findByRestaurantId(restaurant.getId())
                .stream().map(TableDto::new).collect(Collectors.toList());
        final Date reservationEnd = addHoursToDate(reservationStart, reservationLength);

        for (Reservation other : reservations) {
            final Date otherReservationEnd = addHoursToDate(other.getDate(), other.getHoursLength());
            if ((other.getDate().before(reservationStart) && otherReservationEnd.after(reservationStart)) ||
                    other.getDate().before(reservationEnd) && otherReservationEnd.after(reservationEnd)) {
                for (Table reservedTable : other.getTables()) {
                    tables.stream().filter(table -> reservedTable.getId() == table.getId()).forEach(table -> {
                        table.setAvailable(false);
                    });
                }
            }
        }
        return tables;
    }

    public Table findById(long id) {
        return tableRepository.findById(id).orElseThrow(() -> new BadRequestException("Table not found!"));
    }

    public List<TableDto> findByArea(Area area, Restaurant restaurant) {
        final List<TableDto> tables = tableRepository.findByAreaAndRestaurantId(area, restaurant.getId()).stream().map(TableDto::new).collect(Collectors.toList());
        return tables;
    }

    public Table update(Table table) {
        Table t = findById(table.getId());
        t.update(table);

        return tableRepository.save(t);
    }

    public Table create(Table table, Restaurant restaurant) {
        table.setRestaurant(restaurant);
        return tableRepository.save(table);
    }

    public void delete(long id) {
        tableRepository.delete(id);
    }
}
