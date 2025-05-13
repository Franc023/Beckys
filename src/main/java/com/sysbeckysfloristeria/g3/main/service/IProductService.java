package com.sysbeckysfloristeria.g3.main.service;

import com.sysbeckysfloristeria.g3.main.model.Product;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductDto;

import java.util.List;
import java.util.Optional;

public interface IProductService{
    List<ProductDto> getAllProduct();
    void saveProduct(Product product);
    void editProduct(Product product);
    Optional<ProductDto> findById(Long id);
    List<ProductDto> findByName(String name);
    List<ProductDto> findByDescription(String description);
    List<ProductDto> findBySeason(String season);
    List<ProductDto> search(String word);
    String deletById(Long id);
}
