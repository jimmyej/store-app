package com.store.storeapp.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 10)
    private String username;
    @NotBlank
    //@Size(min = 10, max = 50)
    private String password;
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;
    private String firstname;
    private String lastname;
    private Set<String> roles;
}
