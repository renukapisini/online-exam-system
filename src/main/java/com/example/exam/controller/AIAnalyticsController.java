package com.example.exam.controller;

import com.example.exam.service.MLAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/ai-analytics")
@CrossOrigin(origins = "*")
public class AIAnalyticsController {

    @Autowired
    private MLAnalyticsService mlAnalyticsService;

    /**
     * Predict student performance during/after exam
     */
    @PostMapping("/predict-performance")
    @GetMapping("/predict-performance")
    public ResponseEntity<Map<String, Object>> predictPerformance(
            @RequestParam int correctAnswers,
            @RequestParam int totalQuestions,
            @RequestParam int timeSpent,
            @RequestParam double avgTimePerQ,
            @RequestParam(required = false, defaultValue = "") String previousScoresStr,
            @RequestParam(required = false, defaultValue = "2") int difficultyLevel) {
        
        List<Integer> previousScores = new ArrayList<>();
        if (!previousScoresStr.isEmpty()) {
            for (String score : previousScoresStr.split(",")) {
                previousScores.add(Integer.parseInt(score.trim()));
            }
        }

        Map<String, Object> prediction = mlAnalyticsService.predictPerformance(
            correctAnswers, totalQuestions, timeSpent, avgTimePerQ, previousScores, difficultyLevel
        );
        
        return ResponseEntity.ok(prediction);
    }

    /**
     * Recommend questions/topics for study
     */
    @PostMapping("/recommend-questions")
    public ResponseEntity<Map<String, Object>> recommendQuestions(
            @RequestParam long studentId,
            @RequestBody Map<String, Map<String, Integer>> questionsByTopic) {
        
        Map<String, Object> recommendations = mlAnalyticsService.recommendQuestions(studentId, questionsByTopic);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Detect suspicious activity/cheating
     */
    @PostMapping("/detect-cheating")
    public ResponseEntity<Map<String, Object>> detectCheating(
            @RequestParam long studentId,
            @RequestParam int totalQuestions,
            @RequestParam int examDuration,
            @RequestParam(required = false, defaultValue = "0") int copyPasteCount,
            @RequestBody List<Map<String, Object>> questionAnswers) {
        
        Map<String, Object> detection = mlAnalyticsService.detectCheating(
            studentId, questionAnswers, totalQuestions, examDuration, copyPasteCount
        );
        
        return ResponseEntity.ok(detection);
    }

    /**
     * Generate personalized study plan
     */
    @PostMapping("/generate-study-plan")
    @GetMapping("/generate-study-plan")
    public ResponseEntity<Map<String, Object>> generateStudyPlan(
            @RequestParam(required = false, defaultValue = "") String weakTopicsStr,
            @RequestParam(required = false, defaultValue = "") String strongTopicsStr,
            @RequestParam float currentScore,
            @RequestParam float targetScore) {
        
        List<String> weakTopics = Arrays.asList(weakTopicsStr.split(","));
        List<String> strongTopics = Arrays.asList(strongTopicsStr.split(","));
        
        Map<String, Object> plan = mlAnalyticsService.generateStudyPlan(
            weakTopics, strongTopics, currentScore, targetScore
        );
        
        return ResponseEntity.ok(plan);
    }

    /**
     * Get AI insights dashboard data
     */
    @GetMapping("/dashboard/{studentId}")
    public ResponseEntity<Map<String, Object>> getAIDashboard(@PathVariable long studentId) {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("student_id", studentId);
        dashboard.put("ai_features", Arrays.asList(
            "Performance Prediction",
            "Question Recommendation",
            "Cheat Detection",
            "Study Plan Generator"
        ));
        dashboard.put("status", "AI Analytics Engine Active");
        dashboard.put("timestamp", new Date());
        
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Health check for AI backend
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "AI Analytics System Online");
        response.put("features", "Performance Prediction, Question Recommendation, Cheat Detection, Study Planning");
        response.put("timestamp", new Date().toString());
        
        return ResponseEntity.ok(response);
    }
}
