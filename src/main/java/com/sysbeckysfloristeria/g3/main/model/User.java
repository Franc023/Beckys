package com.sysbeckysfloristeria.g3.main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private boolean guest;
    private String name;
    private String lastName;
    private String email;
    private int phone;
    @Enumerated(EnumType.STRING)
    private Role rol;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Cart> cart;
    private String password;

    public User(String name, String lastName, String email, int number, Role role, String password) {
    }
}
