package com.example.service.restaurant;

import com.example.dto.RestaurantDTO;
import com.example.model.Restaurant;
import com.example.model.User;
import com.example.request.restaurant.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) throws Exception;

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateReq) throws Exception;

    public Restaurant deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> restaurantList(User user) throws Exception;

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant findRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDTO addtoFavourites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
