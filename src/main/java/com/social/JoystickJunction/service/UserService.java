package com.social.JoystickJunction.service;

import com.social.JoystickJunction.common.dto.request.LoginRequest;
import com.social.JoystickJunction.common.dto.request.RegisterRequest;
import com.social.JoystickJunction.common.dto.response.BaseResponse;
import com.social.JoystickJunction.exception.UserServiceException;
import com.social.JoystickJunction.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    BaseResponse saveUserDetails(RegisterRequest user) throws UserServiceException;

    BaseResponse login(LoginRequest loginRequest);

    BaseResponse fetchUserWithId(String userId);

    BaseResponse addFriend(String userId, String friendUserId);

    BaseResponse removeFriend(String userId, String friendUserId);

    UserDetails loadUserByUsername(String email);

}
