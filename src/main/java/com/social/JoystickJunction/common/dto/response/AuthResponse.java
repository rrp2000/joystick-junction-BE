package com.social.JoystickJunction.common.dto.response;

import com.social.JoystickJunction.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private User userDetails;
}
