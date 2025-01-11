package com.social.JoystickJunction.common.dto.request;

import com.social.JoystickJunction.common.dto.CommentDto;
import com.social.JoystickJunction.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PostDto {
    private String userId;
    private String description;
    private String picturePath;
    private MultipartFile picture;

    public PostDto(User userDetails){
        this.userId = userDetails.getId();
    }
}
