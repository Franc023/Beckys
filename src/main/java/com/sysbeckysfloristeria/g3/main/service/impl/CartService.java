package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.exception.BadRequestException;
import com.sysbeckysfloristeria.g3.main.exception.ResourceNotFoundException;
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

    private CartDto convertToDto(Cart cart) {
        if (cart == null) {
            throw new BadRequestException("No se puede convertir un carrito nulo a DTO");
        }
        List<Long> productCartIds = cart.getProductCartList().stream()
                .map(ProductCart::getId)
                .collect(Collectors.toList());
        
        return new CartDto(
            cart.getId(),
            cart.getUser() != null ? cart.getUser().getId() : null,
            productCartIds,
            cart.getCreationDate()
        );
    }

    @Override
    public List<CartDto> getAllCart() {
        List<Cart> carts = cartRepository.findAll();
        if (carts.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron carritos registrados");
        }
        return carts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveCart(Cart cart) {
        if (cart == null) {
            throw new BadRequestException("Los datos del carrito no pueden ser nulos");
        }
        if (cart.getUser() == null) {
            throw new BadRequestException("El carrito debe estar asociado a un usuario");
        }
        cartRepository.save(cart);
    }

    @Override
    public void editCart(Cart cart) {
        if (cart == null || cart.getId() == null) {
            throw new BadRequestException("Los datos del carrito son inválidos");
        }
        if (!cartRepository.existsById(cart.getId())) {
            throw new ResourceNotFoundException("Carrito con ID " + cart.getId() + " no existe");
        }
        cartRepository.save(cart);
    }

    @Override
    public Optional<CartDto> findByIdcart(Long id) {
        if (id == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        return cartRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public void deletCartById(Long id) {
        if (id == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        if (!cartRepository.existsById(id)) {
            throw new ResourceNotFoundException("Carrito con ID " + id + " no existe");
        }
        cartRepository.deleteById(id);
    }
}
