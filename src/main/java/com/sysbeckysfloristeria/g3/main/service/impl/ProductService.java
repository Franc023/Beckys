package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.model.Product;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductDto;
import com.sysbeckysfloristeria.g3.main.repository.IProductRepository;
import com.sysbeckysfloristeria.g3.main.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        var products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByDescription(String description) {
        var products = productRepository.findByDescriptionContainingIgnoreCase(description);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findBySeason(String season) {
        var products = productRepository.findBySeasonContainingIgnoreCase(season);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> search(String word) {
        var searchName = productRepository.findByNameContainingIgnoreCase(word);
        var searchDescription = productRepository.findByDescriptionContainingIgnoreCase(word);

        Set<Product> combined= new HashSet<>();
        combined.addAll(searchName);
        combined.addAll(searchDescription);

        return combined.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public String deletById(Long id) {
        productRepository.deleteById(id);
        return "Producto eliminado con exito";
    }
}
