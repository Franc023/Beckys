package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.Delivery;
import com.sysbeckysfloristeria.g3.main.modelDTO.DeliveryDto;
import com.sysbeckysfloristeria.g3.main.service.impl.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String saveDelivery(@RequestBody Delivery delivery){
        deliveryServicer.saveDelivery(delivery);
        return "Saved Delivery ";
    }

    @PutMapping("/editDelivery")
    public String editDelivery(@RequestBody Delivery delivery){
        deliveryServicer.editDelivery(delivery);
        return "Edited Delivery";
    }

    @DeleteMapping("/deletDelivery/{id}")
    public String deletDelivery(@PathVariable Long id){
        deliveryServicer.deletDelivery(id);
        return "Delted Delivery";
    }

}
