package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.ProductCart;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductCartDto;
import com.sysbeckysfloristeria.g3.main.service.impl.ProductCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productCart")
public class ProductCartController {
    @Autowired
    private ProductCartService servicePCart;

    @GetMapping("/allProductCart")
    public List<ProductCartDto> getAllProductCart(){
        return servicePCart.getAllProductCart();
    }

    @PostMapping("/saveProductCart")
    public String saveProductCart(@RequestBody ProductCart productCart){
        servicePCart.saveProductCart(productCart);
        return "saved product";
    }

    @GetMapping("/findByIdProductCart/{id}")
    public Optional<ProductCartDto> findByIdProductCart(@PathVariable Long id){
        return servicePCart.findByIdProductCart(id);
    }

    @DeleteMapping("/deletByIdProductCart/{id}")
    public String deletByIdProducCart(@PathVariable  Long id){
        servicePCart.deletByIdProductCart(id);
        return "Deleted productCart ";
    }
}
