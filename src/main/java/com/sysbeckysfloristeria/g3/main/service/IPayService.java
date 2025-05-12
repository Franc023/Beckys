package com.sysbeckysfloristeria.g3.main.service;

import com.sysbeckysfloristeria.g3.main.model.Pay;
import com.sysbeckysfloristeria.g3.main.modelDTO.PayDto;

import java.util.List;

public interface IPayService {
    List<PayDto> getAllPay();
    void savePay(Pay pay);
    void editPay(Pay pay);
    void deletPay(Long id);
}
