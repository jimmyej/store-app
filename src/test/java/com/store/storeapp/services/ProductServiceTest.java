package com.store.storeapp.services;

import com.store.storeapp.entities.Product;
import com.store.storeapp.repositories.ProductRepository;
import com.store.storeapp.services.impls.ProductServiceImpl;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Import(ProductServiceImpl.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository mockedRepository;

    private List<Product> products = Arrays.asList(
            new Product(1, "Product 1", 10.0, true),
            new Product(2, "Product 2", 20.0, false),
            new Product(3, "Product 3", 30.0, true)
    );

    private List<Product> activeProducts = products.stream().filter(f -> f.isActive()).collect(Collectors.toList());

    @Test
    public void testSaveProduct_success() {
        Product request = new Product();
        request.setName("Product 1");
        request.setPrice(10.0);
        request.setActive(true);

        Product response = new Product(1, "Product 1", 10.0, true);

        when(mockedRepository.save(request)).thenReturn(response);
        Product newProduct = service.createProduct(request);

        assertNotNull(newProduct);
        assertEquals(response, newProduct);
        assertEquals(response.getName(), newProduct.getName());
    }

    @Test
    public void testUpdateProduct_success() {
        Product response = new Product(1, "Product 1", 20.0, true);

        when(mockedRepository.existsById(eq(1))).thenReturn(true);
        when(mockedRepository.save(any(Product.class))).thenReturn(response);
        Product newProduct = service.updateProduct(response);

        assertEquals(response, newProduct);
    }

    @Test
    public void testGetProductById_success() {
        Product response = new Product(1, "Product 1", 20.0, true);

        when(mockedRepository.findById(eq(1))).thenReturn(Optional.of(response));
        Optional<Product> product = service.getProductById(1);

        product.ifPresent((value) ->{
            assertEquals(value, response);
        });
    }

    @Test
    public void testDeleteProductById() {
        when(mockedRepository.existsById(eq(1))).thenReturn(true);
        service.deleteProductById(1);
    }

    @Test
    public void testGetAllProducts() {
        when(mockedRepository.findAll()).thenReturn(products);
        List<Product> allProducts = service.getProducts();

        assertEquals(products, allProducts);
        assertEquals(3, allProducts.size());
    }

    @Test
    public void testGetActiveProducts() {
        when(mockedRepository.findByActive(eq(true))).thenReturn(activeProducts);
        List<Product> products = service.getActiveProducts(true);

        assertEquals(activeProducts, products);
        assertEquals(2, products.size());
    }

}