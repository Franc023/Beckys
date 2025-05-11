package com.sysbeckysfloristeria.g3.main.service;

import com.sysbeckysfloristeria.g3.main.model.Cart;
import com.sysbeckysfloristeria.g3.main.modelDTO.CartDto;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    List<CartDto> getAllCart();
    void saveCart(Cart cart);
    void editCart(Cart cart);
    Optional<CartDto> findByIdcart(Long id);
    void deletCartById (Long id);
}
