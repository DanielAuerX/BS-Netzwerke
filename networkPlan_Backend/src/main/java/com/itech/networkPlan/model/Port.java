package com.itech.networkPlan.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(PortId.class)
public class Port {

    @Id
    @JoinColumn(name = "switch_id")
    @ManyToOne(optional = false)
    private Switch switchId;
    @Id
    private String name;
    private String portMode;
    @OneToOne
    private Host host;
    private String vlan;


}
