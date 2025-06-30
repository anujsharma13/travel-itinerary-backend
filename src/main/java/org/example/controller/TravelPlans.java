package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ApiResponse;
import org.example.dto.AuthResponse;
import org.example.dto.SignUpRequest;
import org.example.service.TravelPlanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;

@Slf4j
@RestController
@RequestMapping("/api/travel/suggestions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TravelPlans {
    @Autowired
    TravelPlanner travelPlanner;
    @PostMapping("/get/plans")
    public ResponseEntity<ApiResponse<AuthResponse>> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            return null;
        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/get/randomPlans")
    public ResponseEntity<ApiResponse<AuthResponse>> randomSuggestion(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            return null;
        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

}
