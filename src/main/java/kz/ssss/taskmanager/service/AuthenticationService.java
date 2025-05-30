package kz.ssss.taskmanager.service;

import kz.ssss.taskmanager.dto.request.LoginRequest;
import kz.ssss.taskmanager.dto.request.RegisterRequest;
import kz.ssss.taskmanager.dto.response.JwtAuthenticationResponse;
import kz.ssss.taskmanager.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse register(RegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        log.info("User {} registered successfully, JWT issued", request.getUsername());
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse login(LoginRequest request) {
        log.info("Authenticating user: {}", request.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        log.info("User {} authenticated successfully, JWT issued", request.getUsername());
        return new JwtAuthenticationResponse(jwt);
    }

}
