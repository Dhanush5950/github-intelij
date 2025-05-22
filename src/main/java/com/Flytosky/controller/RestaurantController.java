package com.Flytosky.controller;


import com.Flytosky.Service.RestaurantService;
import com.Flytosky.Service.UserService;
import com.Flytosky.dto.RestaurantDto;
import com.Flytosky.model.Restaurant;
import com.Flytosky.model.User;
import com.Flytosky.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;


    // ✅ Search restaurants by keyword with a distinct path
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    // ✅ Get all restaurants
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    // ✅ Get restaurant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addToFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id

    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        RestaurantDto restaurant = restaurantService.addToFavorites(id,user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}

