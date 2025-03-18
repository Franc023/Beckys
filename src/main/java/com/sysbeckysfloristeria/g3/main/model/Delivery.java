package com.sysbeckysfloristeria.g3.main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    @JoinColumn(name = "pay_id",nullable = false, unique = true)
    private Pay pay;
    private String methodDelivery;
    private String deliveryAddres;
    private Date arrivalDate;
    private LocalTime arrivalTime;
}
