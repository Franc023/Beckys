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
    public ResponseEntity<String> saveCart(@Valid @RequestBody Cart cart) {
        try {
            cartService.saveCart(cart);
            return ResponseEntity.status(HttpStatus.CREATED).body("Carrito guardado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el carrito: " + e.getMessage());
        }
    }

    @PutMapping("/editCart")
    public ResponseEntity<String> editCart(@Valid @RequestBody Cart cart) {
        try {
            cartService.editCart(cart);
            return ResponseEntity.ok("Carrito editado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al editar el carrito: " + e.getMessage());
        }
    }

    @GetMapping("/findByIdCart/{id}")
    public ResponseEntity<CartDto> findByIdCart(@PathVariable Long id) {
        Optional<CartDto> cartDto = cartService.findByIdcart(id);
        return cartDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/deletCart/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        try {
            cartService.deletCartById(id);
            return ResponseEntity.ok("Carrito eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el carrito: " + e.getMessage());
        }
    }

}
