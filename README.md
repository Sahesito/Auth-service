## 🔐 Auth Service – SmartCommerce
This module centralizes authentication, credential management, and JWT generation for the entire SmartCommerce ecosystem. It operates independently, registers in Eureka, and communicates with the User Service to maintain domain separation between authentication and user profile data.

## 🎯 Why This Module Exists
- Centralized Authentication Logic
  Handles login, registration, token generation, and token validation in one isolated service.

- Secure Credential Management
  Passwords are encrypted using BCrypt before persistence.

- Stateless Security Architecture
  Uses JWT tokens instead of sessions, enabling horizontal scalability.

- Role-Based Token Generation
  Embeds user role inside the JWT for downstream authorization across microservices.

- Service-to-Service Synchronization
  Uses OpenFeign to automatically create user profiles in the User Service during registration.

- Microservice Isolation
  Maintains its own PostgreSQL database (smartcommerce_auth) separate from user profile data.

- OAuth2 Ready
  Includes Google OAuth2 client configuration for future social login integration.

## 🔑 Core Endpoints
| Endpoint              | Description                         |
| --------------------- | ----------------------------------- |
| `POST /auth/login`    | Authenticate user and return JWT    |
| `POST /auth/register` | Register new user and generate JWT  |
| `GET /auth/validate`  | Validate token integrity            |
| `GET /auth/me`        | Retrieve current authenticated user |

## 🛡️ Security Design
- Stateless session management
- JWT filter executed before request authentication
- Public access:

**/auth/login**

**/auth/register**

**/auth/validate**

**/actuator/****

- Swagger endpoints
-All other routes require authentication

## 🔐 JWT Configuration
- HMAC-based signature
- Configurable expiration (86400000 ms default)
- Email as subject
- Role stored as claim

## 🗄️ Persistence
- Database: PostgreSQL
- JPA with Hibernate (ddl-auto: update)
- Unique email constraint
- Seeded initial users (ADMIN, SELLER, CLIENT)

## ⚙️ Configuration
- Port: 8081
- Registered in Eureka
- OpenFeign enabled for inter-service communication
- Actuator endpoints enabled (health, info)
- Debug logging enabled for security layer
