package com.code10.isa.service;

import com.code10.isa.controller.exception.BadRequestException;
import com.code10.isa.controller.exception.NotFoundException;
import com.code10.isa.model.Area;
import com.code10.isa.model.Shift;
import com.code10.isa.model.Table;
import com.code10.isa.model.user.Employee;
import com.code10.isa.repository.RestaurantRepository;
import com.code10.isa.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository, RestaurantRepository restaurantRepository) {
        this.shiftRepository = shiftRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Shift> findAll(long restaurantId) {
        return shiftRepository.findByRestaurantId(restaurantId);
    }

    public Shift findById(long id) {
        return shiftRepository.findById(id).orElseThrow(() -> new NotFoundException("Shift doesn't exist!"));
    }

    public Shift create(Shift newShift, Area area) {
        if (shiftRepository.exists(newShift.getId())) {
            throw new BadRequestException("Shift already exists!");
        }

        if (newShift.getStartTime().after(newShift.getEndTime())) {
            throw new BadRequestException("Start time must be before end time!");
        }

        if (area != null) {
            newShift.setArea(area);
        }

        newShift.setRestaurant(newShift.getEmployee().getRestaurant());
        return shiftRepository.save(newShift);
    }

    public Shift update(long id, Shift shift) {
        final Shift updatedShift = shiftRepository.findById(id).orElseThrow(() -> new NotFoundException("Shift not found!"));

        if (updatedShift.getId() != shift.getId()) {
            throw new BadRequestException("Wrong shift id!");
        }

        return shiftRepository.save(shift);
    }

    public Shift delete(long id) {
        final Shift shift = shiftRepository.findById(id).orElseThrow(() -> new NotFoundException("Shift doesn't exist!"));
        shiftRepository.delete(shift);
        return shift;
    }

    public Shift findCurrent(Date date, Employee currentEmployee) {
        final List<Shift> shifts =
                shiftRepository.findByRestaurantIdAndEmployeeId(currentEmployee.getRestaurant().getId(), currentEmployee.getId());

        final Calendar calNow = Calendar.getInstance();
        final Calendar calShiftStart = Calendar.getInstance();
        final Calendar calShiftEnd = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy.");

        calNow.setTime(date);

        for (Shift shift : shifts) {
            calShiftStart.setTime(shift.getStartTime());
            int currentDay = calNow.get(Calendar.DAY_OF_WEEK);
            int startDay = calShiftStart.get(Calendar.DAY_OF_WEEK);

            if (currentDay == 1 && startDay != 1) {
                startDay -= 7;  // sunday is edge case, move it to week before
            } else if (currentDay != 1 && startDay == 1) {
                startDay += 7;
            }

            calShiftStart.set(Calendar.DAY_OF_MONTH, calNow.get(Calendar.DAY_OF_MONTH) - currentDay + startDay);
            calShiftStart.set(Calendar.YEAR, calNow.get(Calendar.YEAR));
            calShiftStart.set(Calendar.MONTH, calNow.get(Calendar.MONTH));

            calShiftEnd.setTime(shift.getEndTime());
            int endDay = calShiftEnd.get(Calendar.DAY_OF_WEEK);
            if (currentDay == 1 && endDay != 1 && startDay <= 0) {
                endDay -= 7;
            } else if (currentDay != 1 && endDay == 1) {
                endDay += 7;
            }

            calShiftEnd.set(Calendar.DAY_OF_MONTH, calNow.get(Calendar.DAY_OF_MONTH) - currentDay + endDay);
            calShiftEnd.set(Calendar.YEAR, calNow.get(Calendar.YEAR));
            calShiftEnd.set(Calendar.MONTH, calNow.get(Calendar.MONTH));

            System.out.println("Shift: " + sdf.format(calShiftStart.getTime()) + " to " + sdf.format(calShiftEnd.getTime()));

            if (calShiftStart.getTime().before(calNow.getTime()) && calShiftEnd.getTime().after(calNow.getTime())) {
                return shift;
            }
        }
        throw new BadRequestException("Shift not in progress!");
    }

    public boolean isOrderInArea(List<Table> tables, Area area) {
        for (Table table : tables) {
            if (table.getArea() == area) {
                return true;
            }
        }
        return false;
    }
}
