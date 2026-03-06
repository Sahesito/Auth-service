package sahe.com.authservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sahe.com.authservice.client.UserClient;
import sahe.com.authservice.dto.AuthResponse;
import sahe.com.authservice.dto.LoginRequest;
import sahe.com.authservice.dto.RegisterRequest;
import sahe.com.authservice.dto.UserRequest;
import sahe.com.authservice.model.User;
import sahe.com.authservice.repository.UserRepository;
import sahe.com.authservice.security.JwtUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserClient userClient;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.getActive()) {
            throw new RuntimeException("User disabled");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());

        log.info("User logged in correctly: {}", user.getEmail());
        return new AuthResponse(token, user);
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            User existingUser = userRepository.findByEmail(request.getEmail()).get();

            if (Boolean.FALSE.equals(existingUser.getActive())) {
                throw new RuntimeException("This account is deactivated. Contact the administrator.");
            }

            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setActive(true);
        User savedUser = userRepository.save(user);
        log.info("Registered User Auth Service: {}", savedUser.getEmail());

        try {
            UserRequest userRequest = new UserRequest();
            userRequest.setFirstName(request.getFirstName());
            userRequest.setLastName(request.getLastName());
            userRequest.setEmail(request.getEmail());
            userRequest.setRole(request.getRole().name());
            userRequest.setActive(true);
            userRequest.setPhone(request.getPhone());
            userRequest.setAddress(request.getAddress());
            userRequest.setCity(request.getCity());
            userRequest.setCountry(request.getCountry());

            userClient.createUserFromAuth(userRequest);
            log.info("User created in User Service: {}", savedUser.getEmail());
        } catch (Exception e) {
            log.error("Error creating user in user service: {}", e.getMessage());
        }

        String token = jwtUtils.generateToken(savedUser.getEmail(), savedUser.getRole().name());
        return new AuthResponse(token, savedUser);
    }

    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    public User getCurrentUser(String token) {
        String email = jwtUtils.getEmailFromToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void setUserActive(String email, boolean active) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        user.setActive(active);
        userRepository.save(user);
        log.info("User {} {} in auth-service", email, active ? "Activate" : "Deactivate");
    }
}
