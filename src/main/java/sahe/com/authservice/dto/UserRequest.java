package sahe.com.authservice.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Boolean active;
    private String phone;
    private String address;
    private String city;
    private String country;
}