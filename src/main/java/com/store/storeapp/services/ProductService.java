package com.store.storeapp.services;

import com.store.storeapp.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product getProductById(Integer id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    boolean deleteProduct(Integer id);
    List<Product> getActiveProducts(boolean isActive);
}
