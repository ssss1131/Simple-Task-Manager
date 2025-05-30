package kz.ssss.taskmanager.controller;

import jakarta.validation.Valid;
import kz.ssss.taskmanager.dto.request.LoginRequest;
import kz.ssss.taskmanager.dto.request.RegisterRequest;
import kz.ssss.taskmanager.dto.response.JwtAuthenticationResponse;
import kz.ssss.taskmanager.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static kz.ssss.taskmanager.util.Constant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_AUTH_URL)
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping(LOGIN_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse login(@RequestBody @Valid LoginRequest request){
        return authService.login(request);
    }

    @PostMapping(REGISTER_ENDPOINT)
    @ResponseStatus(HttpStatus.CREATED)
    public JwtAuthenticationResponse register(@RequestBody @Valid RegisterRequest request){
        return authService.register(request);
    }

}
