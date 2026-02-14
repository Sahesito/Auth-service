package sahe.com.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "El correo es requerido")
    @Email(message = "El correo debe ser valido")
    private String email;

    @NotBlank(message = "Contraseña es requerida")
    private String password;
}
