package com.sysbeckysfloristeria.g3.main.modelDTO;

import com.sysbeckysfloristeria.g3.main.model.Pay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private Long id;
    private Long payId;
    private String methodDelivery;
    private String deliveryAddres;
    private Date arrivalDate;
    private LocalTime arrivalTime;
}
