package com.itech.networkPlan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //strategy generates key
    private Integer id;
    @NonNull
    private String name;
    private String location;

}
