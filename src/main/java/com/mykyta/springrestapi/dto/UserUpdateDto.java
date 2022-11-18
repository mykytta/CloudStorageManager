package com.mykyta.springrestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mykyta.springrestapi.model.User;
import lombok.Data;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserUpdateDto {
    private Long id;
    private String username;

    private String password;


    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }

    public static UserUpdateDto fromUser(User user) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setId(user.getId());
        userUpdateDto.setUsername(user.getUsername());
        userUpdateDto.setPassword(user.getPassword());

        return userUpdateDto;
    }
}
