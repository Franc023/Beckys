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

    @GetMapping
    public ResponseEntity<List<PayDto>> getAllPayments() {
        return ResponseEntity.ok(payService.getAllPay());
    }

    @PostMapping
    public ResponseEntity<String> savePay(@Valid @RequestBody Pay pay) {
        payService.savePay(pay);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Pago registrado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editPay(@PathVariable Long id, @Valid @RequestBody Pay pay) {
        pay.setId(id);
        payService.editPay(pay);
        return ResponseEntity.ok("Pago actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePay(@PathVariable Long id) {
        payService.deletPay(id);
        return ResponseEntity.ok("Pago eliminado exitosamente");
    }
}
