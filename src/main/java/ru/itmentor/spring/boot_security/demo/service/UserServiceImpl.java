package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setRoles(Collections.singleton(roleRepository.getById(1L)));
        }
        userRepository.save(user);
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setNationality(updatedUser.getNationality());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRoles(updatedUser.getRoles());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
