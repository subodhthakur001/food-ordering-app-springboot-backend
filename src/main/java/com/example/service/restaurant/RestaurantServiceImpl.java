package com.example.service.restaurant;

import com.example.dao.AddressRepository;
import com.example.dao.RestaurantRepository;
import com.example.dao.UserRepository;
import com.example.dto.RestaurantDTO;
import com.example.model.Address;
import com.example.model.Restaurant;
import com.example.model.USER_ROLE;
import com.example.model.User;
import com.example.request.restaurant.CreateRestaurantRequest;
import com.example.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 AddressRepository addressRepository,
                                 UserService userService,
                                 UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) throws Exception {
        if (restaurantRepository.findByOwnerId(user.getId()) != null) {
            throw new Exception("User already has a restaurant.");
        }
        Address savedAddress = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setName(req.getName());
        restaurant.setOwner(user);
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOpen(req.getOpen());
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
        return isRestaurantExist;
    }

    @Override
    public List<Restaurant> restaurantList(User user) throws Exception {
        USER_ROLE role = userService.findRoleByEmail(user.getEmail());
        if(role.toString().equals("ROLE_RESTAURANT_OWNER")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"you are not authorized to access this resource");
        }
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

         List<RestaurantDTO> favorites = user.getFavourites();
         boolean flag = false;
         for(RestaurantDTO favourite : favorites){
             if(favourite.getId().equals(restaurantId)){
                 flag = true;
                 break;
             }
         }
         if(flag){
             favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
         }
         else{
             favorites.add(dto);
         }
         userRepository.save(user);
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
