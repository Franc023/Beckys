package com.sysbeckysfloristeria.g3.main.service.impl;

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
        this.saveDelivery(delivery);
    }

    @Override
    public void deletDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}
