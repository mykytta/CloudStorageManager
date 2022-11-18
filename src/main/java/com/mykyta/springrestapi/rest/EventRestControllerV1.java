package com.mykyta.springrestapi.rest;

import com.mykyta.springrestapi.dto.EventDto;
import com.mykyta.springrestapi.model.Event;
import com.mykyta.springrestapi.model.Status;
import com.mykyta.springrestapi.service.EventService;
import com.mykyta.springrestapi.service.FileService;
import com.mykyta.springrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/events")
public class EventRestControllerV1 {

    private final EventService eventService;

    private final UserService userService;

    private final FileService fileService;

    @Autowired
    public EventRestControllerV1(EventService eventService, UserService userService, FileService fileService) {
        this.eventService = eventService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<?> getEventById(@PathVariable(name = "id") Long id){
        if(eventService.getById(id).isPresent()){
            Event event = eventService.getById(id).get();
            return new ResponseEntity<>(EventDto.fromEvent(event), HttpStatus.OK);
        }

        return new ResponseEntity<>("We cannot find this event, please try to enter the correct id", HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<List<EventDto>> getAllEvents(){
        var events = eventService.getAll().stream()
                .map(EventDto::fromEvent).collect(Collectors.toList());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<?> createEvent(@RequestBody EventDto event){
        event.setStatus(Status.ACTIVE);
        if(fileService.findById(event.getFile().getId()) == null){
            return new ResponseEntity<>("We cant find file with this id", HttpStatus.NOT_FOUND);
        }
        if(userService.findById(event.getUser().getId()).isEmpty()){
            return new ResponseEntity<>("We cant find user with this id", HttpStatus.NOT_FOUND);
        }
        eventService.create(event.toEvent());
        return new ResponseEntity<>(event.toEvent(), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<?> updateEvent(@RequestBody EventDto event, @PathVariable(name = "id") Long id){
        Optional<Event> eventToUpdate = eventService.getById(id);
        if(eventToUpdate.isPresent()){
            Event eventUpdate = event.toEvent();
            eventUpdate.setUser(event.getUser().toUser());
            eventUpdate.setEventName(event.getEventName());
            eventUpdate.setFile(event.getFile().toFile());
            eventService.update(eventUpdate);
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
        return new ResponseEntity<>("We cannot find this event, please try to enter the correct id", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<?> deleteUserById(@PathVariable(name = "id") Long id){
        Optional<Event> event = eventService.getById(id);
        if(event.isPresent()){
            Event eventToDelete = event.get();
            eventToDelete.setStatus(Status.DELETED);
            eventService.update(event.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("We cannot find this event, please try to enter the correct id", HttpStatus.NOT_FOUND);
    }
}
