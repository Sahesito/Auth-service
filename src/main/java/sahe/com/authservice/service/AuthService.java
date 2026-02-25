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

    /* Login */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales invalidas");
        }

        if (!user.getActive()) {
            throw new RuntimeException("Usuario deshabilitado");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());

        log.info("Usuario logeado correctamente: {}", user.getEmail());
        return new AuthResponse(token, user);
    }

    /* Registro */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            User existingUser = userRepository.findByEmail(request.getEmail()).get();

            if (Boolean.FALSE.equals(existingUser.getActive())) {
                throw new RuntimeException("Esta cuenta está desactivada. Contacta al administrador.");
            }

            throw new RuntimeException("Correo ya registrado");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setActive(true);

        User savedUser = userRepository.save(user);
        log.info("Usuario registrado Auth Service: {}", savedUser.getEmail());

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
            log.info("Usuario creado en User Service: {}", savedUser.getEmail());
        } catch (Exception e) {
            log.error("Error al crear el usuario en user service: {}", e.getMessage());
        }

        String token = jwtUtils.generateToken(savedUser.getEmail(), savedUser.getRole().name());
        return new AuthResponse(token, savedUser);
    }

    /* Validar Token */
    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    /* Obtener Usuario Actual*/
    public User getCurrentUser(String token) {
        String email = jwtUtils.getEmailFromToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void setUserActive(String email, boolean active) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
        user.setActive(active);
        userRepository.save(user);
        log.info("Usuario {} {} en auth-service", email, active ? "activado" : "desactivado");
    }
}
