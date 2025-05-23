package com.sysbeckysfloristeria.g3.main.service.impl;

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
    private IProductCartRepository repository;

    private ProductCartDto convertToDto(ProductCart productCart){
        return new ProductCartDto(productCart.getId(),productCart.getCart() != null ? productCart.getCart().getId():null
        , productCart.getProduct() != null ? productCart.getProduct().getId(): null
        , productCart.getAmount(), productCart.getMsg());
    }


    @Override
    public List<ProductCartDto> getAllProductCart() {
        List<ProductCart> productCart = repository.findAll();
        return productCart.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void saveProductCart(ProductCart product) {
        repository.save(product);
    }

    @Override
    public void editProductCart(ProductCart product) {
        this.saveProductCart(product);
    }

    @Override
    public Optional<ProductCartDto> findByIdProductCart(Long id) {
        ProductCart productCart = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto del carrito con ID " + id + " no encontrado"));
        return Optional.of(convertToDto(productCart));
    }

    @Override
    public String deletByIdProductCart(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("No se pudo eliminar, producto del carrito con ID "+id+" No encontrado.");
        }
        repository.deleteById(id);
        return "El producto del carrito se elimino correctamente";
    }
}
