package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.Cart;
import com.sysbeckysfloristeria.g3.main.modelDTO.CartDto;
import com.sysbeckysfloristeria.g3.main.service.impl.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCart());
    }

    @PostMapping
    public ResponseEntity<String> saveCart(@Valid @RequestBody Cart cart) {
        cartService.saveCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Carrito creado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editCart(@PathVariable Long id, @Valid @RequestBody Cart cart) {
        cart.setId(id);
        cartService.editCart(cart);
        return ResponseEntity.ok("Carrito actualizado exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> findCartById(@PathVariable Long id) {
        return cartService.findByIdcart(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        cartService.deletCartById(id);
        return ResponseEntity.ok("Carrito eliminado exitosamente");
    }
}
