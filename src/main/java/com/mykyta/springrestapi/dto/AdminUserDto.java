package com.mykyta.springrestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mykyta.springrestapi.model.Event;
import com.mykyta.springrestapi.model.Status;
import com.mykyta.springrestapi.model.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdminUserDto {
    private Long id;
    private String username;
    private String status;

    private List<EventDto> events;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        List<Event> eventList = events.stream()
                .map(EventDto::toEvent).collect(Collectors.toList());
        user.setEvents(eventList);
        user.setStatus(Status.valueOf(status));

        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUsername(user.getUsername());
        List<EventDto> eventDtos = user.getEvents().stream()
                        .map(EventDto::fromEvent).collect(Collectors.toList());
        adminUserDto.setEvents(eventDtos);
        adminUserDto.setStatus(user.getStatus().name());

        return adminUserDto;
    }
}
