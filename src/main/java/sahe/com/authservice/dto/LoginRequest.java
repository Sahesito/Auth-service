package sahe.com.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "The email address must be valid.")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
