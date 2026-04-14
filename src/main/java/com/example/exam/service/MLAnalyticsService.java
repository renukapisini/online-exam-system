package com.example.exam.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@Service
public class MLAnalyticsService {

    @Value("${ml.backend.url:http://localhost:5000}")
    private String mlBackendUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Predict student performance - pass/fail probability and final score
     */
    public Map<String, Object> predictPerformance(
            int correctAnswers,
            int totalQuestions,
            int timeSpent,
            double avgTimePerQ,
            List<Integer> previousScores,
            int difficultyLevel) {
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("correct_answers", correctAnswers);
        payload.put("total_questions", totalQuestions);
        payload.put("time_spent", timeSpent);
        payload.put("avg_time_per_q", avgTimePerQ);
        payload.put("previous_scores", previousScores);
        payload.put("difficulty_level", difficultyLevel);

        try {
            String url = mlBackendUrl + "/api/ml/predict-performance";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, payload, Map.class);
            return response.getBody();
        } catch (Exception e) {
            return getDefaultPrediction(correctAnswers, totalQuestions);
        }
    }

    /**
     * Recommend questions based on weak areas
     */
    public Map<String, Object> recommendQuestions(
            long studentId,
            Map<String, Map<String, Integer>> questionsByTopic) {
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("student_id", studentId);
        
        Map<String, Object> examResults = new HashMap<>();
        examResults.put("total_questions", questionsByTopic.values().stream()
                .mapToInt(m -> m.getOrDefault("total", 0))
                .sum());
        examResults.put("questions_by_topic", questionsByTopic);
        payload.put("exam_results", examResults);

        try {
            String url = mlBackendUrl + "/api/ml/recommend-questions";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, payload, Map.class);
            return response.getBody();
        } catch (Exception e) {
            return getDefaultRecommendations(questionsByTopic);
        }
    }

    /**
     * Detect cheating/suspicious activity
     */
    public Map<String, Object> detectCheating(
            long studentId,
            List<Map<String, Object>> questionAnswers,
            int totalQuestions,
            int examDuration,
            int copyPasteCount) {
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("student_id", studentId);
        payload.put("question_answers", questionAnswers);
        payload.put("total_questions", totalQuestions);
        payload.put("exam_duration", examDuration);
        payload.put("copy_paste_count", copyPasteCount);

        try {
            String url = mlBackendUrl + "/api/ml/detect-cheating";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, payload, Map.class);
            return response.getBody();
        } catch (Exception e) {
            return getDefaultCheatDetection();
        }
    }

    /**
     * Generate personalized study plan
     */
    public Map<String, Object> generateStudyPlan(
            List<String> weakTopics,
            List<String> strongTopics,
            float currentScore,
            float targetScore) {
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("weak_topics", weakTopics);
        payload.put("strong_topics", strongTopics);
        payload.put("current_score", currentScore);
        payload.put("target_score", targetScore);

        try {
            String url = mlBackendUrl + "/api/ml/generate-study-plan";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, payload, Map.class);
            return response.getBody();
        } catch (Exception e) {
            return getDefaultStudyPlan(weakTopics, targetScore);
        }
    }

    /**
     * Perform batch analysis - all ML features at once
     */
    public Map<String, Object> performBatchAnalysis(Map<String, Object> analysisData) {
        try {
            String url = mlBackendUrl + "/api/ml/batch-analysis";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, analysisData, Map.class);
            return response.getBody();
        } catch (Exception e) {
            return Map.of("error", "ML Backend unavailable", "status", "fallback_mode");
        }
    }

    // Default fallback methods when ML backend is unavailable

    private Map<String, Object> getDefaultPrediction(int correct, int total) {
        float percentage = (float) correct / total * 100;
        float passProb = Math.min(percentage / 100, 0.95f);
        
        return Map.of(
            "predicted_final_score", percentage,
            "pass_probability", passProb,
            "confidence", 0.5,
            "recommendation", "Keep practicing this topic"
        );
    }

    private Map<String, Object> getDefaultRecommendations(Map<String, Map<String, Integer>> byTopic) {
        List<String> weakTopics = new ArrayList<>();
        for (String topic : byTopic.keySet()) {
            Map<String, Integer> scores = byTopic.get(topic);
            float accuracy = (float) scores.get("correct") / scores.get("total");
            if (accuracy < 0.6) {
                weakTopics.add(topic);
            }
        }
        
        return Map.of(
            "weak_topics", weakTopics,
            "top_3_recommendations", weakTopics.subList(0, Math.min(3, weakTopics.size())),
            "study_plan", new ArrayList<>()
        );
    }

    private Map<String, Object> getDefaultCheatDetection() {
        return Map.of(
            "risk_score", 0,
            "risk_level", "LOW",
            "flags", new ArrayList<>(),
            "action", "No action needed"
        );
    }

    private Map<String, Object> getDefaultStudyPlan(List<String> weakTopics, float targetScore) {
        return Map.of(
            "weekly_plan", new ArrayList<>(),
            "estimated_improvement", 10,
            "expected_score", Math.min(100, targetScore + 5),
            "consistency_required", "Daily 60 minutes"
        );
    }
}
