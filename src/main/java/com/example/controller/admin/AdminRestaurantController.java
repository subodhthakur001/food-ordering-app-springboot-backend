package com.example.controller.admin;

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

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateRestaurant(@PathVariable Long id,
                                                         @RequestBody CreateRestaurantRequest request)throws Exception {

        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id,request);
        BaseResponse response = new BaseResponse(updatedRestaurant, ApiConstant.UPDATE_MESSAGE);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteRestaurant(@PathVariable Long id) throws Exception {
        Restaurant deletedRestaurant = restaurantService.deleteRestaurant(id);
        BaseResponse response = new BaseResponse(deletedRestaurant, ApiConstant.DELETE_MESSAGE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getRestaurantList(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurantList = restaurantService.restaurantList(user);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @PutMapping("status/{id}")
    public ResponseEntity<BaseResponse> updateRestaurantStatus(@PathVariable Long id) throws Exception
    {
        Restaurant updatedRestaurant = restaurantService.updateRestaurantStatus(id);
        BaseResponse response = new BaseResponse(updatedRestaurant, "restaurant status updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<BaseResponse> findRestaurantByUserId(@RequestHeader("Authorization") String jwt) throws Exception
    {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantByUserId(user.getId());
        BaseResponse response = new BaseResponse(restaurant,ApiConstant.LIST_MESSAGE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
