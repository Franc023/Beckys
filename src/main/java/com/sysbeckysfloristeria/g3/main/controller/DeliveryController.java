package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.Delivery;
import com.sysbeckysfloristeria.g3.main.modelDTO.DeliveryDto;
import com.sysbeckysfloristeria.g3.main.service.impl.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<DeliveryDto>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDelivery());
    }

    @PostMapping
    public ResponseEntity<String> saveDelivery(@Valid @RequestBody Delivery delivery) {
        deliveryService.saveDelivery(delivery);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Entrega registrada exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editDelivery(@PathVariable Long id, @Valid @RequestBody Delivery delivery) {
        delivery.setId(id);
        deliveryService.editDelivery(delivery);
        return ResponseEntity.ok("Entrega actualizada exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDelivery(@PathVariable Long id) {
        deliveryService.deletDelivery(id);
        return ResponseEntity.ok("Entrega eliminada exitosamente");
    }
}
