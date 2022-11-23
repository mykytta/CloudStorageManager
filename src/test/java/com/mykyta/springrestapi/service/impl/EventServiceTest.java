package com.mykyta.springrestapi.service.impl;

import com.mykyta.springrestapi.model.Event;
import com.mykyta.springrestapi.model.File;
import com.mykyta.springrestapi.model.User;
import com.mykyta.springrestapi.repository.EventRepository;
import com.mykyta.springrestapi.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class EventServiceTest {

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Test
    void create() {
        Event expected = new Event("downloading", new User(), new File());
        given(eventRepository.save(expected)).willReturn(expected);
        Event actual = eventService.create(expected);

        assertNotNull(expected);
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        Event withoutChangesExpected = new Event("downloading", new User(), new File());
        given(eventRepository.findById(1L)).willReturn(Optional.of(withoutChangesExpected));
        given(eventRepository.save(withoutChangesExpected)).willReturn(withoutChangesExpected);
        Optional<Event> withoutChangesActual = eventService.getById(1L);

        assertNotNull(withoutChangesActual);
        assertEquals(withoutChangesExpected, withoutChangesActual.get());

        String eventName = "uploading";
        withoutChangesActual.get().setEventName(eventName);
        eventService.update(withoutChangesActual.get());
        assertEquals(eventName, withoutChangesActual.get().getEventName());
    }

    @Test
    void getAll() {
        when(eventRepository.findAll()).thenReturn(Stream
                .of(new Event("downloading", new User(), new File()),
                        new Event("uploading", new User(), new File())).collect(Collectors.toList()));
        assertEquals(2, eventService.getAll().size());
        assertNotNull(eventService.getAll());
    }

    @Test
    void getById() {
        when(eventRepository.findById(2L)).thenReturn(Optional.of(new Event("downloading", new User(), new File())));
        Optional<Event> test = eventService.getById(2L);
        assertNotNull(test);
        assertEquals(eventRepository.findById(2L), test);
        assertEquals(Optional.empty(),eventService.getById(1L));
    }

    @Test
    void delete() {
        eventService.delete(1L);
        verify(eventRepository).deleteById(1L);
    }
}