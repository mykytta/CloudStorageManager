package com.mykyta.springrestapi.repository;

import com.mykyta.springrestapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {
}
