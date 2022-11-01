package com.store.storeapp.controllers;

import com.store.storeapp.entities.Product;
import com.store.storeapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "")
    List<Product> getProducts(){
        return service.getProducts();
    }

    @GetMapping(value = "/{id}")
    Product getProductById(@PathVariable Integer id){
        return service.getProductById(id);
    }

    @PostMapping(value = "")
    Product createProduct(@RequestBody Product product){
        return service.createProduct(product);
    }

    @PutMapping(value = "")
    Product updateProduct(@RequestBody Product product){
        return service.updateProduct(product);
    }

    @DeleteMapping(value = "/{id}")
    boolean deleteProduct(@PathVariable Integer id){
        return service.deleteProduct(id);
    }

    @GetMapping(value = "/actives/{isActive}")
    List<Product> getActiveProducts(@PathVariable boolean isActive){
        return service.getActiveProducts(isActive);
    }

}
