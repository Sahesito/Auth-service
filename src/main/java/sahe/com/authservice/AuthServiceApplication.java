package sahe.com.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println("""
                AUTH SERVICE - RUNNING
                Port: 8081
                Login: POST /auth/login
                Register: POST /auth/register
                Validate: GET /auth/validate
                Current User: GET /auth/me
                """);
    }

}
