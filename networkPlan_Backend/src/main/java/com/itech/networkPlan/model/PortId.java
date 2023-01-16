package com.itech.networkPlan.model;

import java.io.Serializable;
import java.util.Objects;

public class PortId implements Serializable {
    private Integer switchId;  //Integer or Switch ??
    private String name;

    public PortId(Integer switchId, String name) {
        this.switchId = switchId;
        this.name = name;
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
