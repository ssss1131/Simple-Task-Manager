package kz.ssss.taskmanager.service;

import kz.ssss.taskmanager.exception.UniqueUsernameException;
import kz.ssss.taskmanager.model.User;
import kz.ssss.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UniqueUsernameException("User with such username already exists!!");
        }

        return userRepository.save(user);
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }

    public UserDetailsService userDetailsService() {
        return this::loadUserByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return loadUserByUsername(username);
    }

}
