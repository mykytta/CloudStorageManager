package com.mykyta.springrestapi.rest;

import com.mykyta.springrestapi.dto.AdminUserDto;
import com.mykyta.springrestapi.dto.UserRegistrationDto;
import com.mykyta.springrestapi.model.*;
import com.mykyta.springrestapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserRestControllerV1Test {

    @MockBean
    private UserService userService;

    @Autowired
    private UserRestControllerV1 userRestControllerV1;
    @Test
    void getUserById() {
        Optional<User> userToTest = Optional.of(generateRandomUser());
        when(userService.findById(2L)).thenReturn(userToTest);
        AdminUserDto result = AdminUserDto.fromUser(userToTest.get());
        ResponseEntity<?> test = userRestControllerV1.getUserById(2L);
        assertNotNull(test);
        assertEquals(result, test.getBody());
        assertEquals("We cannot find this user, please try to enter the correct id",userRestControllerV1.getUserById(1L).getBody());
    }

    @Test
    void getAllUsers() {
        when(userService.getAll()).thenReturn(List.of(generateRandomUser(),
                generateRandomUser()));
        assertEquals(2, userRestControllerV1.getAllUsers().getBody().size());
        assertNotNull(userRestControllerV1.getAllUsers());

    }

    @Test
    void createUser() {
        User testUser = generateRandomUser();
        when(userService.register(testUser)).thenReturn(testUser);
        UserRegistrationDto actual = (UserRegistrationDto) userRestControllerV1.createUser(UserRegistrationDto.fromUser(testUser)).getBody();
        UserRegistrationDto expected = UserRegistrationDto.fromUser(testUser);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateUser() {
        User withoutChangesExpected = generateRandomUser();
        given(userService.findById(1L)).willReturn(Optional.of(withoutChangesExpected));
        given(userService.register(withoutChangesExpected)).willReturn(withoutChangesExpected);
        AdminUserDto withoutChangesActual = (AdminUserDto) userRestControllerV1.getUserById(1L).getBody();

        assertNotNull(withoutChangesActual);
        assertEquals(AdminUserDto.fromUser(withoutChangesExpected), withoutChangesActual);

        String username = "Oleh";
        withoutChangesActual.setUsername(username);
        userService.update(withoutChangesActual.toUser());
        assertEquals(username, withoutChangesActual.getUsername());
    }

    @Test
    void deleteUserById() {
        User expected = generateRandomUser();
        when(userService.findById(1L)).thenReturn(Optional.of(expected));
        userRestControllerV1.deleteUserById(1L);
        assertEquals(Status.DELETED, expected.getStatus());
    }

    private static User generateRandomUser(){
        String username = UUID.randomUUID().toString();
        String fileName = UUID.randomUUID().toString();

        User userToTest = new User(username, "strongPassword",
                List.of(new Event(
                        fileName,
                        new User(),
                        new File()
                )), List.of(new Role()));
        userToTest.setStatus(Status.ACTIVE);

        return userToTest;
    }
}