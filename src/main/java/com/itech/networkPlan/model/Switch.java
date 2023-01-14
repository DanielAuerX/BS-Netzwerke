package com.itech.networkPlan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data       // creates getters and setters etc
@Builder    // builds objects
@NoArgsConstructor
@AllArgsConstructor    // with builder
@Entity
public class Switch {

    @Id
    //@GeneratedValue(generator = "uuid2") //todo the generator doesnt work
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    //private String id; //string or uuid
    //@Column(name="id", columnDefinition = "VARCHAR(255)", nullable = false)
    //private String id = UUID.randomUUID().toString();
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

}
