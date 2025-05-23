package com.sysbeckysfloristeria.g3.main.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFullInfoDto {
    private String name;
    private String email;
    private List<CartDto> carts;
    private List<PayDto> payments;
}
