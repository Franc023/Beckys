package com.sysbeckysfloristeria.g3.main.modelDTO;

import com.sysbeckysfloristeria.g3.main.model.Cart;
import com.sysbeckysfloristeria.g3.main.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private int number;
}
