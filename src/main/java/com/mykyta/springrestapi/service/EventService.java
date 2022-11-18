package com.mykyta.springrestapi.service;

import com.mykyta.springrestapi.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Event create(Event event);
    Event update(Event event);
    List<Event> getAll();
    Optional<Event> getById(Long id);
    void delete(Long id);
}
