package org.blackbird.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

// TODO: Sollte bei der @Table Annotation die "uniqueConstraints" verwendet werden?
@Entity
@Table(name = "Notification")
public class Notification extends PanacheEntity {
    @Column(name = "content")
    private String content;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private AbsenceType absenceType;

    @Version
    private int version;

    public String getContent() {
        return content;
    }

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

    public AbsenceType getAbsenceType() {
        return absenceType;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setAbsenceType(AbsenceType absenceType) {
        this.absenceType = absenceType;
    }
}
