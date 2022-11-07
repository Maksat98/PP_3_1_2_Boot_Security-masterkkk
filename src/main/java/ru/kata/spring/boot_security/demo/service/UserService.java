package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService  {
    List<User> allUsers();
    User findById(Long id);
    User addUser(User addUser);
    void delete(Long id);
    void edit(Long id, User editUser);
    User getUsername(String name);
}
