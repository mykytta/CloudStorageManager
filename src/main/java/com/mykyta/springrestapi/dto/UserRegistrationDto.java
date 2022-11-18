package com.mykyta.springrestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mykyta.springrestapi.model.Status;
import com.mykyta.springrestapi.model.User;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserRegistrationDto {
    private Long id;
    private String username;

    private String password;

    private String status;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus(Status.valueOf(status));

        return user;
    }

    public static UserRegistrationDto fromUser(User user) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setId(user.getId());
        userRegistrationDto.setUsername(user.getUsername());
        userRegistrationDto.setPassword(user.getPassword());
        userRegistrationDto.setStatus(user.getStatus().name());

        return userRegistrationDto;
    }
}
