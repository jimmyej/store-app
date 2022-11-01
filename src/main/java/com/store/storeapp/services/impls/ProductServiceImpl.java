package com.store.storeapp.services.impls;

import com.store.storeapp.entities.Product;
import com.store.storeapp.repositories.ProductRepository;
import com.store.storeapp.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts() {
        log.info("Getting product list");
        return repository.findAll();
    }

    public Product getProductById(Integer id) {
        log.info("Getting product by id={}", id);
        return repository.findById(id).get();
    }

    public Product createProduct(Product product) {
        log.info("Creating product={}", product.getName());
        return repository.save(product);
    }

    public Product updateProduct(Product product) {
        log.info("Updating product={}", product.getName());
        boolean exists = repository.existsById(product.getId());
        return exists ? repository.save(product) : null;
    }

    public boolean deleteProduct(Integer id) {
        log.info("Deleting product by id={}", id);
        boolean deleted = false;
        try {
            if(repository.existsById(id)){
                repository.deleteById(id);
                deleted = true;
            }
        } catch (Exception e) {
            log.error("Error deleting product by id={}", id);
            e.printStackTrace();
        }
        return deleted;
    }

    public List<Product> getActiveProducts(boolean isActive) {
        log.info("Getting active and inactive products by flag={}", isActive);
        return repository.findByActive(isActive);
    }
}
