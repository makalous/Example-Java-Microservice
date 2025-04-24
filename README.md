# Example Java Microservice

A minimal Spring Boot 3.4.4 based microservice demonstrating integration with JWT authentication, Swagger/OpenAPI documentation, SSL and global configuration properties with use of the own SDK from URL:
```html
https://github.com/makalous/Example-Java-SDK
```

## Technologies:
-	Spring Boot 3.4.4
-	Lombok
-	Gradle

## Running the application:
1.	Clone the repository and navigate to the project folder
2.	Start the app: 
```gradle
./gradlew bootRun
```
3.	Default port: 9043 (HTTPS with SSL enabled)

## JWT Authentication:
-	Hidden login endpoint: POST /auth/login
```html
https://localhost:9043/auth/login?username=example1&password=example2
```
-	You will receive a JWT token
-	Pass the token in the Authorization header when calling private endpoints

## API Documentation:
-	Swagger UI available at:
```html
https://localhost:9043/swagger-ui/index.html
```
- Auth endpoints are hidden from the Swagger UI

License:
MIT License