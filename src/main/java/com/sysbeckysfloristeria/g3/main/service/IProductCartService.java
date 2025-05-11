package com.sysbeckysfloristeria.g3.main.service;


import com.sysbeckysfloristeria.g3.main.model.ProductCart;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductCartDto;

import java.util.List;
import java.util.Optional;

public interface IProductCartService {
    List<ProductCartDto> getAllProductCart();
    void saveProductCart(ProductCart product);
    void editProductCart(ProductCart product);
    Optional<ProductCartDto> findByIdProductCart(Long id);
    String deletByIdProductCart(Long id);
}
