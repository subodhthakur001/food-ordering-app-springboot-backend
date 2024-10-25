package com.example.controller;

import com.example.constants.ApiConstant;
import com.example.model.Restaurant;
import com.example.model.User;
import com.example.request.restaurant.CreateRestaurantRequest;
import com.example.response.base.BaseResponse;
import com.example.service.restaurant.RestaurantService;
import com.example.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    @Autowired
    public AdminRestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createRestaurant(@Valid @RequestBody CreateRestaurantRequest request,
                                                         @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Restaurant savedRestaurant = restaurantService.createRestaurant(request,user);
        BaseResponse response = new BaseResponse(savedRestaurant, ApiConstant.SAVE_MESSAGE);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("{/id}")
    public ResponseEntity<BaseResponse> updateRestaurant(@PathVariable Long restaurantId,
                                                         @RequestBody CreateRestaurantRequest request)throws Exception {

        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurantId,request);
        BaseResponse response = new BaseResponse(updatedRestaurant, ApiConstant.UPDATE_MESSAGE);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("{/id}")
    public ResponseEntity<BaseResponse> deleteRestaurant(@PathVariable Long restaurantId) throws Exception {
        Restaurant deletedRestaurant = restaurantService.deleteRestaurant(restaurantId);
        BaseResponse response = new BaseResponse(deletedRestaurant, ApiConstant.DELETE_MESSAGE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getRestaurantList(){
        List<Restaurant> restaurantList = restaurantService.restaurantList();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }
}
