package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.Pay;
import com.sysbeckysfloristeria.g3.main.modelDTO.PayDto;
import com.sysbeckysfloristeria.g3.main.service.impl.PayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private PayService payService;

    @GetMapping("/allPay")
    public List<PayDto> getAllPay(){
        return payService.getAllPay();
    }

    @PostMapping("/savePay")
    public ResponseEntity<String> savePay(@Valid @RequestBody Pay pay) {
        try {
            payService.savePay(pay);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pago guardado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el pago: " + e.getMessage());
        }
    }

    @PutMapping("/editPay")
    public ResponseEntity<String> editPay(@Valid @RequestBody Pay pay) {
        try {
            payService.editPay(pay);
            return ResponseEntity.ok("Pago editado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al editar el pago: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletePay/{id}")
    public ResponseEntity<String> deletPay(@PathVariable Long id) {
        try {
            payService.deletPay(id);
            return ResponseEntity.ok("Pago eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el pago: " + e.getMessage());
        }
    }
}
