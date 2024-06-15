package com.epam.rd.autocode.assessment.basics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Serializable {
    private long id;
    private long clientId;
    private long employeeId;
    private long vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal price;

    public Order() {
    }

    public Order(long id, long clientId, long employeeId, long vehicleId, LocalDateTime startTime, LocalDateTime endTime, BigDecimal price) {
        this.id = id;
        this.clientId = clientId;
        this.employeeId = employeeId;
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", employeeId=" + employeeId +
                ", vehicleId=" + vehicleId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getId() == order.getId() && getClientId() == order.getClientId() && getEmployeeId() == order.getEmployeeId() && getVehicleId() == order.getVehicleId() && Objects.equals(getStartTime(), order.getStartTime()) && Objects.equals(getEndTime(), order.getEndTime()) && Objects.equals(getPrice(), order.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClientId(), getEmployeeId(), getVehicleId(), getStartTime(), getEndTime(), getPrice());
    }

}
