package org.blackbird.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class Department extends PanacheEntity {
    @Column(name = "dep_name")
    private String depName;

    @Column(name = "short_name")
    private String shortName;

    @Version
    private int version;

    public String getDepName() {
        return depName;
    }

    public String getShortName() {
        return shortName;
    }

    public int getVersion() {
        return version;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
