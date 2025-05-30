package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.model.Product;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductDto;
import com.sysbeckysfloristeria.g3.main.repository.IProductRepository;
import com.sysbeckysfloristeria.g3.main.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    private ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getImgUrl(),
                product.getName(),
                product.getSeason(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getDateAdded()
        );
    }

    @Override
    public List<ProductDto> getAllProduct() {
        var products = productRepository.findAll();
        // No lanzamos excepción aquí, devolvemos lista vacía si no hay productos
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveProduct(Product product) {
        if (product.getName() == null || product.getPrice() == null) {
            throw new RuntimeException("El nombre y el precio del producto son obligatorios.");
        }
        productRepository.save(product);
    }

    @Override
    public void editProduct(ProductDto productDto) {
        Product existingProduct = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado para editar."));

        // Solo actualiza los campos que vienen en el DTO
        existingProduct.setName(productDto.getName());
        existingProduct.setSeason(productDto.getSeason());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setStock(productDto.getStock());
        existingProduct.setCategory(productDto.getCategory());
        existingProduct.setDateAdded(productDto.getDateAdded());

        // Solo cambia la imagen si viene una nueva
        if (productDto.getImgUrl() != null && !productDto.getImgUrl().isBlank()) {
            existingProduct.setImgUrl(productDto.getImgUrl());
        }

        productRepository.save(existingProduct);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDto)
                .or(() -> {
                    throw new RuntimeException("Producto con ID " + id + " no encontrado.");
                });
    }

    @Override
    public List<ProductDto> findByName(String name) {
        var products = productRepository.findByNameContainingIgnoreCase(name);
        if (products.isEmpty()) {
            throw new RuntimeException("No se encontraron productos con ese nombre.");
        }
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findByDescription(String description) {
        var products = productRepository.findByDescriptionContainingIgnoreCase(description);
        if (products.isEmpty()) {
            throw new RuntimeException("No se encontraron productos con esa descripción.");
        }
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findBySeason(String season) {
        var products = productRepository.findBySeasonContainingIgnoreCase(season);
        if (products.isEmpty()) {
            throw new RuntimeException("No se encontraron productos para esa temporada.");
        }
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> search(String word) {
        var searchName = productRepository.findByNameContainingIgnoreCase(word);
        var searchDescription = productRepository.findByDescriptionContainingIgnoreCase(word);

        Set<Product> combined = new HashSet<>();
        combined.addAll(searchName);
        combined.addAll(searchDescription);

        if (combined.isEmpty()) {
            throw new RuntimeException("No se encontraron resultados para la búsqueda.");
        }

        return combined.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public String deletById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("No se encontró un producto con ese ID para eliminar.");
        }
        productRepository.deleteById(id);
        return "Producto eliminado con éxito.";
    }

    @Override
    public List<ProductDto> getProductsByDateAdded() {
        var products = productRepository.findAllByOrderByDateAddedAsc();
        if (products.isEmpty()) {
            throw new RuntimeException("No hay productos disponibles por fecha.");
        }
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsSortedByNameAsc() {
        var products = productRepository.findAllByOrderByNameAsc();
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsSortedByNameDesc() {
        var products = productRepository.findAllByOrderByNameDesc();
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
