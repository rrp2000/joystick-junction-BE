package com.social.JoystickJunction.controller;

import com.social.JoystickJunction.common.dto.request.LoginRequest;
import com.social.JoystickJunction.common.dto.request.RegisterRequest;
import com.social.JoystickJunction.common.dto.response.BaseResponse;
import com.social.JoystickJunction.config.JwtProvider;
import com.social.JoystickJunction.models.User;
import com.social.JoystickJunction.service.UserService;
import com.social.JoystickJunction.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.social.JoystickJunction.common.constants.RouteConstants.*;
import static com.social.JoystickJunction.common.constants.RoutePathVariables.FRIEND_USER_ID;
import static com.social.JoystickJunction.common.constants.RoutePathVariables.USER_ID;

@RestController
@RequestMapping(USER_BASE_ROUTE)
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = SIGNUP, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> createUser(
            @ModelAttribute RegisterRequest user
    ){
       BaseResponse baseResponse = userService.saveUserDetails(user);
        return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<BaseResponse> login(
            @RequestBody LoginRequest loginRequest
    ){
       BaseResponse baseResponse = userService.login(loginRequest);
       return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @GetMapping(API + USER_ID_PATH_VARIABLE)
    public ResponseEntity<BaseResponse> fetchUserWithId(
            @PathVariable(USER_ID) String userId
    ){
        BaseResponse baseResponse = userService.fetchUserWithId(userId);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PutMapping(API + ADD + USER_ID_PATH_VARIABLE + FRIEND_USER_ID_PATH_VARIABLE)
    public ResponseEntity<BaseResponse> addFriend(
            @PathVariable(USER_ID) String userId,
            @PathVariable(FRIEND_USER_ID) String friendUserId
    ){
        BaseResponse baseResponse = userService.addFriend(userId, friendUserId);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PutMapping(API + REMOVE + USER_ID_PATH_VARIABLE + FRIEND_USER_ID_PATH_VARIABLE)
    public ResponseEntity<BaseResponse> removeFriend(
            @PathVariable(USER_ID) String userId,
            @PathVariable(FRIEND_USER_ID) String friendUserId
    ){
        BaseResponse baseResponse = userService.removeFriend(userId, friendUserId);
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }


}
