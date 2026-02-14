package sahe.com.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sahe.com.authservice.dto.RegisterRequest;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping("/users/from-auth")
    ResponseEntity<Void> createUserFromAuth(@RequestBody RegisterRequest request);
}
