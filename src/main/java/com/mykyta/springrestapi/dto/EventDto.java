package com.mykyta.springrestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mykyta.springrestapi.model.Event;
import com.mykyta.springrestapi.model.Status;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EventDto {
    private Long id;

    private String eventName;

    private UserDto user;

    private FileDto file;

    private Status status;

    public Event toEvent(){
        Event event = new Event();
        event.setId(id);
        event.setEventName(eventName);
        event.setFile(file.toFile());
        event.setUser(user.toUser());
        event.setStatus(status);

        return event;
    }

    public static EventDto fromEvent(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setEventName(event.getEventName());
        eventDto.setUser(UserDto.fromUser(event.getUser()));
        eventDto.setFile(FileDto.fromFile(event.getFile()));
        eventDto.setStatus(event.getStatus());

        return eventDto;
    }
}
