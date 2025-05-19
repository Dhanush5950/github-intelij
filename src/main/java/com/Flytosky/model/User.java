package com.Flytosky.model;


import com.Flytosky.dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  id;

    @NotBlank(message = "Full name is required")
    private String fullName;


    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;


    @NotBlank(message = "Password is required")
    @JsonIgnore
    private String password;

    @NotNull(message = "Role must not be null")
    private USER_ROLE role= USER_ROLE.ROLE_CUSTOMER;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto>favourites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address>addresses = new ArrayList<>();

}
