package com.example.controller.customer;

import com.example.dto.RestaurantDTO;
import com.example.model.Restaurant;
import com.example.model.User;
import com.example.service.restaurant.RestaurantService;
import com.example.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class CustomerRestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    public CustomerRestaurantController(RestaurantService restaurantService,
                                        UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestParam String keyword){
        List<Restaurant> restaurantList = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurantList = restaurantService.restaurantList(user);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@PathVariable Long id) throws  Exception{
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/add-favorites/{id}")
    public ResponseEntity<RestaurantDTO> addtoFavorites(@PathVariable Long id,
                                                        @RequestHeader("Authorization") String jwt)
    throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDTO restaurantDTO = restaurantService.addtoFavourites(id,user);
        return new ResponseEntity<>(restaurantDTO,HttpStatus.OK);
    }
}
