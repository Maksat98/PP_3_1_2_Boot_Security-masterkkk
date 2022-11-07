package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;


@Service
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder
            ;
    @Autowired
    public UserServiceImp(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> allUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public User addUser(User addUser) {
        addUser.setPassword(passwordEncoder.encode(addUser.getPassword()));
        return userRepository.save(addUser);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getAuthorities()
        );
    }

    @Override
    public void edit(Long id, User editUser) {
        User user = userRepository.findById(id).get();
        editUser.setPassword(
                editUser.getPassword().isEmpty() ?
                        user.getPassword() : passwordEncoder.encode(editUser.getPassword()));
    }

    @Override
    public User getUsername(String username) {
        return userRepository.findByEmail(username);
    }
}