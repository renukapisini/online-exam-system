package com.example.exam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/public/api")
@CrossOrigin(origins = "*")
public class PublicMLController {

    /**
     * Public health check 
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "✅ Public API Online");
        response.put("features", "ML Features Ready");
        response.put("timestamp", new Date().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Public prediction endpoint
     */
    @GetMapping("/predict")
    public ResponseEntity<Map<String, Object>> predict(
            @RequestParam(required = false, defaultValue = "15") int correctAnswers,
            @RequestParam(required = false, defaultValue = "20") int totalQuestions,
            @RequestParam(required = false, defaultValue = "1200") int timeSpent,
            @RequestParam(required = false, defaultValue = "60") double avgTimePerQ,
            @RequestParam(required = false, defaultValue = "2") int difficultyLevel) {
        
        Map<String, Object> response = new HashMap<>();
        double correctPercentage = (correctAnswers * 100.0) / totalQuestions;
        double timePercentage = (timeSpent / (totalQuestions * avgTimePerQ)) * 100;
        
        // Simple prediction algorithm
        double predictedScore = correctPercentage * 0.7 + (100 - timePercentage * 0.5) * 0.3;
        predictedScore = Math.min(100, Math.max(0, predictedScore));
        
        response.put("predicted_score", Math.round(predictedScore * 100.0) / 100.0);
        response.put("pass_probability", predictedScore > 40 ? 0.95 : 0.45);
        response.put("recommendation", predictedScore > 70 ? "You're doing great! Keep it up!" : "Study harder to improve!");
        response.put("current_percentage", Math.round(correctPercentage * 100.0) / 100.0);
        response.put("timestamp", new Date());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Public study plan endpoint
     */
    @GetMapping("/study-plan")
    public ResponseEntity<Map<String, Object>> getStudyPlan(
            @RequestParam(required = false, defaultValue = "") String weakTopics,
            @RequestParam(required = false, defaultValue = "0") int currentScore,
            @RequestParam(required = false, defaultValue = "100") int targetScore) {
        
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> plan = new HashMap<>();
        
        plan.put("Monday", "Math - 2 hours");
        plan.put("Tuesday", "English - 2 hours");
        plan.put("Wednesday", "Science - 2 hours");
        plan.put("Thursday", "Review - 3 hours");
        plan.put("Friday", "Practice - 2 hours");
        
        response.put("weekly_study_plan", plan);
        response.put("current_score", currentScore);
        response.put("target_score", targetScore);
        response.put("estimated_improvement", Math.min(35, targetScore - currentScore));
        response.put("achievable", true);
        response.put("timestamp", new Date());
        
        return ResponseEntity.ok(response);
    }
}
