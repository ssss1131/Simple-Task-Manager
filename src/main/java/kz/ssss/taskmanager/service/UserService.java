package kz.ssss.taskmanager.service;

import kz.ssss.taskmanager.exception.UniqueUsernameException;
import kz.ssss.taskmanager.model.User;
import kz.ssss.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    public User create(User user) {
        log.info("Creating user with username {}", user.getUsername());
        if (userRepository.existsByUsername(user.getUsername())) {
            log.warn("Username {} already exists", user.getUsername());
            throw new UniqueUsernameException("User with such username already exists!!");
        }
        User saved = userRepository.save(user);
        log.debug("User {} created with id {}", saved.getUsername(), saved.getId());
        return saved;
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User {} not found", username);
                    return new UsernameNotFoundException("Username not found " + username);
                });
    }

    public UserDetailsService userDetailsService() {
        return this::loadUserByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return loadUserByUsername(username);
    }

}
