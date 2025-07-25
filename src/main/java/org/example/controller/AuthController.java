package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.model.entity.User;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;
    Logger logger = Logger.getLogger(String.valueOf(AuthController.class));

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            AuthResponse authResponse = userService.signUp(signUpRequest);
            return ResponseEntity.ok(ApiResponse.success("User Registered Successfully", authResponse));
        } catch (RuntimeException e) {
            logger.log(Level.INFO, "Error while SignUp" + e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = userService.Login(loginRequest);
            return ResponseEntity.ok(ApiResponse.success("User Logged In", authResponse));
        } catch (Exception e) {
            logger.log(Level.FINE, "Error while Login" + e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<User>> getProfile(UserProfileRequest userProfileRequest) {
        try {
            User user = userService.getUserByUserName(userProfileRequest);
            return ResponseEntity.ok(ApiResponse.success("Profile Fetched", user));
        } catch (Exception e) {
            logger.log(Level.INFO, "Error while getting profile" + e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<ApiResponse<Boolean>> checkUsernameAvailability(@PathVariable String username) {
        try {
            Boolean isPresent = userService.existByUserName(username);
            return ResponseEntity.ok(ApiResponse.success("UserName availability response", !isPresent));
        } catch (Exception exception) {
            logger.log(Level.INFO, "Error while getting user by username" + exception.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(exception.getMessage()));
        }
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkExistsByEmail(@PathVariable String email) {
        try {
            Boolean isAvailable = userService.existByEmail(email);
            return ResponseEntity.ok(ApiResponse.success("email availability response", isAvailable));
        } catch (Exception exception) {
            logger.log(Level.INFO, "Error while getting user by username" + exception.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(exception.getMessage()));
        }
    }
    @PostMapping("/google-auth")
    public ResponseEntity<ApiResponse<AuthResponse>> googleAuth(@Valid @RequestBody GoogleAuthRequest googleAuthRequest){
        try {
            AuthResponse authResponse=userService.googleAuth(googleAuthRequest);
            return ResponseEntity.ok(ApiResponse.success("Google Auth Success", authResponse));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
