package com.mykyta.springrestapi.service;

import com.mykyta.springrestapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(User user);

    User update(User user);

    List<User> getAll();
    User findByUsername(String username);
    Optional<User> findById(Long id);
    void delete(Long id);
}
