package com.store.storeapp.controllers;

import com.store.storeapp.entities.Product;
import com.store.storeapp.entities.Role;
import com.store.storeapp.entities.User;
import com.store.storeapp.models.UserModel;
import com.store.storeapp.services.UserService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@WithMockUser(username = "user", authorities={"ROLE_USER", "ROLE_ADMIN"})
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private static final String BASE_PATH = "/api/users/v1";

    private String newUserJson = "{\"userId\":10,\"username\":\"admin\",\"password\":\"$2a$10$sAW4AnArgKbePWD36LJTiOpjAbHOK/HQZiQvnP/s4L9YoJc5RdOg2\",\"email\":\"admin@gmail.com\",\"firstname\":\"Admin\",\"lastname\":\"User\",\"status\":true,\"roles\":[{\"roleId\":4,\"roleName\":\"ROLE_USER\"}]}";
    private User user = new User(
            1,
            "user",
            "2343vxdvdsg36bdfhdgh",
            "user@gmail.com",
            "user",
            "test",
            true,
            Arrays.asList(new Role(1,"ROLE_USER")));

    @Test
    void createUser_success() throws Exception {
        when(userService.register(any(UserModel.class))).thenReturn(user);
        RequestBuilder request = post(BASE_PATH)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON).content(newUserJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "admin", authorities={"ROLE_ADMIN"})
    void createProduct_serverError() throws Exception {
        when(userService.register(any(UserModel.class))).thenThrow(new RuntimeException());
        RequestBuilder request = post(BASE_PATH)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON).content(newUserJson)
                .contentType(MediaType.APPLICATION_JSON);;

        MvcResult result = mvc.perform(request).andReturn();
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

}