package kz.ssss.taskmanager.controller;

import jakarta.validation.Valid;
import kz.ssss.taskmanager.dto.request.LoginRequest;
import kz.ssss.taskmanager.dto.request.RegisterRequest;
import kz.ssss.taskmanager.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kz.ssss.taskmanager.util.Constant.BASE_AUTH_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_AUTH_URL)
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

}
