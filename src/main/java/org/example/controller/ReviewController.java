package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.ApiResponse;
import org.example.dto.ReviewRequest;
import org.example.dto.ReviewResponse;
import org.example.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllReviews(){
        try {
            List<ReviewResponse> reviewResponses=reviewService.getAllReviews();
            return ResponseEntity.ok(ApiResponse.success("Review retrieved succ", reviewResponses));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> submitResponse(HttpServletRequest request, @Valid @RequestBody ReviewRequest reviewRequest){
        try {
            String tokenUserName=(String) request.getAttribute("authenticatedUsername");
            if(tokenUserName==null)
            {
                return ResponseEntity.status(401).body(ApiResponse.error("Authentication failed"));
            }
            ReviewResponse reviewResponse=reviewService.submitResponse(reviewRequest, tokenUserName);
            return ResponseEntity.ok(ApiResponse.success("Submitted success", reviewResponse));
        }catch (Exception e){
            return ResponseEntity.status(400).body(ApiResponse.error(e.getMessage()));
        }
    }
}
