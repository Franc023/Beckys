package com.sysbeckysfloristeria.g3.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/error-mail")
    public void testErrorEmail() {
        // Generar una excepción deliberada
        throw new RuntimeException("¡Este es un error de prueba para el sistema de correos!");
    }

    @GetMapping("/null-error")
    public void testNullPointerError() {
        // Generar un NullPointerException
        String str = null;
        str.length(); // Esto generará un NullPointerException
    }

    @GetMapping("/arithmetic-error")
    public void testArithmeticError() {
        // Generar un ArithmeticException
        int result = 100 / 0; // Esto generará un ArithmeticException
    }
} 