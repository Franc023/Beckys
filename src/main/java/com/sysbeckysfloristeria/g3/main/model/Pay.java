package com.sysbeckysfloristeria.g3.main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id",nullable = true)
    private User user;
    @OneToOne
    @JoinColumn(name = "cart_id",nullable = false, unique = true)
    private Cart cart;
    private String typePayment;
    private Date fechaPago;
}
