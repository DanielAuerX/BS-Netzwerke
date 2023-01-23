package com.itech.networkPlan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data       // creates getters and setters etc
@Builder    // builds objects
@NoArgsConstructor
@AllArgsConstructor    // with builder
@Entity
public class Host {

    @Id
    private String macId;
    private String name;
    @ManyToOne //optional: host might not be assigned to a network yet
    private Department department;
    private String ip;
    private String system;
   // private String defaultGateway; //foreign of router?

}
