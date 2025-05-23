package com.sysbeckysfloristeria.g3.main.modelDTO;

import com.sysbeckysfloristeria.g3.main.model.ProductCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestCheckoutDto {
    private String name;
    private String lastName;
    private String email;
    private int phone;
    private List<ProductCartDto> products;
    private String typePayment;
    private String deliveryAddress;
    private Date arriveDate;
    private LocalTime arrivalTime;
}
