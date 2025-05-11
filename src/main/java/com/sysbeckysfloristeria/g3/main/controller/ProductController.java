package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.Product;
import com.sysbeckysfloristeria.g3.main.modelDTO.ProductDto;
import com.sysbeckysfloristeria.g3.main.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/product/v1")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping("/allproduct")
    public List<ProductDto> getAllProduct(){
        return service.getAllProduct();
    }

    @PostMapping("/saveproduct")
    public String saveProduct(@RequestBody Product product){
        service.saveProduct(product);
        return "product saved";
    }

    @PutMapping("/editproducts")
    public String editProduct(@RequestBody Product product){
        service.editProduct(product);
        return "product edited";
    }

    @GetMapping("/idproduct/{id}")
    public Optional<ProductDto> findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping("/nameproduct")
    public List<ProductDto> findByName(@RequestBody Map<String,String> request){
        String name = request.get("name");
        return service.findByName(name);
    }

    @PostMapping("/descriptionproduct")
    public List<ProductDto> findByDescription(@RequestBody Map<String, String>request){
        String description= request.get("description");
        return service.findByDescription(description);
    }

    @DeleteMapping("/deletid/{id}")
    public String deletById(@PathVariable Long id){
        return service.deletById(id);
    }
}

