package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Sexes")
public class Sex {
    @Id
    private boolean id;

    private String name;
    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
