package com.sysbeckysfloristeria.g3.main.service;

import com.sysbeckysfloristeria.g3.main.model.Delivery;
import com.sysbeckysfloristeria.g3.main.modelDTO.DeliveryDto;

import java.util.List;

public interface IDeliveryService {
    List<DeliveryDto> getAllDelivery();
    void saveDelivery(Delivery delivery);
    void editDelivery(Delivery delivery);
    void deletDelivery(Long id);
}
