package com.example.service.user;

import com.example.model.USER_ROLE;
import com.example.model.User;
import com.example.dao.UserRepository;
import com.example.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws EntityNotFoundException {
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }
        String email = jwtUtil.extractUsername(jwt);
        User user =  userRepository.findByEmail(email);
        if(user == null){
            throw new EntityNotFoundException("User not found with this token");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws EntityNotFoundException {
        User user =  userRepository.findByEmail(email);

        if(user == null){
            throw new EntityNotFoundException("User not found with this email " + email);
        }
        return user;
    }

    @Override
    public USER_ROLE findRoleByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new EntityNotFoundException("User not found with this email " + email);
        }
        return user.getRole();
    }
}
