package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.Pay;
import com.sysbeckysfloristeria.g3.main.modelDTO.PayDto;
import com.sysbeckysfloristeria.g3.main.service.impl.PayService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String savePay(@RequestBody Pay pay){
        payService.savePay(pay);
        return "Saved pay";
    }

    @PutMapping("/editPay")
    public String editPay(@RequestBody Pay pay){
        payService.editPay(pay);
        return "Edited pay";
    }

    public String deletPay(@PathVariable Long id){
        payService.deletPay(id);
        return "Deleted pay";
    }
}
