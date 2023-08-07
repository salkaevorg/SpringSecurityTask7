package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Autowired
    public MyUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return user;
    }
}
