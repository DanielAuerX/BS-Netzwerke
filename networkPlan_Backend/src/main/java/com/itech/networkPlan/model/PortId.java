package com.itech.networkPlan.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PortId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "switch_id")
    private Switch switchId;
    private String name;

    public PortId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortId portId = (PortId) o;
        return Objects.equals(switchId, portId.switchId) && Objects.equals(name, portId.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(switchId, name);
    }
}
