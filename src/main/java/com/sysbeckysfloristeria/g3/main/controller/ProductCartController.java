package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.ProductCart;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductCartDto;
import com.sysbeckysfloristeria.g3.main.service.impl.ProductCartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> saveProductCart(@Valid @RequestBody ProductCart productCart){
        try {
            servicePCart.saveProductCart(productCart);
            return ResponseEntity.status(HttpStatus.CREATED).body("Producto guardado correctamente");
        } catch (Exception e) {
            // Aquí puedes capturar excepciones específicas si tienes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el producto: " + e.getMessage());
        }
    }

    @GetMapping("/findByIdProductCart/{id}")
    public ResponseEntity<?> findByIdProductCart(@PathVariable Long id){
        Optional<ProductCartDto> optional = servicePCart.findByIdProductCart(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto en carrito con id " + id + " no encontrado");
        }
    }

    @DeleteMapping("/deletByIdProductCart/{id}")
    public ResponseEntity<?> deletByIdProducCart(@PathVariable Long id){
        try {
            servicePCart.deletByIdProductCart(id);
            return ResponseEntity.ok("Producto eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
