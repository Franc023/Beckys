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
    private DeliveryService deliveryServicer;

    @GetMapping("/allDelivery")
    public List<DeliveryDto> AllDelivery(){
        return deliveryServicer.getAllDelivery();
    }

    @PostMapping("/saveDelivery")
    public ResponseEntity<String> saveDelivery(@Valid @RequestBody Delivery delivery) {
        try {
            deliveryServicer.saveDelivery(delivery);
            return ResponseEntity.status(HttpStatus.CREATED).body("Entrega guardada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la entrega: " + e.getMessage());
        }
    }

    @PutMapping("/editDelivery")
    public ResponseEntity<String> editDelivery(@Valid @RequestBody Delivery delivery) {
        try {
            deliveryServicer.editDelivery(delivery);
            return ResponseEntity.ok("Entrega editada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al editar la entrega: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletDelivery/{id}")
    public ResponseEntity<String> deleteDelivery(@PathVariable Long id) {
        try {
            deliveryServicer.deletDelivery(id);
            return ResponseEntity.ok("Entrega eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la entrega: " + e.getMessage());
        }
    }

}
