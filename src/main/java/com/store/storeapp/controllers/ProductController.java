package com.store.storeapp.controllers;

import com.store.storeapp.entities.Product;
import com.store.storeapp.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "")
    ResponseEntity<List<Product>> getProducts(){
        try{
            List<Product> products = service.getProducts();
            if (products.isEmpty()) {
                log.warn("Data not found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("Data found");
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Server error");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Product> getProductById(@PathVariable Integer id){
        Optional<Product> product = service.getProductById(id);
        if (product.isPresent()) {
            log.info("Data found");
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            log.warn("Data not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "")
    ResponseEntity<Product> createProduct(@RequestBody Product product){
        try {
            Product newProduct = service.createProduct(product);
            log.info("Product created");
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Server error");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "")
    ResponseEntity<Product> updateProduct(@RequestBody Product product){
        try {
            Product newProduct = service.updateProduct(product);
            log.info("Product updated");
            return new ResponseEntity<>(newProduct, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Server error");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<HttpStatus> deleteProduct(@PathVariable Integer id){
        try {
            service.deleteProductById(id);
            log.info("Product deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Server error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/actives/{isActive}")
    ResponseEntity<List<Product>> getActiveProducts(@PathVariable boolean isActive){
        try {
            List<Product> activeProducts = service.getActiveProducts(isActive);
            if (activeProducts.isEmpty()) {
                log.warn("Data not found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            log.info("Data found");
            return new ResponseEntity<>(activeProducts, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Server error");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
