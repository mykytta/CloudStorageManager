package com.mykyta.springrestapi.rest;

import com.mykyta.springrestapi.dto.AdminUserDto;
import com.mykyta.springrestapi.dto.UserRegistrationDto;
import com.mykyta.springrestapi.dto.UserUpdateDto;
import com.mykyta.springrestapi.model.Status;
import com.mykyta.springrestapi.model.User;
import com.mykyta.springrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserRestControllerV1 {

    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRestControllerV1(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") Long id){
        if(userService.findById(id).isEmpty()){
            return new ResponseEntity<>("We cannot find this user, please try to enter the correct id", HttpStatus.NO_CONTENT);
        }
        User userToUpdate = userService.findById(id).get();
        AdminUserDto result = AdminUserDto.fromUser(userToUpdate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<List<AdminUserDto>> getAllUsers(){
        List<AdminUserDto> users = userService.getAll().stream()
                .map(AdminUserDto::fromUser).collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity createUser(@RequestBody UserRegistrationDto userRegistrationDto){
        User userReg = userRegistrationDto.toUser();
        userService.register(userReg);
        return new ResponseEntity<>(UserRegistrationDto.fromUser(userReg), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity updateUser(@RequestBody UserUpdateDto user, @PathVariable(name = "id") Long id){
        Optional<User> userUpdate = userService.findById(id);
        if(userUpdate.isPresent()){
            User userToUpdate = userUpdate.get();
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            userToUpdate.setUsername(user.getUsername());
            userService.update(userToUpdate);
            return new ResponseEntity<>(UserUpdateDto.fromUser(userUpdate.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>("We cannot find this user, please try to enter the correct id", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteUserById(@PathVariable(name = "id") Long id){
        Optional<User> user = userService.findById(id);
        if(user.isPresent()){
            User userToDelete = user.get();
            userToDelete.setStatus(Status.DELETED);
            userService.update(userToDelete);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("We cannot find this user, please try to enter the correct id", HttpStatus.NOT_FOUND);
    }

}
