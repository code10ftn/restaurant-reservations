package com.code10.isa.controller;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.model.Order;
import com.code10.isa.model.OrderStatus;
import com.code10.isa.model.Rating;
import com.code10.isa.model.dto.*;
import com.code10.isa.model.user.Manager;
import com.code10.isa.model.user.Waiter;
import com.code10.isa.service.AuthService;
import com.code10.isa.service.OrderService;
import com.code10.isa.service.RatingService;
import com.code10.isa.service.WaiterService;
import com.code10.isa.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.util.*;

@Controller
@RequestMapping("/api/report")
public class ReportController {

    private final OrderService orderService;
    private final AuthService authService;
    private final WaiterService waiterService;
    private final RatingService ratingService;

    @Autowired
    public ReportController(OrderService orderService, AuthService authService, WaiterService waiterService, RatingService ratingService) {
        this.orderService = orderService;
        this.authService = authService;
        this.waiterService = waiterService;
        this.ratingService = ratingService;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("revenue/restaurant")
    public ResponseEntity getRestaurantRevenueForPeriod(@RequestParam("startDate") long startDateMil, @RequestParam("endDate") long endDateMil) {
        Manager manager = (Manager) authService.getCurrentUser();
        List<Order> orders = orderService.findByRestaurant(manager.getRestaurant().getId());

        Date startDate = new Date(startDateMil);
        Date endDate = new Date(endDateMil);

        if (endDate.before(startDate)) {
            throw new BadRequestException("End date is before start date.");
        }

        double revenue = 0;
        for (Order order : orders) {
            if (order.getCreated().after(startDate) && order.getCreated().before(endDate) && order.getOrderStatus() == OrderStatus.FINISHED) {
                revenue += order.getAmount();
            }
        }

        RestaurantRevenueDto restaurantRevenueDto = new RestaurantRevenueDto(startDate, endDate, revenue);

        return new ResponseEntity<>(restaurantRevenueDto, HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("revenue/waiters")
    public ResponseEntity getWaitersRevenues() {
        Manager manager = (Manager) authService.getCurrentUser();
        List<Order> orders = orderService.findByRestaurant(manager.getRestaurant().getId());

        List<Waiter> waiters = waiterService.findByRestaurant(manager.getRestaurant().getId());

        Map<Long, WaiterRevenueDto> revenues = new HashMap<>();

        for (Waiter waiter : waiters) {
            revenues.put(waiter.getId(), new WaiterRevenueDto(waiter, 0));
        }
        for (Order order : orders) {
            long id = order.getWaiter().getId();

            if (revenues.containsKey(id) && order.getOrderStatus() == OrderStatus.FINISHED) {
                WaiterRevenueDto revenueDto = revenues.get(id);
                revenueDto.setRevenue(revenueDto.getRevenue() + order.getAmount());
            }
        }
        List<WaiterRevenueDto> revenueDtoList = new ArrayList<>(revenues.values());
        return new ResponseEntity<>(revenueDtoList, HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("visits/daily")
    public ResponseEntity getDailyVisits(@RequestParam("date") long date) {
        Manager manager = (Manager) authService.getCurrentUser();
        List<Order> orders = orderService.findByRestaurant(manager.getRestaurant().getId());

        Calendar calDate = Calendar.getInstance();
        calDate.setTimeInMillis(date);

        Map<Integer, Integer> visitsByHours = new HashMap<>();

        for (int i = 0; i < 24; i++) {
            visitsByHours.put(i, 0);
        }
        Calendar calOrderDate = Calendar.getInstance();
        for (Order order : orders) {
            calOrderDate.setTime(order.getCreated());
            if (DateUtil.compareDay(calDate, calOrderDate) == 0) {
                int hour = calOrderDate.get(Calendar.HOUR_OF_DAY);
                int sum = visitsByHours.get(hour) + 1;
                visitsByHours.put(hour, sum);
            }
        }

        DailyVisitsDto visitsDto = new DailyVisitsDto();

        for (int i = 0; i < 24; i++) {
            visitsDto.getHours().add(i);
            int visits = visitsByHours.get(i);
            visitsDto.getVisits().add(visits);
        }

        return new ResponseEntity<>(visitsDto, HttpStatus.OK);

    }


    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("visits/weekly")
    public ResponseEntity getWeeklyVisits(@RequestParam("date") long date) {
        Manager manager = (Manager) authService.getCurrentUser();
        List<Order> orders = orderService.findByRestaurant(manager.getRestaurant().getId());

        Calendar calDate = Calendar.getInstance();
        calDate.setTimeInMillis(date);
        Calendar calStart = (Calendar) calDate.clone();
        calStart.add(Calendar.DAY_OF_WEEK,
                calStart.getFirstDayOfWeek() - calStart.get(Calendar.DAY_OF_WEEK));

        // and add six days to the end date
        Calendar calEnd = (Calendar) calStart.clone();
        calEnd.add(Calendar.DAY_OF_YEAR, 6);


        Map<DayOfWeek, Integer> visitsByDay = new HashMap<>();

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            visitsByDay.put(dayOfWeek, 0);
        }
        Calendar calOrderDate = Calendar.getInstance();
        for (Order order : orders) {
            calOrderDate.setTime(order.getCreated());
            if (DateUtil.compareDay(calStart, calOrderDate) <= 0 && DateUtil.compareDay(calEnd, calOrderDate) >= 0) {
                int day = calOrderDate.get(Calendar.DAY_OF_WEEK);
                day = (day + 5) % 7 + 1;
                int sum = visitsByDay.get(DayOfWeek.of(day)) + 1;
                visitsByDay.put(DayOfWeek.of(day), sum);
            }
        }

        WeeklyVisitsDto weeklyVisitsDto = new WeeklyVisitsDto();

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            weeklyVisitsDto.getDays().add(dayOfWeek);
            int visits = visitsByDay.get(dayOfWeek);
            weeklyVisitsDto.getVisits().add(visits);
        }

        return new ResponseEntity<>(weeklyVisitsDto, HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("ratings/restaurant/me")
    public ResponseEntity getRestaurantRating() {
        Manager manager = (Manager) authService.getCurrentUser();
        long id = manager.getRestaurant().getId();
        List<Rating> ratings = ratingService.findByRestaurant(id);

        double sum = 0;
        int count = 0;
        for (Rating rating : ratings) {
            sum += rating.getRestaurantRating();
            count++;
        }
        ReportRatingDto ratingDto = new ReportRatingDto(0, 0);
        if (count > 0) {
            ratingDto.setRating(sum / count);
            ratingDto.setCount(count);
        }

        return new ResponseEntity<>(ratingDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("ratings/waiter/{id}")
    public ResponseEntity getWaiterRating(@PathVariable long id) {
        List<Rating> ratings = ratingService.findByWaiter(id);

        double sum = 0;
        int count = 0;
        for (Rating rating : ratings) {
            sum += rating.getWaiterRating();
            count++;
        }
        ReportRatingDto ratingDto = new ReportRatingDto(0, 0);
        if (count > 0) {
            ratingDto.setRating(sum / count);
            ratingDto.setCount(count);
        }

        return new ResponseEntity<>(ratingDto, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("ratings/menu-item/{id}")
    public ResponseEntity getMenuItemRating(@PathVariable long id) {
        List<Rating> ratings = ratingService.findByMenuItem(id);

        double sum = 0;
        int count = 0;
        for (Rating rating : ratings) {
            sum += rating.getOrderRating();
            count++;
        }
        ReportRatingDto ratingDto = new ReportRatingDto(0, 0);
        if (count > 0) {
            ratingDto.setRating(sum / count);
            ratingDto.setCount(count);
        }

        return new ResponseEntity<>(ratingDto, HttpStatus.OK);
    }

}
