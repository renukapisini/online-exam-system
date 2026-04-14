# 🤖 ML Enhancement for Online Exam System

This document explains the AI/ML features integrated into the exam system to make it unique and intelligent.

## 🎯 Features Implemented

### 1. **Student Performance Prediction**
- Predicts final exam score in real-time
- Calculates pass/fail probability
- Provides confidence level
- Recommends study strategies based on current performance

**Endpoint:** `POST /api/ai-analytics/predict-performance`

**Example:**
```bash
curl -X POST "http://localhost:8080/api/ai-analytics/predict-performance?correctAnswers=15&totalQuestions=20&timeSpent=1200&avgTimePerQ=60&difficultyLevel=3"
```

---

### 2. **Intelligent Question Recommendation**
- Identifies weak topics from exam performance
- Recommends specific questions to practice
- Creates personalized learning paths
- Prioritizes high-impact topics

**Endpoint:** `POST /api/ai-analytics/recommend-questions`

**Example JSON:**
```json
{
  "studentId": 1,
  "questionsByTopic": {
    "Mathematics": {"correct": 8, "total": 10},
    "Science": {"correct": 5, "total": 10},
    "English": {"correct": 9, "total": 10}
  }
}
```

---

### 3. **Cheat Detection System**
- Detects unusually fast answers
- Flags copy-paste activities
- Identifies suspicious timing patterns
- Generates risk scores and reports

**Risks Detected:**
- `LOW`: Normal exam behavior
- `MEDIUM`: Suspicious patterns (monitor)
- `HIGH`: Strong cheating indicators

**Endpoint:** `POST /api/ai-analytics/detect-cheating`

---

### 4. **Smart Study Plan Generator**
- Creates personalized weekly study plans
- Allocates time based on weak areas
- Includes daily activities and targets
- Predicts improvement over time

**Generates:**
- Daily study schedule
- Time allocation per topic
- Practice questions list
- Expected score improvement

**Endpoint:** `POST /api/ai-analytics/generate-study-plan`

---

## 🚀 Setup Instructions

### Step 1: Install Python (if not already installed)
1. Download from: https://www.python.org/downloads/
2. Check installation: `python --version`

### Step 2: Setup ML Backend

```bash
# Navigate to ml_backend folder
cd ml_backend

# Create virtual environment
python -m venv venv

# Activate virtual environment
# On Windows:
venv\Scripts\activate
# On macOS/Linux:
source venv/bin/activate

# Install dependencies
pip install -r requirements.txt

# Run Flask app
python app.py
```

✅ ML Backend will be running on: `http://localhost:5000`

---

### Step 3: Update Spring Boot Configuration

Add to `application.properties`:

```properties
# ML Backend Configuration
ml.backend.url=http://localhost:5000
```

---

### Step 4: Test the Integration

#### Test ML Backend Health:
```bash
curl http://localhost:5000/health
```

#### Test Performance Prediction:
```bash
curl -X POST http://localhost:8080/api/ai-analytics/predict-performance \
  -G \
  -d "correctAnswers=15" \
  -d "totalQuestions=20" \
  -d "timeSpent=1200" \
  -d "avgTimePerQ=60" \
  -d "difficultyLevel=2"
```

#### Test AI Dashboard:
```bash
curl http://localhost:8080/api/ai-analytics/dashboard/1
```

---

## 📊 How to Use in Application

### For Students:

1. **During Exam:**
   - System predicts real-time performance
   - Shows estimated final score
   - Displays pass probability

2. **After Exam:**
   - View performance analysis
   - See weak areas identified
   - Get recommended questions to practice

3. **Study Plan:**
   - Access personalized weekly plan
   - Follow daily study recommendations
   - Track improvement over time

### For Admin:

1. **Student Analytics:**
   - Monitor performance trends
   - Identify struggling students
   - View cheating detection flags

2. **Reports:**
   - Generate AI-powered reports
   - Identify patterns across exams
   - Make data-driven decisions

---

## 🔧 Optional Enhancements

### Add to Exam Controller:

```java
@PostMapping("/submit")
public String submitExam(@RequestParam Long examId,
                         @RequestParam Map<String, String> submittedAnswers,
                         RedirectAttributes redirectAttributes,
                         @Autowired MLAnalyticsService mlService) {
    
    // ... existing code ...
    
    // Call ML Service for analysis
    Map<String, Object> analysis = mlService.performBatchAnalysis(buildAnalysisData(...));
    
    // Store ML insights in database or session
    redirectAttributes.addFlashAttribute("mlInsights", analysis);
    
    return "redirect:/exam/result?id=" + resultId;
}
```

---

## 📈 Architecture

```
┌─────────────────────────────────┐
│   Spring Boot Application       │
│   (Online Exam System)          │
└────────────┬────────────────────┘
             │
             │ (REST calls)
             │
┌────────────▼────────────────────┐
│   ML Backend (Flask API)        │
│   Port: 5000                    │
│                                 │
│ - Performance Predictor         │
│ - Question Recommender          │
│ - Cheat Detector                │
│ - Study Plan Generator          │
└─────────────────────────────────┘
```

---

## 🎓 Performance Metrics

Machine Learning models used:
- **Random Forest (Classification)** - For pass/fail prediction
- **Random Forest (Regression)** - For score prediction
- **Statistical Analysis** - For cheat detection
- **Rule-based Engine** - For study plan generation

---

## 📝 Notes

- ML Backend must be running before Spring Boot app starts
- If ML Backend is unavailable, system uses fallback predictions
- Models improve accuracy with more training data over time
- All student data is processed securely

---

## 🆘 Troubleshooting

### Issue: ML Backend not connecting
**Solution:** 
```bash
# Ensure Flask app is running
python ml_backend/app.py

# Verify connection
curl http://localhost:5000/health
```

### Issue: Port 5000 already in use
**Solution:**
```bash
# Change port in app.py and update ml.backend.url in application.properties
```

### Issue: Python packages not installing
**Solution:**
```bash
pip install --upgrade pip
pip install -r requirements.txt --no-cache-dir
```

---

## 📞 Support

For issues or enhancements, check:
1. ML Backend logs: `ml_backend/` folder
2. Spring Boot logs: Console
3. Verify both services are running on correct ports
