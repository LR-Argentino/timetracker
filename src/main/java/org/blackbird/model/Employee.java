package org.blackbird.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Employee")
// The PanacheEntity base class provides an id field with an automatically
// generated long value that will be mapped to a database sequence.
public class Employee extends PanacheEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @ManyToOne
    private Department department;

    @Version
    private int version;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }


    public Department getDepartment() {
        return department;
    }

    public int getVersion() {
        return version;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
