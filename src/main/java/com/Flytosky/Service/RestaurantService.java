package com.Flytosky.Service;

import com.Flytosky.dto.RestaurantDto;
import com.Flytosky.model.Restaurant;
import com.Flytosky.model.User;
import com.Flytosky.request.CreateRestaurantRequest;

import java.util.List;

// Service interface for managing restaurants.

public interface RestaurantService {

    //Creates a new restaurant owned by the given user.
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    // Updates a restaurant by ID.
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

    // Deletes a restaurant by ID
    public void deleteRestaurant(Long restaurantId) throws Exception;

    //* Retrieves all restaurants.
    public   List<Restaurant> getAllRestaurant();

    // Searches restaurants by keyword.
    public List<Restaurant> searchRestaurant(String keyword);

    // Finds a restaurant by ID.

    public Restaurant findRestaurantById(Long id) throws Exception;

    //Gets the restaurant owned by a specific user.
    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    //Adds or removes a restaurant from user's favorites.
    public   RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    //Toggles open/close status of a restaurant.
    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
