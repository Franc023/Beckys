package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.exception.BadRequestException;
import com.sysbeckysfloristeria.g3.main.exception.ResourceNotFoundException;
import com.sysbeckysfloristeria.g3.main.model.ProductCart;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductCartDto;
import com.sysbeckysfloristeria.g3.main.repository.IProductCartRepository;
import com.sysbeckysfloristeria.g3.main.service.IProductCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductCartService implements IProductCartService {

    @Autowired
    private IProductCartRepository productCartRepository;

    private ProductCartDto convertToDto(ProductCart productCart) {
        if (productCart == null) {
            throw new BadRequestException("No se puede convertir un producto en carrito nulo a DTO");
        }
        return new ProductCartDto(
            productCart.getId(),
            productCart.getCart().getId(),
            productCart.getProduct().getId(),
            productCart.getAmount(),
            productCart.getMsg()
        );
    }

    @Override
    public List<ProductCartDto> getAllProductCart() {
        List<ProductCart> productCarts = productCartRepository.findAll();
        if (productCarts.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos en carritos");
        }
        return productCarts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveProductCart(ProductCart productCart) {
        if (productCart == null) {
            throw new BadRequestException("Los datos del producto en carrito no pueden ser nulos");
        }
        if (productCart.getProduct() == null) {
            throw new BadRequestException("El producto es requerido");
        }
        if (productCart.getCart() == null) {
            throw new BadRequestException("El carrito es requerido");
        }
        if (productCart.getAmount() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a 0");
        }
        productCartRepository.save(productCart);
    }

    @Override
    public void editProductCart(ProductCart productCart) {
        if (productCart == null || productCart.getId() == null) {
            throw new BadRequestException("Los datos del producto en carrito son inválidos");
        }
        if (!productCartRepository.existsById(productCart.getId())) {
            throw new ResourceNotFoundException("Producto en carrito con ID " + productCart.getId() + " no existe");
        }
        if (productCart.getAmount() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a 0");
        }
        productCartRepository.save(productCart);
    }

    @Override
    public Optional<ProductCartDto> findByIdProductCart(Long id) {
        if (id == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        return productCartRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public String deletByIdProductCart(Long id) {
        if (id == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        if (!productCartRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto en carrito con ID " + id + " no existe");
        }
        productCartRepository.deleteById(id);
        return "Producto eliminado del carrito exitosamente";
    }
}
