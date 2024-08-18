package com.social.JoystickJunction.common.dto.response;

import com.social.JoystickJunction.models.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String location;
    private String occupation;
    private Integer viewedProfile = 847;
    private Integer impressions = 243;
    private long createdAt;
    private List<User> friends = new ArrayList<>();

    public UserResponseDto(User user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.location = user.getLocation();
        this.occupation = user.getOccupation();
        this.createdAt = user.getCreatedAt();
    }
}
