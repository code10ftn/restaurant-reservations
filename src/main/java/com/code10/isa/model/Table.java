package com.code10.isa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@javax.persistence.Table(name = "table_table")
public class Table {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant")
    private Restaurant restaurant;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Area area;

    private double horizontalPosition;

    private double verticalPosition;

    private int seatCount;

    @Version
    private long version;

    private long changes;

    public Table() {
    }

    public Table(Area area, int horizontalPosition, int verticalPosition, int seatCount) {
        this.area = area;
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
        this.seatCount = seatCount;
    }

    public void update(Table table) {
        this.area = table.area;
        this.horizontalPosition = table.horizontalPosition;
        this.verticalPosition = table.verticalPosition;
        this.seatCount = table.seatCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public double getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(double horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public double getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(double verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getChanges() {
        return changes;
    }

    public void setChanges(long changes) {
        this.changes = changes;
    }
}
