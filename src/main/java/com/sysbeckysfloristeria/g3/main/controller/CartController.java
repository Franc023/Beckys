package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.Cart;
import com.sysbeckysfloristeria.g3.main.modelDTO.CartDto;
import com.sysbeckysfloristeria.g3.main.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/getCart")
    public List<CartDto> getCart (){
        return cartService.getAllCart();
    }

    @PostMapping("/saveCart")
    public String saveCart(@RequestBody Cart cart){
        cartService.saveCart(cart);
        return "Saved Cart";
    }

    @PutMapping("/editCart")
    public String editCart(@RequestBody Cart cart){
        cartService.editCart(cart);
        return "Edited cart";
    }

    @GetMapping("/findByIdCart/{id}")
    public Optional<CartDto> findByIdCart(@PathVariable Long id){
        return cartService.findByIdcart(id);
    }

    @DeleteMapping("/deletCart/{if}")
    public String deletCart(@PathVariable Long id){
        cartService.deletCartById(id);
        return "deleted cart";
    }

}
