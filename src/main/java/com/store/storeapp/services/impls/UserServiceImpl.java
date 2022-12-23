package com.store.storeapp.services.impls;

import com.store.storeapp.entities.Role;
import com.store.storeapp.entities.User;
import com.store.storeapp.models.UserModalDetails;
import com.store.storeapp.models.UserModel;
import com.store.storeapp.repositories.RoleRepository;
import com.store.storeapp.repositories.UserRepository;
import com.store.storeapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User register(UserModel model) {
        String roleName = "ROLE_USER";
        Role role = new Role(roleName);//to create a role
        if(roleRepository.existsByRoleName(roleName))
            role = roleRepository.findByRoleName(roleName).get();//to update a role
        User user = new User();
        user.setUsername(model.getUsername());
        user.setPassword(passwordEncoder().encode(model.getPassword()));
        user.setEmail(model.getEmail());
        user.setFirstname(model.getFirstname());
        user.setLastname(model.getLastname());
        user.setStatus(model.getStatus());
        user.setRoles(Arrays.asList(role));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                //.map(user -> new UserModalDetails(user))
                .map(UserModalDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: "+ username));
    }
}
