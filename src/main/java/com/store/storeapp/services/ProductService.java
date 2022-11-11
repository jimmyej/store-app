package com.store.storeapp.services;

import com.store.storeapp.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();
    Optional<Product> getProductById(Integer id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    boolean deleteProductById(Integer id);
    List<Product> getActiveProducts(boolean isActive);
}
