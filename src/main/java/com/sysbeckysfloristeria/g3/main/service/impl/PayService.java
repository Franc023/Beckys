package com.sysbeckysfloristeria.g3.main.service.impl;

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

    private PayDto converToDto(Pay pay){
        return new PayDto(pay.getId(),pay.getUser().getId(),pay.getCart().getId()
        ,pay.getTypePayment(),pay.getFechaPago());
    }

    @Override
    public List<PayDto> getAllPay() {
        return payrepository.findAll().stream().map(this::converToDto).collect(Collectors.toList());
    }

    @Override
    public void savePay(Pay pay) {
        payrepository.save(pay);
    }

    @Override
    public void editPay(Pay pay) {
        if(!payrepository.existsById(pay.getId())){
            throw new ResourceNotFoundException("Pago con id "+ pay.getId() +" No existe para editar");
        }
        this.savePay(pay);
    }

    @Override
    public void deletPay(Long id) {
        if(!payrepository.existsById(id)){
            throw new ResourceNotFoundException("Pago con id "+ id + "no existe para elimianr");
        }
        payrepository.deleteById(id);
    }
}
