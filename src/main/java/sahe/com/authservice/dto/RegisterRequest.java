package sahe.com.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import sahe.com.authservice.model.User;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name Required ")
    private String firstName;

    @NotBlank(message = "LastName Required")
    private String lastName;

    @NotBlank(message = "Email required")
    @Email(message = "The email address must be valid.")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    private String password;
    private User.Role role = User.Role.CLIENT;
    private String phone;
    private String address;
    private String city;
    private String country;
}
