package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.model.Cart;
import com.sysbeckysfloristeria.g3.main.model.ProductCart;
import com.sysbeckysfloristeria.g3.main.modelDTO.CartDto;
import com.sysbeckysfloristeria.g3.main.repository.ICartRepository;
import com.sysbeckysfloristeria.g3.main.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {

    @Autowired
    private ICartRepository cartRepository;

    private CartDto convertToDto(Cart cart){

        List<Long> productCartListIds= cart.getProductCartList().stream()
                .map(ProductCart::getId).collect(Collectors.toList());

        return new CartDto(cart.getId(),cart.getUser() != null ? cart.getUser().getId():null
                ,productCartListIds,cart.getCreationDate());
    }

    @Override
    public List<CartDto> getAllCart() {
        return cartRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void editCart(Cart cart) {
        this.cartRepository.save(cart);
    }

    @Override
    public Optional<CartDto> findByIdcart(Long id) {
        return cartRepository.findById(id).stream().map(this::convertToDto).findFirst();
    }

    @Override
    public void deletCartById(Long id) {
        cartRepository.deleteById(id);
    }
}
