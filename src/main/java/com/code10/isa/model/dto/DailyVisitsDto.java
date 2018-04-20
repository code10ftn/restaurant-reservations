package com.code10.isa.model.dto;

import java.util.ArrayList;
import java.util.List;

public class DailyVisitsDto {

    private List<Integer> hours;

    private List<Integer> visits;

    public DailyVisitsDto() {
        this.hours = new ArrayList<>();
        this.visits = new ArrayList<>();
    }

    public DailyVisitsDto(List<Integer> hours, List<Integer> visits) {
        this.hours = hours;
        this.visits = visits;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours = hours;
    }

    public List<Integer> getVisits() {
        return visits;
    }

    public void setVisits(List<Integer> visits) {
        this.visits = visits;
    }
}
