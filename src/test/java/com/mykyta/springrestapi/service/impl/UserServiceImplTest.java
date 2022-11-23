package com.mykyta.springrestapi.service.impl;

import com.mykyta.springrestapi.model.Event;
import com.mykyta.springrestapi.model.Role;
import com.mykyta.springrestapi.model.User;
import com.mykyta.springrestapi.repository.UserRepository;
import com.mykyta.springrestapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    void register() {
        User expected = new User("Mykyta", "strongPassword",
                List.of(new Event()), List.of(new Role()));
        given(userRepository.save(expected)).willReturn(expected);
        User actual = userService.register(expected);

        assertNotNull(expected);
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        User withoutChangesExpected = new User("Mykyta", "strongPassword",
                List.of(new Event()), List.of(new Role()));
        given(userRepository.findById(1L)).willReturn(Optional.of(withoutChangesExpected));
        given(userRepository.save(withoutChangesExpected)).willReturn(withoutChangesExpected);
        Optional<User> withoutChangesActual = userService.findById(1L);

        assertNotNull(withoutChangesActual);
        assertEquals(withoutChangesExpected, withoutChangesActual.get());

        String username = "Oleh";
        withoutChangesActual.get().setUsername(username);
        userService.update(withoutChangesActual.get());
        assertEquals(username, withoutChangesActual.get().getUsername());
    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenReturn(Stream
                .of(new User("Mykyta", "strongPassword",
                                List.of(new Event()), List.of(new Role())),
                        new User("Oleh", "strongPassword/1234",
                                List.of(new Event()), List.of(new Role()))).collect(Collectors.toList()));
        assertEquals(2, userService.getAll().size());
        assertNotNull(userService.getAll());
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("Oleh")).thenReturn(new User("Mykyta", "strongPassword",
                List.of(new Event()), List.of(new Role())));
        User test = userService.findByUsername("Oleh");
        assertNotNull(test);
        assertEquals(userRepository.findByUsername("Oleh"), test);
        assertNull(userService.findByUsername("Vasyl"));
    }

    @Test
    void findById() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(new User("Mykyta", "strongPassword",
                List.of(new Event()), List.of(new Role()))));
        Optional<User> test = userService.findById(2L);
        assertNotNull(test);
        assertEquals(userRepository.findById(2L), test);
        assertEquals(Optional.empty(),userService.findById(1L));
    }

    @Test
    void delete() {
        userService.delete(1L);
        verify(userRepository).deleteById(1L);
    }
}