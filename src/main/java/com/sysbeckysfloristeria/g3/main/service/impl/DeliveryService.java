package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.exception.BadRequestException;
import com.sysbeckysfloristeria.g3.main.exception.ResourceNotFoundException;
import com.sysbeckysfloristeria.g3.main.model.Delivery;
import com.sysbeckysfloristeria.g3.main.modelDTO.DeliveryDto;
import com.sysbeckysfloristeria.g3.main.repository.IDeliveryRepository;
import com.sysbeckysfloristeria.g3.main.service.IDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService implements IDeliveryService {

    @Autowired
    private IDeliveryRepository deliveryRepository;

    private DeliveryDto convertToDto(Delivery delivery) {
        if (delivery == null) {
            throw new BadRequestException("No se puede convertir una entrega nula a DTO");
        }
        return new DeliveryDto(
            delivery.getId(),
            delivery.getPay().getId(),
            delivery.getMethodDelivery(),
            delivery.getDeliveryAddres(),
            delivery.getArrivalDate(),
            delivery.getArrivalTime()
        );
    }

    @Override
    public List<DeliveryDto> getAllDelivery() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        if (deliveries.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron entregas registradas");
        }
        return deliveries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveDelivery(Delivery delivery) {
        if (delivery == null) {
            throw new BadRequestException("Los datos de la entrega no pueden ser nulos");
        }
        if (delivery.getPay() == null) {
            throw new BadRequestException("El pago asociado es requerido");
        }
        if (delivery.getMethodDelivery() == null || delivery.getMethodDelivery().trim().isEmpty()) {
            throw new BadRequestException("El método de entrega es requerido");
        }
        if (delivery.getDeliveryAddres() == null || delivery.getDeliveryAddres().trim().isEmpty()) {
            throw new BadRequestException("La dirección de entrega es requerida");
        }
        if (delivery.getArrivalDate() == null) {
            throw new BadRequestException("La fecha de llegada es requerida");
        }
        if (delivery.getArrivalTime() == null) {
            throw new BadRequestException("La hora de llegada es requerida");
        }
        deliveryRepository.save(delivery);
    }

    @Override
    public void editDelivery(Delivery delivery) {
        if (delivery == null || delivery.getId() == null) {
            throw new BadRequestException("Los datos de la entrega son inválidos");
        }
        if (!deliveryRepository.existsById(delivery.getId())) {
            throw new ResourceNotFoundException("Entrega con ID " + delivery.getId() + " no existe");
        }
        saveDelivery(delivery); // Reutilizamos las validaciones del método save
    }

    @Override
    public void deletDelivery(Long id) {
        if (id == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        if (!deliveryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Entrega con ID " + id + " no existe");
        }
        deliveryRepository.deleteById(id);
    }
}
