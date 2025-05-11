package com.sysbeckysfloristeria.g3.main.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private double price;
    private int stock;
    private String Category;
    private Date dateExpiration;
}
