package com.itech.networkPlan.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data       // creates getters and setters etc
@Builder    // builds objects
@NoArgsConstructor
@AllArgsConstructor    // with builder
@Entity
public class Port {

    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(optional = false)
    private Switch switchId;
    private String name;
    private String portMode;


}
