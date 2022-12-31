package org.blackbird.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "AbsenceType")
public class AbsenceType extends PanacheEntity {
    @Column(name = "name")
    private String name;

    @Version
    private int version;

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
