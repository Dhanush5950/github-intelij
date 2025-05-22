package com.Flytosky.Service;

import com.Flytosky.dto.RestaurantDto;
import com.Flytosky.model.Address;
import com.Flytosky.model.Restaurant;
import com.Flytosky.model.User;
import com.Flytosky.repository.AddressRepository;
import com.Flytosky.repository.RestaurantRepository;
import com.Flytosky.repository.UserRepository;
import com.Flytosky.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpenHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRequest) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updateRequest.getCuisineType());
        }

        if (restaurant.getDescription() != null) {
            restaurant.setDescription(updateRequest.getDescription());
        }

        if (restaurant.getName() != null) {
            restaurant.setName(updateRequest.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new Exception("Restaurant not found with id " + id));
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found with owner id " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setId(restaurantId);
        dto.setTitle(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());

        if (user.getFavourites().contains(dto)) {
            user.getFavourites().remove(dto);
        } else {
            user.getFavourites().add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
