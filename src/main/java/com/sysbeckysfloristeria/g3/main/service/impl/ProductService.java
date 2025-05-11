package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.model.Product;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductDto;
import com.sysbeckysfloristeria.g3.main.repository.IProductRepository;
import com.sysbeckysfloristeria.g3.main.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    private ProductDto convertToDto(Product product){
        return new ProductDto(product.getName(),product.getDescription()
                ,product.getPrice(),product.getStock(),product.getCategory(),product.getDateExpiration());
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product> products= productRepository.findAll();
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void saveProduct(Product product) {
         productRepository.save(product);
    }

    @Override
    public void editProduct(Product product) {
        this.saveProduct(product);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id).map(this::convertToDto) ;
    }

    @Override
    public List<ProductDto> findByName(String name) {
        var products = productRepository.findByNameContaining(name);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByDescription(String description) {
        var products = productRepository.findByDescriptionContaining(description);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public String deletById(Long id) {
        productRepository.deleteById(id);
        return "Producto eliminado con exito";
    }
}
