package com.Flytosky.dto;

import jakarta.persistence.Column;
import lombok.Data;
import java.util.List;

@Data
public class RestaurantDto {

    private Long id;
    private String title;
    private String description;

    @Column(length = 1000)
    private List<String> images;
}
