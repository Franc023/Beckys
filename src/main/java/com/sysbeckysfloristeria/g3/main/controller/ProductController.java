package com.sysbeckysfloristeria.g3.main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysbeckysfloristeria.g3.main.model.Product;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductDto;
import com.sysbeckysfloristeria.g3.main.service.IProductService;
import com.sysbeckysfloristeria.g3.main.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product/v1")
public class ProductController {

    @Autowired
    private IProductService service;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/allproduct")
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        return ResponseEntity.ok(service.getAllProduct());
    }

    @PostMapping(value = "/saveproduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveProduct(
            @RequestPart("product") String productJson,
            @RequestPart("image") MultipartFile imageFile) {

        try {
            // Convertir el JSON manualmente a un objeto Product
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(productJson, Product.class);

            String imageUrl = fileStorageService.storeImage(imageFile); // lógica que veremos abajo
            product.setImgUrl(imageUrl);
            service.saveProduct(product);
            return ResponseEntity.ok("Producto guardado correctamente con imagen.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar el producto: " + e.getMessage());
        }
    }

    @PutMapping("/editproducts")
    public ResponseEntity<String> editProduct(@RequestBody Product product) {
        service.editProduct(product);
        return ResponseEntity.ok("Producto editado correctamente.");
    }

    @GetMapping("/idproduct/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado")));
    }

    @PostMapping("/nameproduct")
    public ResponseEntity<List<ProductDto>> findByName(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        return ResponseEntity.ok(service.findByName(name));
    }

    @PostMapping("/descriptionproduct")
    public ResponseEntity<List<ProductDto>> findByDescription(@RequestBody Map<String, String> request) {
        String description = request.get("description");
        return ResponseEntity.ok(service.findByDescription(description));
    }

    @DeleteMapping("/deletid/{id}")
    public ResponseEntity<String> deletById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deletById(id));
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductDto>> search(@RequestBody Map<String, String> request) {
        String word = request.get("word");
        return ResponseEntity.ok(service.search(word));
    }

    @GetMapping("/sortByDate")
    public ResponseEntity<List<ProductDto>> getProductsByDateAdded() {
        return ResponseEntity.ok(service.getProductsByDateAdded());
    }

    @GetMapping("/sortByNameAsc")
    public ResponseEntity<List<ProductDto>> getProductsSortedByNameAsc() {
        return ResponseEntity.ok(service.getProductsSortedByNameAsc());
    }

    @GetMapping("/sortByNameDesc")
    public ResponseEntity<List<ProductDto>> getProductsSortedByNameDesc() {
        return ResponseEntity.ok(service.getProductsSortedByNameDesc());
    }
}
