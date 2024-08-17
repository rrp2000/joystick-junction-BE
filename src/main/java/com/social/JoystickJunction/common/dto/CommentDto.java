package com.social.JoystickJunction.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommentDto {
    private String userId;
    private String comment;
    private List<String> likes;
}
