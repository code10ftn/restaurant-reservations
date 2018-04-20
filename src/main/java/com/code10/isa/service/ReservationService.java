package com.code10.isa.service;

import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Order;
import com.code10.isa.model.Reservation;
import com.code10.isa.model.ReservationGuest;
import com.code10.isa.model.Table;
import com.code10.isa.repository.OrderRepository;
import com.code10.isa.repository.ReservationGuestRepository;
import com.code10.isa.repository.ReservationRepository;
import com.code10.isa.repository.TableRepository;
import com.code10.isa.util.TransactionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationGuestRepository reservationGuestRepository;
    private final OrderRepository orderRepository;
    private final TableRepository tableRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationGuestRepository reservationGuestRepository, OrderRepository orderRepository, TableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationGuestRepository = reservationGuestRepository;
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
    }

    public Reservation create(Reservation reservation, Order order) {
        order.setRestaurant(reservation.getRestaurant());
        orderRepository.save(order);
        reservation.setOrder(order);

        List<Table> reservationTables = new ArrayList<>();
        for (Table table : reservation.getTables()) {
            final Table reservationTable = tableRepository.findById(table.getId())
                    .orElseThrow(() -> new NotFoundException("Table not found!"));

            TransactionsUtil.sleepIfTesting();

            reservationTables.add(reservationTable);
            reservationTable.setChanges(reservationTable.getChanges() + 1);
        }
        reservation.getTables().clear();
        reservation.getTables().addAll(reservationTables);

        return reservationRepository.save(reservation);
    }

    public Reservation findById(long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found!"));
    }

    public List<Reservation> findUserReservations(long userId) {
        final List<ReservationGuest> reservationGuests = reservationGuestRepository.findByGuestId(userId);
        List<Reservation> reservations = reservationGuests.stream()
                .filter(ReservationGuest::isAccepted)
                .map(ReservationGuest::getReservation)
                .collect(Collectors.toList());

        return reservations;
    }

    public void remove(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    public List<Reservation> findByTable(long id) {
        return reservationRepository.findByTable(id);
    }
}
