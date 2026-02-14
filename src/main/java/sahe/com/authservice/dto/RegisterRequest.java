package sahe.com.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import sahe.com.authservice.model.User;

@Data
public class RegisterRequest {

    @NotBlank(message = "Nombres requeridos")
    private String firstName;

    @NotBlank(message = "Apellidos requeridos")
    private String lastName;

    @NotBlank(message = "Correo requerido")
    @Email(message = "El correo debe ser valido")
    private String email;

    @NotBlank(message = "Contraseña requerida")
    @Size(min = 6, message = "La contraseña deben ser almenos 6 caracteres")
    private String password;
    private User.Role role = User.Role.CLIENT;
}
