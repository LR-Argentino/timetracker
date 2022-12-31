package org.blackbird.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TimeBooking")
public class TimeBooking extends PanacheEntity {
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(optional = false)
    private Employee employee;

    @Version
    private int version;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getVersion() {
        return version;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
