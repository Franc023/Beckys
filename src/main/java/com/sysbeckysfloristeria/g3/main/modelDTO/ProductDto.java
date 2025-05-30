package com.sysbeckysfloristeria.g3.main.modelDTO;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String imgUrl;
    private String name;
    private String season;
    private String description;
    @Min(0)
    private Double price;
    private int stock;
    private String Category;
    private Date dateAdded;
}
