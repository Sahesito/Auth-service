package sahe.com.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sahe.com.authservice.dto.AuthResponse;
import sahe.com.authservice.dto.LoginRequest;
import sahe.com.authservice.dto.RegisterRequest;
import sahe.com.authservice.model.User;
import sahe.com.authservice.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    // POST http://localhost:8081/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Intento de Inicio de sesión de: {}", loginRequest.getEmail());
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    // POST http://localhost:8081/auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Register attempt for: {}", registerRequest.getEmail());
        AuthResponse response = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET http://localhost:8081/auth/validate
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }

    // GET http://localhost:8081/auth/me
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        User user = authService.getCurrentUser(token);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/email/{email}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable String email) {
        authService.setUserActive(email, false);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/email/{email}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable String email) {
        authService.setUserActive(email, true);
        return ResponseEntity.ok().build();
    }
}
