package com.social.JoystickJunction.common.dto.response;

import lombok.Data;

@Data
public class BaseResponse {
    private String message;
    private Object payload;
    private boolean success;
}