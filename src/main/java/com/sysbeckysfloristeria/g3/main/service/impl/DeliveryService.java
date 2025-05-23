package com.sysbeckysfloristeria.g3.main.service.impl;

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

    private DeliveryDto converToDto(Delivery delivery){
        return new DeliveryDto(delivery.getId(),delivery.getPay().getId(), delivery.getMethodDelivery()
        ,delivery.getDeliveryAddres(), delivery.getArrivalDate(), delivery.getArrivalTime());
    }

    @Override
    public List<DeliveryDto> getAllDelivery() {
        return deliveryRepository.findAll().stream().map(this::converToDto).collect(Collectors.toList());
    }

    @Override
    public void saveDelivery(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    @Override
    public void editDelivery(Delivery delivery) {
        if (!deliveryRepository.existsById(delivery.getId())) {
            throw new ResourceNotFoundException("Entrega con ID " + delivery.getId() + " no existe para editar.");
        }
        this.saveDelivery(delivery);
    }

    @Override
    public void deletDelivery(Long id) {
        if (!deliveryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Entrega con ID " + id + " no existe para eliminar.");
        }
        deliveryRepository.deleteById(id);
    }
}
