package com.store.storeapp.controllers;

import com.store.storeapp.entities.Product;
import com.store.storeapp.entities.User;
import com.store.storeapp.models.UserModel;
import com.store.storeapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users/v1")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(value = "")
    ResponseEntity<User> createUser(@RequestBody UserModel userModel){
        try {
            User newUser = service.register(userModel);
            log.info("User created");
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error while creating user");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
