package com.code10.isa.model.dto;


import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class WeeklyVisitsDto {

    private List<DayOfWeek> days;

    private List<Integer> visits;

    public WeeklyVisitsDto() {
        this.days = new ArrayList<>();
        this.visits = new ArrayList<>();
    }

    public WeeklyVisitsDto(List<DayOfWeek> days, List<Integer> visits) {
        this.days = days;
        this.visits = visits;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    public void setDays(List<DayOfWeek> days) {
        this.days = days;
    }

    public List<Integer> getVisits() {
        return visits;
    }

    public void setVisits(List<Integer> visits) {
        this.visits = visits;
    }
}
