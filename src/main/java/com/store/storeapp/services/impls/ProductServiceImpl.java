package com.store.storeapp.services.impls;

import com.store.storeapp.entities.Product;
import com.store.storeapp.repositories.ProductRepository;
import com.store.storeapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return repository.findById(id);
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Product product) {
        boolean exists = repository.existsById(product.getId());
        return exists ? repository.save(product) : null;
    }

    public void deleteProductById(Integer id) {
        boolean exists = repository.existsById(id);
        if(exists) repository.deleteById(id);
    }

    public List<Product> getActiveProducts(boolean isActive) {
        return repository.findByActive(isActive);
    }
}
