package com.sysbeckysfloristeria.g3.main.service;

import com.sysbeckysfloristeria.g3.main.model.Product;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductDTO;
import com.sysbeckysfloristeria.g3.main.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    private ProductDTO convertToDto(Product product){
        return new ProductDTO(product.getName(),product.getDescription()
                ,product.getPrice(),product.getStock(),product.getCategory(),product.getDateExpiration());
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        var products= productRepository.findAll();
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product editProduct(Product product) {
        return this.saveProduct(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductDTO> findByName(String name) {
        var products = productRepository.findByNameContaining(name);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findByDescription(String description) {
        var products = productRepository.findByDescriptionContaining(description);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public String deletById(Long id) {
        productRepository.deleteById(id);
        return "Producto eliminado con exito";
    }
}
