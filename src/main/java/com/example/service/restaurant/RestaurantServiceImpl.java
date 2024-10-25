package com.example.service.restaurant;

import com.example.dao.AddressRepository;
import com.example.dao.RestaurantRepository;
import com.example.dto.RestaurantDTO;
import com.example.model.Address;
import com.example.model.Restaurant;
import com.example.model.User;
import com.example.request.restaurant.CreateRestaurantRequest;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class RestaurantServiceImpl implements RestaurantService{
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, AddressRepository addressRepository) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) throws Exception {
        Address savedAddress = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setImages(req.getImages());
        restaurant.setAddress(savedAddress);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateReq) throws Exception {
        Restaurant isRestaurantExist = findRestaurantById(restaurantId);
        if(updateReq.getName() != null){
            isRestaurantExist.setName(updateReq.getName());
        }
        if(updateReq.getDescription() != null){
            isRestaurantExist.setDescription(updateReq.getDescription());
        }
        if(updateReq.getCuisineType() != null){
            isRestaurantExist.setCuisineType(updateReq.getCuisineType());
        }
        return restaurantRepository.save(isRestaurantExist);
    }

    @Override
    public Restaurant deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant isRestaurantExist = findRestaurantById(restaurantId);
        restaurantRepository.delete(isRestaurantExist);
    }

    @Override
    public List<Restaurant> restaurantList() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findByQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);
        if(opt.isEmpty()){
            throw new EntityNotFoundException("Restaurant does not exist with id : " + restaurantId);
        }
        return opt.get();
    }

    @Override
    public Restaurant findRestaurantByUserId(Long userId) throws Exception {
        return restaurantRepository.findByOwnerId(userId);
    }

    @Override
    public RestaurantDTO addtoFavourites(Long restaurantId, User user) throws Exception {
         Restaurant restaurant = findRestaurantById(restaurantId);

         RestaurantDTO dto = new RestaurantDTO();
         dto.setId(restaurant.getId());
         dto.setTitle(restaurant.getName());
         dto.setDescription(restaurant.getDescription());
         dto.setImages(restaurant.getImages());

         if(user.getFavourites().contains(dto)){
             user.getFavourites().remove(dto);
         }
         else{
             user.getFavourites().add(dto);
         }
         return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        Boolean status = restaurant.getOpen();
        restaurant.setOpen(!status);
        return restaurantRepository.save(restaurant);
    }
}
