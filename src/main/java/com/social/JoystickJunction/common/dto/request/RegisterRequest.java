package com.social.JoystickJunction.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private String location;
    private String occupation;
    private MultipartFile picture;
    private String picturePath;
}
