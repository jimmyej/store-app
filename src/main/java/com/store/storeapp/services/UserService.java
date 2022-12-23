package com.store.storeapp.services;

import com.store.storeapp.entities.User;
import com.store.storeapp.models.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(UserModel model);
}
