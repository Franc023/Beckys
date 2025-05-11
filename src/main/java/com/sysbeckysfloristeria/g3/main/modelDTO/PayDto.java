package com.sysbeckysfloristeria.g3.main.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayDto {
    private Long id;
    private Long userId;
    private Long cartId;
    private String typePayment;
    private Date fechaPago;
}
