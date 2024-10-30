package com.example.request.restaurant;

import com.example.model.Address;
import com.example.model.ContactInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurantRequest {
    private Long id;
    @NotBlank(message = "name of the restaurant should not be blank")
    private String name;
    @NotBlank(message = "description of the restaurant should not be blank")
    private String description;
    @NotBlank(message = "cuisineType of the restaurant should not be blank")
    @JsonProperty("cuisine_type")
    private String cuisineType;
    @NotNull(message = "address of the restaurant should not be blank")
    private Address address;
    @NotNull(message = "contact Information of the restaurant should not be blank")
    @JsonProperty("contact_information")
    private ContactInformation contactInformation;
    @NotBlank(message = "openingHours of the restaurant should not be blank")
    @JsonProperty("opening_hours")
    private String openingHours;
    @NotNull(message = "openingHours of the restaurant should not be blank")
    private Boolean open;
    @NotNull(message = "images of the restaurant should not be blank")
    private List<String> images;
}
