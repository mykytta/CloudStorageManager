package com.mykyta.springrestapi.rest;

import com.mykyta.springrestapi.dto.AdminUserDto;
import com.mykyta.springrestapi.dto.EventDto;
import com.mykyta.springrestapi.dto.UserRegistrationDto;
import com.mykyta.springrestapi.model.*;
import com.mykyta.springrestapi.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class EventRestControllerV1Test {

    @MockBean
    private EventService eventService;

    @Autowired
    private EventRestControllerV1 eventRestControllerV1;

    @Test
    void getEventById() {
        Optional<Event> eventToTest = Optional.of(generateRandomEvent());
        when(eventService.getById(2L)).thenReturn(eventToTest);
        EventDto result = EventDto.fromEvent(eventToTest.get());
        ResponseEntity<?> test = eventRestControllerV1.getEventById(2L);
        assertNotNull(test);
        assertEquals(result, test.getBody());
        assertEquals("We cannot find this event, please try to enter the correct id",eventRestControllerV1.getEventById(1L).getBody());
    }

    @Test
    void getAllEvents() {
        when(eventService.getAll()).thenReturn(List.of(generateRandomEvent(),
                generateRandomEvent()));
        assertEquals(2, eventRestControllerV1.getAllEvents().getBody().size());
        assertNotNull(eventRestControllerV1.getAllEvents());    }

    @Test
    void createEvent() {
        Event testEvent = generateRandomEvent();
        when(eventService.create(testEvent)).thenReturn(testEvent);
        Event actual = (Event) eventRestControllerV1.createEvent(EventDto.fromEvent(testEvent)).getBody();

        assertNotNull(actual);
        assertEquals(EventDto.fromEvent(testEvent), EventDto.fromEvent(actual));
    }

    @Test
    void updateEvent() {
        Event withoutChangesExpected = generateRandomEvent();
        given(eventService.getById(1L)).willReturn(Optional.of(withoutChangesExpected));
        given(eventService.create(withoutChangesExpected)).willReturn(withoutChangesExpected);
        EventDto withoutChangesActual = (EventDto) eventRestControllerV1.getEventById(1L).getBody();

        assertNotNull(withoutChangesActual);
        assertEquals(EventDto.fromEvent(withoutChangesExpected), withoutChangesActual);

        String eventName = "Uploading";
        withoutChangesActual.setEventName(eventName);
        eventService.update(withoutChangesActual.toEvent());
        assertEquals(eventName, withoutChangesActual.getEventName());
    }

    @Test
    void deleteUserById() {
        Event expected = generateRandomEvent();
        when(eventService.getById(1L)).thenReturn(Optional.of(expected));
        eventRestControllerV1.deleteEventById(1L);
        assertEquals(Status.DELETED, expected.getStatus());
    }

    private static Event generateRandomEvent(){
        String eventName = UUID.randomUUID().toString();
        String fileName = UUID.randomUUID().toString();
        String username = UUID.randomUUID().toString();
        User userToTest = new User(username, "strongPassword",
                List.of(new Event(
                        fileName,
                        new User(),
                        new File()
                )), List.of(new Role()));
        userToTest.setId(1L);
        userToTest.setStatus(Status.ACTIVE);
        File file = new File("cat", "/Users/niqitagrigorivich/Downloads/REST-API-JWT/src/test/java/com/mykyta/springrestapi/filetotest/cat.jpg");
        file.setId(1L);
        Event eventToTest = new Event(
                        eventName,
                        userToTest,
                        file
                );
        eventToTest.setStatus(Status.ACTIVE);

        return eventToTest;
    }
}