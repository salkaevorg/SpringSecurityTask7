package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByUsername(String username);

    void saveUser(User user);
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
}
