# 🎯 ML Features - Complete Input Guide

## How to Use ML Features - Step by Step

---

## **Option 1: Using the AI Dashboard (EASIEST) 🎨**

### Step 1: Login to the System
1. Go to: **http://localhost:7890**
2. Login with:
   - Email: `admin`
   - Password: `adminpass`

### Step 2: Access AI Dashboard
1. Click: **Menu** → **AI Analytics Dashboard**
2. Or directly visit: **http://localhost:7890/student/ai-dashboard**

### Step 3: Click Test Buttons
- ✅ Pre-filled with sample inputs
- ✅ Just click **"Test Feature"** button
- ✅ See results instantly!

---

## **Option 2: Manual API Calls (Advanced) 💻**

### **Feature 1: Performance Prediction**

**What it does:** Predicts your final exam score based on current performance

**📋 Inputs Needed:**
```
correctAnswers    = Number of questions answered correctly (0-100)
totalQuestions    = Total questions in exam (usually 20-100)
timeSpent         = Time spent so far in SECONDS (e.g., 600 = 10 minutes)
avgTimePerQ       = Average time per question in SECONDS (e.g., 45)
difficultyLevel   = 1 (Easy), 2 (Medium), or 3 (Hard)
```

**💡 Example Values:**
```
correctAnswers = 15        (answered 15 correctly)
totalQuestions = 20        (out of 20 questions)
timeSpent = 1200           (20 minutes spent)
avgTimePerQ = 60           (1 minute per question)
difficultyLevel = 2        (Medium difficulty)
```

**🌐 Test with Browser or Terminal:**
```
http://localhost:8080/api/ai-analytics/predict-performance?correctAnswers=15&totalQuestions=20&timeSpent=1200&avgTimePerQ=60&difficultyLevel=2
```

**Expected Output:**
```json
{
  "predicted_final_score": 78.5,
  "pass_probability": 0.92,
  "recommendation": "You are doing well! Keep up the pace."
}
```

---

### **Feature 2: Question Recommendation**

**What it does:** Identifies weak topics and recommends study paths

**📋 Inputs Needed:**
```
studentId           = Your student ID (number)
questionsByTopic    = Performance by topic:
  - Topic name
    - correct: Questions answered correctly
    - total: Total questions in that topic
```

**💡 Example Values:**
```
studentId = 1

Topics:
- Mathematics: 8 out of 10 correct (80%)
- Science: 4 out of 10 correct (40%) ❌ WEAK
- English: 9 out of 10 correct (90%)
```

**🌐 Using Curl (Terminal):**
```bash
curl -X POST http://localhost:8080/api/ai-analytics/recommend-questions \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "questionsByTopic": {
      "Mathematics": {"correct": 8, "total": 10},
      "Science": {"correct": 4, "total": 10},
      "English": {"correct": 9, "total": 10}
    }
  }'
```

**Expected Output:**
```json
{
  "weak_topics": ["Science"],
  "recommended_questions": [
    {"topic": "Science", "question_id": 5, "difficulty": "medium"},
    {"topic": "Science", "question_id": 12, "difficulty": "hard"}
  ],
  "study_hours_recommended": 3
}
```

---

### **Feature 3: Cheat Detection**

**What it does:** Detects suspicious exam behavior

**📋 Inputs Needed:**
```
studentId        = Your student ID
totalQuestions   = Total questions in exam
examDuration     = Total exam time in SECONDS
copyPasteCount   = Number of copy-paste actions detected
questionAnswers  = List of answers with timing and correctness
```

**💡 Example Values:**
```
studentId = 1
totalQuestions = 10
examDuration = 600          (10 minutes exam)
copyPasteCount = 0          (no suspicious copy-paste)

Question answers (for each question):
  - q_id: Question number (1, 2, 3, etc.)
  - time_spent: Seconds spent on this question
  - answer: Your answer (1, 2, 3, or 4)
  - is_correct: true/false
```

**🌐 Using Curl:**
```bash
curl -X POST http://localhost:8080/api/ai-analytics/detect-cheating \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "totalQuestions": 10,
    "examDuration": 600,
    "copyPasteCount": 0,
    "questionAnswers": [
      {"q_id": 1, "time_spent": 45, "answer": 1, "is_correct": true},
      {"q_id": 2, "time_spent": 50, "answer": 2, "is_correct": true},
      {"q_id": 3, "time_spent": 55, "answer": 3, "is_correct": false}
    ]
  }'
```

**Expected Output:**
```json
{
  "cheat_score": 0.15,
  "is_suspicious": false,
  "flags": [],
  "confidence": 0.95
}
```

---

### **Feature 4: Study Plan Generator**

**What it does:** Creates personalized weekly study schedule

**📋 Inputs Needed:**
```
weakTopics      = Topics where you scored low (comma-separated)
strongTopics    = Topics where you scored high (comma-separated)
currentScore    = Your current exam score (0-100)
targetScore     = Your target score (0-100)
```

**💡 Example Values:**
```
weakTopics = "Science,Mathematics"     (need improvement)
strongTopics = "English,History"       (doing well)
currentScore = 65                       (your current score)
targetScore = 85                        (your goal)
```

**🌐 Test with Browser:**
```
http://localhost:8080/api/ai-analytics/generate-study-plan?weakTopics=Science,Mathematics&strongTopics=English,History&currentScore=65&targetScore=85
```

**Expected Output:**
```json
{
  "weekly_plan": {
    "Monday": {
      "topic": "Science",
      "duration_hours": 2,
      "focus": "Basic concepts"
    },
    "Tuesday": {
      "topic": "Mathematics",
      "duration_hours": 2,
      "focus": "Problem solving"
    }
  },
  "estimated_improvement": 12,
  "target_achievable": true
}
```

---

## **Quick Reference Table**

| Feature | Method | Endpoint | Inputs |
|---------|--------|----------|--------|
| **Performance Prediction** | GET | `/api/ai-analytics/predict-performance` | correctAnswers, totalQuestions, timeSpent, avgTimePerQ, difficultyLevel |
| **Question Recommendation** | POST | `/api/ai-analytics/recommend-questions` | studentId, questionsByTopic (JSON) |
| **Cheat Detection** | POST | `/api/ai-analytics/detect-cheating` | studentId, totalQuestions, examDuration, copyPasteCount, questionAnswers |
| **Study Plan Generator** | GET | `/api/ai-analytics/generate-study-plan` | weakTopics, strongTopics, currentScore, targetScore |

---

## **Real-World Example - Complete Flow** 🚀

### Scenario: Student takes exam
```
1. During Exam:
   ✓ 15 out of 20 questions answered correctly
   ✓ Spent 20 minutes (1200 seconds)
   ✓ Average 60 seconds per question
   ✓ Medium difficulty exam

2. CALL: Performance Prediction API
   Input: {correctAnswers: 15, totalQuestions: 20, timeSpent: 1200, avgTimePerQ: 60, difficultyLevel: 2}
   Output: "Your predicted final score is 78.5% - You'll PASS!"

3. CALL: Recommend Questions API
   Input: Topics where they scored low
   Output: "Focus on Science (only 40% correct) and Math (65% correct)"

4. CALL: Study Plan Generator API
   Input: currentScore=65, targetScore=85, weakTopics=Science,Math
   Output: "Study 2 hours daily for 2 weeks to achieve your goal"
```

---

## **🎯 EASIEST WAY - Use the Dashboard!**

Simply visit: **http://localhost:7890/student/ai-dashboard**

All inputs are **pre-filled** with example values. Just click "Test Feature" buttons! ✅

