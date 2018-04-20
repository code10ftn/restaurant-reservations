package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.*;
import com.code10.isa.model.dto.OrderDto;
import com.code10.isa.model.dto.UpdateOrderDto;
import com.code10.isa.model.dto.UpdateOrderItemDto;
import com.code10.isa.model.user.Waiter;
import com.code10.isa.repository.OrderItemRepository;
import com.code10.isa.repository.OrderRepository;
import com.code10.isa.repository.TableRepository;
import com.code10.isa.repository.WaiterRepository;
import com.code10.isa.util.TransactionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final TableRepository tableRepository;
    private final WaiterRepository waiterRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, TableRepository tableRepository, WaiterRepository waiterRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.tableRepository = tableRepository;
        this.waiterRepository = waiterRepository;
    }

    public List<Order> findAll() {
        final List<Order> orderList = orderRepository.findAll();

        for (Order order : orderList) {
            int prepared = 0;

            if (order.getOrderStatus() != OrderStatus.FINISHED) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    if (orderItem.getStatus() == OrderItemStatus.PREPARED) {
                        prepared++;
                    }
                }
                if (prepared == order.getOrderItems().size()) {
                    order.setOrderStatus(OrderStatus.PREPARED);
                    orderRepository.save(order);
                }
            }
        }

        return orderList;
    }

    public Order findById(long id) {
        return orderRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    public Order create(OrderDto orderDto, Waiter currentWaiter, Area area) {
        final Order order = orderDto.createOrder();
        tableRepository.findById(order.getTable().getId()).orElseThrow(() -> new BadRequestException("Table doesn't exist"));

        order.setWaiter(currentWaiter);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setArea(area);
        order.setAccepted(new Date());

        for (OrderItem item : order.getOrderItems()) {
            item.setOrder(order);
        }

        order.setRestaurant(currentWaiter.getRestaurant());
        return orderRepository.save(order);
    }

    @Transactional
    public Order update(long id, UpdateOrderDto updateOrderDto) {
        final Order order = orderRepository.findById(id).orElseThrow(() -> new BadRequestException("Order doesn't exist!"));

        TransactionsUtil.sleepIfTesting();

        for (UpdateOrderItemDto item : updateOrderDto.getOrderItemDtos()) {
            if (item.isAdding()) {
                item.getOrderItem().setStatus(OrderItemStatus.CREATED);
                order.getOrderItems().add(item.getOrderItem());
                item.getOrderItem().setOrder(order);
                order.setOrderStatus(OrderStatus.CREATED);
            } else {
                try {
                    final OrderItem orderItem = orderItemRepository.findById(item.getOrderItem().getId()).orElseThrow(() -> new NotFoundException("Order item doesn't exist!"));
                    if (orderItem.getStatus() == OrderItemStatus.CREATED) {
                        order.getOrderItems().remove(orderItem);
                    }
                } catch (Exception e) {

                }
            }
        }

        order.setReadyAtArrival(updateOrderDto.isReadyAtArrival());

        return orderRepository.save(order);
    }

    public Order create(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        final Order order = orderDto.createOrder();

        order.setOrderStatus(OrderStatus.CREATED);

        for (OrderItem item : order.getOrderItems()) {
            item.setOrder(order);
        }

        return orderRepository.save(order);
    }

    public Order setReservation(Order order, Reservation reservation) {
        order.setReservationStart(reservation.getDate());
        order.setReservation(reservation);
        return orderRepository.save(order);
    }

    public void remove(Order order) {
        orderRepository.delete(order);
    }

    public List<Order> findByRestaurant(long id) {
        return orderRepository.findByRestaurantId(id);
    }

    @Transactional
    public Order close(long id, long waiterId, Shift originalWaiterShift, Shift newWaiterShift) {
        final Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order doesn't exist!"));
        if (order.getOrderStatus() != OrderStatus.PREPARED) {
            throw new BadRequestException("Order can't be closed!");
        }

        TransactionsUtil.sleepIfTesting();

        final Waiter waiter = waiterRepository.findById(waiterId).orElseThrow(() -> new NotFoundException("Waiter doesn't exist!"));

        if (waiter.getId() != order.getWaiter().getId()) {
            if (workedLonger(originalWaiterShift, newWaiterShift, order.getAccepted()).getId() != originalWaiterShift.getId()) {
                order.setWaiter(waiter);
            }
        }

        order.setOrderStatus(OrderStatus.FINISHED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order accept(long id, Waiter waiter, Area area) {
        final Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order doesn't exist!"));

        TransactionsUtil.sleepIfTesting();

        if (order.getWaiter() != null && order.getWaiter().getId() != waiter.getId()) {
            throw new BadRequestException("Order is already accepted!");
        }

        order.setArea(area);
        order.setAccepted(new Date());
        order.setWaiter(waiter);
        return orderRepository.save(order);
    }

    private Shift workedLonger(Shift originalWaiterShift, Shift newWaiterShift, Date acceptedDate) {
        final Calendar calNow = Calendar.getInstance();
        final Calendar calShiftStartOriginal = Calendar.getInstance();
        final Calendar calShiftEndOriginal = Calendar.getInstance();
        final Calendar calShiftStartNew = Calendar.getInstance();
        final Calendar calShiftEndNew = Calendar.getInstance();

        calNow.setTime(new Date());

        // original waiter shift
        calShiftStartOriginal.setTime(originalWaiterShift.getStartTime());
        int currentDay = calNow.get(Calendar.DAY_OF_WEEK);
        int startDay = calShiftStartOriginal.get(Calendar.DAY_OF_WEEK);

        if (currentDay == 1 && startDay != 1) {
            startDay -= 7;  // sunday is edge case, move it to week before
        } else if (currentDay != 1 && startDay == 1) {
            startDay += 7;
        }

        calShiftStartOriginal.set(Calendar.DAY_OF_MONTH, calNow.get(Calendar.DAY_OF_MONTH) - currentDay + startDay);
        calShiftStartOriginal.set(Calendar.YEAR, calNow.get(Calendar.YEAR));
        calShiftStartOriginal.set(Calendar.MONTH, calNow.get(Calendar.MONTH));

        calShiftEndOriginal.setTime(originalWaiterShift.getEndTime());
        int endDay = calShiftEndOriginal.get(Calendar.DAY_OF_WEEK);
        if (currentDay == 1 && endDay != 1 && startDay <= 0) {
            endDay -= 7;
        } else if (currentDay != 1 && endDay == 1) {
            endDay += 7;
        }

        calShiftEndOriginal.set(Calendar.DAY_OF_MONTH, calNow.get(Calendar.DAY_OF_MONTH) - currentDay + endDay);
        calShiftEndOriginal.set(Calendar.YEAR, calNow.get(Calendar.YEAR));
        calShiftEndOriginal.set(Calendar.MONTH, calNow.get(Calendar.MONTH));

        if (calShiftEndOriginal.getTime().getTime() >= calNow.getTime().getTime()) {
            return originalWaiterShift;
        }

        // new waiter shift

        calShiftStartNew.setTime(newWaiterShift.getStartTime());
        currentDay = calNow.get(Calendar.DAY_OF_WEEK);
        startDay = calShiftStartNew.get(Calendar.DAY_OF_WEEK);

        if (currentDay == 1 && startDay != 1) {
            startDay -= 7;  // sunday is edge case, move it to week before
        } else if (currentDay != 1 && startDay == 1) {
            startDay += 7;
        }

        calShiftStartNew.set(Calendar.DAY_OF_MONTH, calNow.get(Calendar.DAY_OF_MONTH) - currentDay + startDay);
        calShiftStartNew.set(Calendar.YEAR, calNow.get(Calendar.YEAR));
        calShiftStartNew.set(Calendar.MONTH, calNow.get(Calendar.MONTH));

        calShiftEndNew.setTime(newWaiterShift.getEndTime());
        endDay = calShiftEndNew.get(Calendar.DAY_OF_WEEK);
        if (currentDay == 1 && endDay != 1 && startDay <= 0) {
            endDay -= 7;
        } else if (currentDay != 1 && endDay == 1) {
            endDay += 7;
        }

        calShiftEndNew.set(Calendar.DAY_OF_MONTH, calNow.get(Calendar.DAY_OF_MONTH) - currentDay + endDay);
        calShiftEndNew.set(Calendar.YEAR, calNow.get(Calendar.YEAR));
        calShiftEndNew.set(Calendar.MONTH, calNow.get(Calendar.MONTH));

        final Calendar acceptedCal = Calendar.getInstance();
        acceptedCal.setTime(acceptedDate);

        final long first = calShiftEndOriginal.getTime().getTime() - acceptedCal.getTime().getTime();
        final long second = calNow.getTime().getTime() - calShiftEndOriginal.getTime().getTime();
        if (second > first) {
            return newWaiterShift;
        }

        return originalWaiterShift;
    }
}
