package spring_security_shield.controller;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.data.domain.Pageable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import spring_security_shield.dto.UserRoleDTO;
import spring_security_shield.services.CaptchaService;
import spring_security_shield.services.UserService;
import spring_security_shield.entity.Users;
import org.springframework.http.*;
import org.springframework.data.domain.Page;
import spring_security_shield.constants.StaticVariables;



import java.time.Duration;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CaptchaService captchaService;
    private final Bucket bucket;

    @Autowired
    public UserController(UserService userService, CaptchaService captchaService) {
        this.userService = userService;
        this.captchaService = captchaService;
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public static class TooManyRequestsException extends RuntimeException {
        public TooManyRequestsException(String message) {
            super(message);
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    public ResponseEntity<String> registerUser(@RequestBody Users users) {
        if (users.getPassword() == null || users.getPassword().isEmpty()) {
            return ResponseEntity.status(409).body("rawPassword cannot be null");
        }
        try {
            userService.createUser(users);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserRoleDTO> getUserById(@PathVariable String id) {
        if (bucket.tryConsume(1)) {
            UserRoleDTO user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } else {
            throw new TooManyRequestsException(StaticVariables.TOOMANYREQUESTEXCEPTION);
        }
    }


    @GetMapping("/all")
    @Operation(summary = "Get all users with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Page<Users> findAllUsers(Pageable pageable) {
        if (bucket.tryConsume(1)) {
            return userService.findAllUsers(pageable);
        } else {
            throw new TooManyRequestsException(StaticVariables.TOOMANYREQUESTEXCEPTION);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        if (bucket.tryConsume(1)) {
            try {
                userService.deleteUserById(id);
                return ResponseEntity.ok("User deleted successfully");
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            throw new TooManyRequestsException(StaticVariables.TOOMANYREQUESTEXCEPTION);
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update user status by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User status updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> updateUserActiveStatus(@PathVariable String id, @RequestParam boolean active) {
        if (bucket.tryConsume(1)) {
            try {
                userService.updateUserActiveStatus(id, active);
                return ResponseEntity.ok("User status updated successfully");
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            throw new TooManyRequestsException(StaticVariables.TOOMANYREQUESTEXCEPTION);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody Users updatedUser) {
        if (bucket.tryConsume(1)) {
            try {
                userService.updateUser(id, updatedUser);
                return ResponseEntity.ok("User updated successfully");
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        } else {
            throw new TooManyRequestsException(StaticVariables.TOOMANYREQUESTEXCEPTION);
        }
    }




}
