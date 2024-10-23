package com.example.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Embeddable
public class RestaurantDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    @Column(length = 1000)
    private List<String> images;

}
