package com.store.storeapp.services.impls;

import com.store.storeapp.entities.Product;
import com.store.storeapp.repositories.ProductRepository;
import com.store.storeapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(Integer id) {
        return repository.findById(id).get();
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Product product) {
        boolean exists = repository.existsById(product.getId());
        return exists ? repository.save(product) : null;
    }

    public boolean deleteProduct(Integer id) {
        boolean deleted = false;
        try {
            repository.deleteById(id);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public List<Product> getActiveProducts(boolean isActive) {
        return repository.findByActive(isActive);
    }
}
