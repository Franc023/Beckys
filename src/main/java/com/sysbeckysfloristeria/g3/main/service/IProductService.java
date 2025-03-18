package com.sysbeckysfloristeria.g3.main.service;

import com.sysbeckysfloristeria.g3.main.model.Product;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface IProductService{
    List<ProductDTO> getAllProduct();
    Product saveProduct(Product product);
    Product editProduct(Product product);
    Optional<Product> findById(Long id);
    List<ProductDTO> findByName(String name);
    List<ProductDTO> findByDescription(String description);
    String deletById(Long id);
}
