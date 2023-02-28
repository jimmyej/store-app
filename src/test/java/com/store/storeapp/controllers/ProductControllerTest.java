package com.store.storeapp.controllers;

import com.store.storeapp.entities.Product;
import com.store.storeapp.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
@WithMockUser(username = "user", authorities={"ROLE_USER", "ROLE_ADMIN"})
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    private List<Product> products = Arrays.asList(
            new Product(1, "Product 1", 10.0, true),
            new Product(2, "Product 2", 20.0, false),
            new Product(3, "Product 3", 30.0, true)
    );

    private List<Product> activeProducts = products.stream().filter(f -> f.isActive()).collect(Collectors.toList());

    private List<Product> inactiveProducts = products.stream().filter(f -> !f.isActive()).collect(Collectors.toList());

    private static final String BASE_PATH = "/api/products/v1";

    @BeforeEach
    void setUp() {
    }

    @Test
    void getProducts_success() throws Exception {
        when(productService.getProducts()).thenReturn(products);
        RequestBuilder request = get(BASE_PATH);
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getProducts_noContent() throws Exception {
        when(productService.getProducts()).thenReturn(Arrays.asList());
        RequestBuilder request = get(BASE_PATH);
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void getProducts_serverError() throws Exception {
        when(productService.getProducts()).thenThrow(new RuntimeException());
        RequestBuilder request = get(BASE_PATH);
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void getProductById_success() throws Exception {
        when(productService.getProductById(1)).thenReturn(Optional.of(products.get(0)));
        RequestBuilder request = get(BASE_PATH.concat("/1"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getProductById_notFound() throws Exception {
        when(productService.getProductById(1)).thenReturn(Optional.empty());
        RequestBuilder request = get(BASE_PATH.concat("/1"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void getProductById_serverError() throws Exception {
        when(productService.getProductById(1)).thenThrow(new RuntimeException());
        RequestBuilder request = get(BASE_PATH.concat("/1"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void createProduct_success() throws Exception {
        String newProductJson = "{\"name\":\"Soda CocaCola 1.5L\",\"price\":8.0,\"active\":true}";

        when(productService.createProduct(any(Product.class))).thenReturn(products.get(0));
        RequestBuilder request = post(BASE_PATH)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON).content(newProductJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "admin", authorities={"ROLE_ADMIN"})
    void createProduct_serverError() throws Exception {
        String newProductJson = "{\"name\":\"Soda CocaCola 1.5L\",\"price\":8.0,\"active\":true}";

        when(productService.createProduct(any(Product.class))).thenThrow(new RuntimeException());
        RequestBuilder request = post(BASE_PATH)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON).content(newProductJson)
                .contentType(MediaType.APPLICATION_JSON);;

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void updateProduct_success() throws Exception {
        String newProductJson = "{\"id\":1,\"name\":\"Soda CocaCola 1.5L\",\"price\":8.0,\"active\":true}";

        when(productService.updateProduct(any(Product.class))).thenReturn(products.get(0));
        RequestBuilder request = put(BASE_PATH)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON).content(newProductJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void updateProduct_serverError() throws Exception {
        String newProductJson = "{\"id\":1,\"name\":\"Soda CocaCola 1.5L\",\"price\":8.0,\"active\":true}";

        when(productService.updateProduct(any(Product.class))).thenThrow(new RuntimeException());
        RequestBuilder request = put(BASE_PATH)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON).content(newProductJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void deleteProduct_success() throws Exception {
        when(productService.deleteProductById(1)).thenReturn(true);
        RequestBuilder request = delete(BASE_PATH.concat("/1"))
                .with(SecurityMockMvcRequestPostProcessors.csrf());
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void deleteProduct_serverError() throws Exception {
        when(productService.deleteProductById(1)).thenThrow(new RuntimeException());
        RequestBuilder request = delete(BASE_PATH.concat("/1"))
                .with(SecurityMockMvcRequestPostProcessors.csrf());
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void getActiveProducts_success() throws Exception {
        when(productService.getActiveProducts(true)).thenReturn(activeProducts);
        RequestBuilder request = get(BASE_PATH.concat("/actives/true"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getInactiveProducts_success() throws Exception {
        when(productService.getActiveProducts(false)).thenReturn(inactiveProducts);
        RequestBuilder request = get(BASE_PATH.concat("/actives/false"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void getActiveProducts_notFound() throws Exception {
        when(productService.getActiveProducts(true)).thenReturn(Arrays.asList());
        RequestBuilder request = get(BASE_PATH.concat("/actives/true"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void getInactiveProducts_notFound() throws Exception {
        when(productService.getActiveProducts(false)).thenReturn(Arrays.asList());
        RequestBuilder request = get(BASE_PATH.concat("/actives/false"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    void getActiveProducts_serverError() throws Exception {
        when(productService.getActiveProducts(true)).thenThrow(new RuntimeException());
        RequestBuilder request = get(BASE_PATH.concat("/actives/true"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    @Test
    void getInactiveProducts_serverError() throws Exception {
        when(productService.getActiveProducts(false)).thenThrow(new RuntimeException());
        RequestBuilder request = get(BASE_PATH.concat("/actives/false"));
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }
}