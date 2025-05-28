package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.exception.BadRequestException;
import com.sysbeckysfloristeria.g3.main.exception.ResourceNotFoundException;
import com.sysbeckysfloristeria.g3.main.model.Pay;
import com.sysbeckysfloristeria.g3.main.modelDTO.PayDto;
import com.sysbeckysfloristeria.g3.main.repository.IPayrepository;
import com.sysbeckysfloristeria.g3.main.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayService implements IPayService {

    @Autowired
    private IPayrepository payrepository;

    private PayDto convertToDto(Pay pay) {
        if (pay == null) {
            throw new BadRequestException("No se puede convertir un pago nulo a DTO");
        }
        return new PayDto(
            pay.getId(),
            pay.getUser().getId(),
            pay.getCart().getId(),
            pay.getTypePayment(),
            pay.getFechaPago()
        );
    }

    @Override
    public List<PayDto> getAllPay() {
        List<Pay> payments = payrepository.findAll();
        if (payments.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron pagos registrados");
        }
        return payments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void savePay(Pay pay) {
        if (pay == null) {
            throw new BadRequestException("Los datos del pago no pueden ser nulos");
        }
        if (pay.getUser() == null) {
            throw new BadRequestException("El usuario es requerido");
        }
        if (pay.getCart() == null) {
            throw new BadRequestException("El carrito es requerido");
        }
        if (pay.getTypePayment() == null || pay.getTypePayment().trim().isEmpty()) {
            throw new BadRequestException("El tipo de pago es requerido");
        }
        if (pay.getFechaPago() == null) {
            throw new BadRequestException("La fecha de pago es requerida");
        }
        payrepository.save(pay);
    }

    @Override
    public void editPay(Pay pay) {
        if (pay == null || pay.getId() == null) {
            throw new BadRequestException("Los datos del pago son inválidos");
        }
        if (!payrepository.existsById(pay.getId())) {
            throw new ResourceNotFoundException("Pago con ID " + pay.getId() + " no existe");
        }
        savePay(pay); // Reutilizamos las validaciones del método save
    }

    @Override
    public void deletPay(Long id) {
        if (id == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        if (!payrepository.existsById(id)) {
            throw new ResourceNotFoundException("Pago con ID " + id + " no existe");
        }
        payrepository.deleteById(id);
    }
}
