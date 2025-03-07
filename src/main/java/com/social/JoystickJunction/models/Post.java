package com.social.JoystickJunction.models;

import com.social.JoystickJunction.common.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Collation("post")
public class Post {
    @Id
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String description;
    private String picturePath;
    private byte[] picture;
    private byte[] usePicture;
    private Map<String, Boolean> likes = new HashMap<>();
    private List<CommentDto> comments = new ArrayList<>();

    public Post(User userDetails){
        this.userId = userDetails.getId();
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
        this.usePicture = userDetails.getPicture();
    }
}
