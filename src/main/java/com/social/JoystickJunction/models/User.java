package com.social.JoystickJunction.models;

import com.social.JoystickJunction.common.dto.request.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Collation("user")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private long createdAt;
    private byte[] picture;
    private String picturePath;
    private List<String> friends = new ArrayList<>();

    public User(RegisterRequest user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.createdAt = System.currentTimeMillis();
        this.picturePath = user.getPicturePath();
    }
}
