package com.Flytosky.request;

import com.Flytosky.model.Address;
import com.Flytosky.model.ContactInformation;
import lombok.Data;

import java.util.List;


@Data
public class CreateRestaurantRequest {


    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openHours;
    private List<String> images;
    private int registrationDate;



}
