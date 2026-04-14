# 🧪 ML Features Testing & Demo Guide

## Prerequisites
- ✅ Spring Boot running on http://localhost:7890
- ✅ ML Backend running on http://localhost:5000
- ✅ Admin account: admin / adminpass

---

## Test Scenario 1: Performance Prediction

### Step 1: Create Sample Exam
1. Login as admin (admin/adminpass)
2. Go to **Manage Exams** → **Add New Exam**
3. Create exam:
   - Title: "Math Final"
   - Duration: 30 minutes
   - Save

### Step 2: Add Questions
1. Go to **Manage Questions** → **Add Question**
2. Add 5 questions with difficulty varying from easy to hard
3. Example:
   - Q1 (Easy): 2+2=?
   - Q2 (Medium): Solve: x² - 5x + 6 = 0
   - Q3 (Hard): Calculus integration problem

### Step 3: Test as Student
1. Logout and create student account
2. Take exam and answer questions
3. API automatically called: `/api/ai-analytics/predict-performance`
4. Expected output in browser console:
```json
{
  "predicted_final_score": 75,
  "pass_probability": 0.80,
  "recommendation": "Good progress"
}
```

### Step 4: Manual API Test
```bash
curl -X POST http://localhost:8080/api/ai-analytics/predict-performance \
  -G \
  -d "correctAnswers=3" \
  -d "totalQuestions=5" \
  -d "timeSpent=600" \
  -d "avgTimePerQ=120" \
  -d "difficultyLevel=2"
```

✅ **Expected:** Performance prediction JSON

---

## Test Scenario 2: Question Recommendation

### Step 1: Complete Multiple Exams
Take exams with intentionally varying performance:
- Math: 80% (good)
- Science: 40% (weak)
- English: 95% (excellent)

### Step 2: View Recommendations
After exam submission, system should show:
- **Weak Areas:** Science (recommended for study)
- **Study Plan:** Personalized topics to focus on

### Step 3: Manual API Test
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

✅ **Expected:** Recommendations with weak topics and study plan

---

## Test Scenario 3: Cheat Detection

### Step 1: Create Test Exam
Create a simple exam with 10 questions.

### Step 2: Intentionally Trigger Suspicious Patterns
Take exam with patterns that trigger cheat detection:

**Pattern 1 - Super Fast Answers:**
- Answer all 10 questions in 30 seconds total (3 seconds per Q)
- System detects: Suspiciously fast timing

**Pattern 2 - Copy-Paste Activity:**
- Enable browser DevTools
- Monitor network calls
- System counts copy-paste events

**Pattern 3 - Perfect Accuracy + Fast Time:**
- Answer 10/10 questions correctly in 1 minute
- System detects: Perfect accuracy with unusual timing

### Step 3: Admin Review
1. Login as admin
2. Go to **Manage Students** or **View Results**
3. Check for flagged exams with risk indicators
4. ⚠️ Risk Levels shown:
   - 🟢 LOW (Green) - No action
   - 🟡 MEDIUM (Yellow) - Monitor
   - 🔴 HIGH (Red) - Review immediately

### Step 4: Manual API Test
```bash
curl -X POST http://localhost:8080/api/ai-analytics/detect-cheating \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "totalQuestions": 10,
    "examDuration": 600,
    "copyPasteCount": 5,
    "questionAnswers": [
      {"q_id": 1, "time_spent": 5, "answer": 1, "is_correct": true},
      {"q_id": 2, "time_spent": 5, "answer": 2, "is_correct": true},
      {"q_id": 3, "time_spent": 5, "answer": 3, "is_correct": true},
      {"q_id": 4, "time_spent": 5, "answer": 4, "is_correct": true},
      {"q_id": 5, "time_spent": 5, "answer": 5, "is_correct": true}
    ]
  }'
```

✅ **Expected:** Risk assessment with flags

---

## Test Scenario 4: Study Plan Generation

### Step 1: Get Performance Analysis
Complete a practice exam with mixed results.

### Step 2: Request Study Plan
System generates personalized plan based on:
- Weak topics identified
- Current score
- Target improvement

### Step 3: View Weekly Schedule
Admin or student can access study plan showing:
- Daily topics
- Time allocation
- Practice questions
- Review sessions

### Step 4: Manual API Test
```bash
curl -X POST http://localhost:8080/api/ai-analytics/generate-study-plan \
  -G \
  -d "weakTopics=Science,Mathematics" \
  -d "strongTopics=English" \
  -d "currentScore=65" \
  -d "targetScore=85"
```

✅ **Expected:** Weekly study plan with daily activities

---

## Test Scenario 5: Batch Analysis (All Features)

### API Call
```bash
curl -X POST http://localhost:8080/api/ai-analytics/batch-analysis \
  -H "Content-Type: application/json" \
  -d '{
    "student_id": 1,
    "performance_data": {
      "correct_answers": 15,
      "total_questions": 20,
      "time_spent": 1200,
      "avg_time_per_q": 60,
      "previous_scores": [70, 75, 80],
      "difficulty_level": 2
    },
    "exam_results": {
      "total_questions": 20,
      "questions_by_topic": {
        "Math": {"correct": 10, "total": 10},
        "Science": {"correct": 5, "total": 10}
      }
    },
    "exam_session": {
      "student_id": 1,
      "question_answers": [],
      "total_questions": 20,
      "exam_duration": 1200,
      "copy_paste_count": 0
    },
    "student_performance": {
      "weak_topics": ["Science"],
      "strong_topics": ["Math"],
      "current_score": 75,
      "target_score": 90
    }
  }'
```

✅ **Expected:** Complete analysis with all 4 features

---

## 📊 Testing Checklist

### Performance Prediction ✓
- [ ] Endpoint responds correctly
- [ ] Calculates pass probability
- [ ] Provides recommendations
- [ ] Handles edge cases (0 correct, all correct)

### Question Recommendation ✓
- [ ] Identifies weak topics
- [ ] Prioritizes correctly
- [ ] Generates study plan
- [ ] Returns valid recommendations

### Cheat Detection ✓
- [ ] Flags fast answers
- [ ] Detects copy-paste
- [ ] Identifies unusual patterns
- [ ] Generates risk scores

### Study Plan ✓
- [ ] Creates weekly schedule
- [ ] Allocates time properly
- [ ] Includes daily activities
- [ ] Predicts improvement

### API Health ✓
- [ ] `/api/ai-analytics/health` responds
- [ ] ML Backend reachable
- [ ] Fallback works when ML offline
- [ ] Error handling works

---

## 🐛 Debugging Tips

### Check ML Backend Logs
```bash
# Terminal running ML backend should show:
# POST /api/ml/predict-performance
# POST /api/ml/recommend-questions
# POST /api/ml/detect-cheating
# POST /api/ml/generate-study-plan
```

### Check Spring Boot Logs
```
curl http://localhost:8080/api/ai-analytics/dashboard/1
# Should return 200 OK with AI features listed
```

### Browser Console
```javascript
// Monitor API calls
fetch('http://localhost:8080/api/ai-analytics/health')
  .then(r => r.json())
  .then(d => console.log('AI System:', d))
```

### Test ML Backend Directly
```bash
curl http://localhost:5000/health
# Should return: {"status": "ML Backend is running"}
```

---

## 📝 Expected Results Summary

| Feature | Test Case | Expected Output |
|---------|-----------|-----------------|
| **Performance** | 15/20 correct | ~75% score, 0.80 pass prob |
| **Recommendation** | 40% Science accuracy | Science flagged as weak |
| **Cheat Detection** | 3 sec/question | HIGH risk with flags |
| **Study Plan** | Science weak | 5-day schedule focusing Math |
| **Health Check** | GET health | Status: Online |

---

## 🚨 Common Issues & Fixes

### Issue: 404 on AI endpoints
**Fix:** Verify Spring Boot has compiled new controller
```bash
.\mvnw.cmd clean compile
```

### Issue: ML Backend timeout
**Fix:** Ensure Flask is running on port 5000
```bash
curl http://localhost:5000/health
```

### Issue: CORS errors
**Fix:** Flask CORS already enabled in app.py, should work

### Issue: Port conflicts
**Fix:** 
- ML Backend: Change port in `app.py` line `app.run(..., port=5000)`
- Spring Boot: Already configured for 7890 in `application.properties`

---

## 🎯 Demo Presentation

**For showcasing to stakeholders:**

1. **Performance Prediction Demo**
   - Show real-time predictions during exam
   - Display confidence levels
   - Highlight pass probability calculations

2. **CheatDetection Demo**
   - Show suspicious patterns being flagged
   - Display risk scores
   - Explain automatic monitoring

3. **Recommendation Demo**
   - Show weak topics identified
   - Display personalized study plan
   - Highlight improvement predictions

4. **Metrics Dashboard**
   - Show system architecture
   - Display API response times
   - Highlight accuracy metrics

---

## ✅ Sign-Off Checklist

- [ ] ML Backend starts without errors
- [ ] Spring Boot compiles successfully
- [ ] All 4 features responding to requests
- [ ] Admin dashboard shows AI features
- [ ] Database connections working
- [ ] Fallback mechanism tested
- [ ] API documentation accurate
- [ ] Performance acceptable (<500ms)

---

**Ready for production!** 🚀🎉

For detailed API documentation, see `ML_FEATURES_README.md`
