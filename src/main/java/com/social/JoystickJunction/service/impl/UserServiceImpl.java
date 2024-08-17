package com.social.JoystickJunction.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.JoystickJunction.common.dto.request.LoginRequest;
import com.social.JoystickJunction.common.dto.request.RegisterRequest;
import com.social.JoystickJunction.common.dto.response.AuthResponse;
import com.social.JoystickJunction.common.dto.response.BaseResponse;
import com.social.JoystickJunction.common.dto.response.UserResponseDto;
import com.social.JoystickJunction.config.JwtProvider;
import com.social.JoystickJunction.exception.UserServiceException;
import com.social.JoystickJunction.models.User;
import com.social.JoystickJunction.repository.UserRepository;
import com.social.JoystickJunction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public BaseResponse saveUserDetails(RegisterRequest user) throws UserServiceException {
        BaseResponse baseResponse = new BaseResponse();

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new UserServiceException("First name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new UserServiceException("Last name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new UserServiceException("Email cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserServiceException("Password cannot be empty", HttpStatus.BAD_REQUEST);
        }

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new UserServiceException("User already exist with email: " + user.getEmail(), HttpStatus.BAD_REQUEST);
        }

        User createdUser = new User(user);
        try{
            createdUser.setPicture(user.getPicture().getBytes());
        } catch (Exception e) {
            System.out.println(e);
        }
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(createdUser);

        baseResponse.setMessage("signup successful");
        baseResponse.setPayload(savedUser);
        baseResponse.setSuccess(true);
        return baseResponse;
    }

    @Override
    public BaseResponse login(LoginRequest loginRequest) {
        BaseResponse baseResponse = new BaseResponse();

        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        Optional<User> userDetails = userRepository.findByEmail(loginRequest.getEmail());

        if(userDetails.isEmpty()){
            throw new UserServiceException("User not found with Email: "+ loginRequest.getEmail(), HttpStatus.NOT_FOUND);
        }

        baseResponse.setMessage("Login successful");
        baseResponse.setPayload(new AuthResponse(token, userDetails.get()));
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public BaseResponse fetchUserWithId(String userId) {
        BaseResponse baseResponse = new BaseResponse();

        Optional<User> userDetailsOpt = userRepository.findById(userId);

        if(userDetailsOpt.isEmpty()){
            throw new UserServiceException("User not found with user Id: "+ userId, HttpStatus.NOT_FOUND);
        }

        User userDetails = userDetailsOpt.get();

        UserResponseDto userResponse = new UserResponseDto(userDetails);

        List<User> userFriendsList = new ArrayList<>();
        userDetails.getFriends().forEach( friendId -> {
            Optional<User> userFriendDetailsOpt = userRepository.findById(friendId);
            userFriendDetailsOpt.ifPresent(userFriendsList::add);
        });
        userResponse.setFriends(userFriendsList);

        baseResponse.setMessage("User details fetched successfully");
        baseResponse.setPayload(userResponse);
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public BaseResponse addFriend(String userId, String friendUserId) {
        BaseResponse baseResponse = new BaseResponse();

        Optional<User> userDetails = userRepository.findById(userId);
        if(userDetails.isEmpty()){
            throw new UserServiceException("User not found with user Id: "+ userId, HttpStatus.NOT_FOUND);
        }

        Optional<User> userFriendDetailsOpt = userRepository.findById(friendUserId);
        if(userFriendDetailsOpt.isEmpty()){
            throw new UserServiceException("User not found with user Id: "+ friendUserId, HttpStatus.NOT_FOUND);
        }

        userDetails.get().getFriends().add(friendUserId);

        baseResponse.setMessage("Friend added successfully");
        baseResponse.setPayload(userDetails.get());
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public BaseResponse removeFriend(String userId, String friendUserId) {
        BaseResponse baseResponse = new BaseResponse();

        Optional<User> userDetails = userRepository.findById(userId);
        if(userDetails.isEmpty()){
            throw new UserServiceException("User not found with user Id: "+ userId, HttpStatus.NOT_FOUND);
        }

        Optional<User> userFriendDetailsOpt = userRepository.findById(friendUserId);
        if(userFriendDetailsOpt.isEmpty()){
            throw new UserServiceException("User not found with user Id: "+ friendUserId, HttpStatus.NOT_FOUND);
        }

        List<String> newFriendList = userDetails.get().getFriends()
                .stream().filter(friendId -> !Objects.equals(friendId, friendUserId)).toList();

        userDetails.get().setFriends(newFriendList);

        baseResponse.setMessage("Friend removed successfully");
        baseResponse.setPayload(userDetails.get());
        baseResponse.setSuccess(true);

        return baseResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserServiceException("User not found with email: " + email, HttpStatus.NOT_FOUND);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = loadUserByUsername(email);
        if (userDetails == null) {
            throw new UserServiceException("Invalid credential", HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserServiceException("Invalid credential", HttpStatus.BAD_REQUEST);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
