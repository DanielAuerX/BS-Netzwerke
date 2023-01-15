package com.itech.networkPlan.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data       // creates getters and setters etc
@Builder    // builds objects
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Port {

//    @Id
//    //@GeneratedValue//(generator = "uuid2") //todo the generator doesnt work
//    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
//    //@UuidGenerator
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    private UUID id;
    @Id
    @JoinColumn(name = "switch_id")
    @ManyToOne(optional = false)
    private Switch switchId;
    @Id
    private String name;
    private String portMode;
    @OneToOne
    private Host host;


}
