package com.mykyta.springrestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mykyta.springrestapi.model.User;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDto {
    private Long id;
    private String username;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        return userDto;
    }
}
