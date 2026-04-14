# 🚀 Quick Start Guide - Enhanced Exam System with AI/ML

## What's New? 🎉

Your exam system now includes **4 powerful ML features**:
1. ✅ **Student Performance Prediction** - Real-time score & pass probability
2. ✅ **Question Recommendation** - Personalized study paths  
3. ✅ **Cheat Detection** - Suspicious activity detection
4. ✅ **Study Plan Generator** - Weekly personalized plans

---

## Step 1: Start ML Backend (Python Flask)

### For Windows:
**Simply double-click this file:**
```
ml_backend/start_ml_backend.bat
```

**Or manually:**
```bash
cd ml_backend
python -m venv venv
venv\Scripts\activate
pip install -r requirements.txt
python app.py
```

✅ You should see:
```
 * Running on http://127.0.0.1:5000
```

### For macOS/Linux:
```bash
cd ml_backend
python -m venv venv
source venv/bin/activate
pip install -r requirements.txt
python app.py
```

---

## Step 2: Start Spring Boot Application

### Windows PowerShell:
```powershell
cd "online-exam-system-master"
.\mvnw.cmd spring-boot:run
```

### macOS/Linux:
```bash
cd online-exam-system-master
./mvnw spring-boot:run
```

✅ You should see:
```
Tomcat initialized with port 7890
Started OnlineExamApplication in X seconds
```

---

## Step 3: Access the Application

### Main Application:
👉 **http://localhost:7890**

### AI Analytics API:
👉 **http://localhost:8080/api/ai-analytics/health**

### Admin Login:
- Username: `admin`
- Password: `adminpass`

---

## 🎯 How to Use ML Features

### During Exam - Performance Prediction

```javascript
// API Call
fetch('/api/ai-analytics/predict-performance', {
  method: 'POST',
  params: {
    correctAnswers: 15,
    totalQuestions: 20,
    timeSpent: 1200,
    avgTimePerQ: 60,
    difficultyLevel: 2
  }
})
.then(response => response.json())
.then(data => console.log(data));

// Response Example
{
  "predicted_final_score": 82.5,
  "pass_probability": 0.85,
  "confidence": 0.85,
  "recommendation": "Excellent! Keep up the momentum"
}
```

### After Exam - Question Recommendations

```javascript
fetch('/api/ai-analytics/recommend-questions', {
  method: 'POST',
  body: JSON.stringify({
    studentId: 1,
    questionsByTopic: {
      "Mathematics": {"correct": 8, "total": 10},
      "Science": {"correct": 5, "total": 10},
      "English": {"correct": 9, "total": 10}
    }
  })
})
.then(response => response.json())
.then(data => {
  console.log("Weak Topics:", data.weak_topics);
  console.log("Study Plan:", data.study_plan);
});
```

### Cheat Detection

```javascript
fetch('/api/ai-analytics/detect-cheating', {
  method: 'POST',
  params: {
    studentId: 1,
    totalQuestions: 20,
    examDuration: 1800,
    copyPasteCount: 0
  },
  body: JSON.stringify([
    {"q_id": 1, "time_spent": 45, "answer": 1, "is_correct": true},
    {"q_id": 2, "time_spent": 30, "answer": 2, "is_correct": true},
    ...
  ])
})
.then(response => response.json())
.then(data => {
  console.log("Risk Level:", data.risk_level); // LOW, MEDIUM, or HIGH
  console.log("Flags:", data.flags);
});
```

### Study Plan Generation

```javascript
fetch('/api/ai-analytics/generate-study-plan', {
  method: 'POST',
  params: {
    weakTopics: "Mathematics,Science",
    strongTopics: "English,History",
    currentScore: 65,
    targetScore: 85
  }
})
.then(response => response.json())
.then(data => {
  console.log("Weekly Plan:", data.weekly_plan);
  console.log("Expected Score:", data.expected_score);
});
```

---

## 📊 System Architecture

```
┌────────────────────────────────────────┐
│    Web Browser                         │
│    (http://localhost:7890)             │
└────────────────┬───────────────────────┘
                 │
                 │ (Views & API Requests)
                 │
┌────────────────▼───────────────────────┐
│    Spring Boot Application             │
│    Port: 7890 / 8080                   │
│    - Controllers                       │
│    - Services                          │
│    - H2 Database                       │
└────────────────┬───────────────────────┘
                 │
                 │ (REST Calls)
                 │
┌────────────────▼───────────────────────┐
│    ML Backend (Flask)                  │
│    Port: 5000                          │
│    - Performance Predictor             │
│    - Question Recommender              │
│    - Cheat Detection                   │
│    - Study Plan Generator              │
└────────────────────────────────────────┘
```

---

## ✨ Unique Features

### 1. Real-time Performance Tracking
- Monitors student progress during exam
- Predicts final score before exam ends
- Shows pass probability with confidence level

### 2. Intelligent Learning Paths
- Analyzes weak areas automatically
- Recommends priority topics to study
- Creates personalized schedules

### 3. Automated Integrity Checking
- Detects suspicious answer patterns
- Flags unusually fast responses
- Monitors for copy-paste activities
- Generates risk scores for admin review

### 4. AI-Powered Study Plans
- Customized weekly schedules
- Daily activities with time allocation
- Estimated score improvement
- Progress tracking

---

## 🔧 Troubleshooting

### Issue: Port 5000 already in use
```bash
# Windows
netstat -ano | findstr :5000
taskkill /PID <PID> /F

# macOS/Linux
lsof -i :5000
kill -9 <PID>
```

### Issue: Python not found
```bash
# Reinstall Python and add to PATH
https://www.python.org/downloads/
```

### Issue: ML Backend not responding
```bash
# Check if Flask is running on port 5000
curl http://localhost:5000/health

# Restart ML backend
```

### Issue: Dependencies installation fails
```bash
pip install --upgrade pip
pip install -r ml_backend/requirements.txt --no-cache-dir
```

---

## 📈 Performance Tips

1. **Keep ML Backend running** - Better predictions with continuous service
2. **Monitor Logs** - Check console for any errors or warnings
3. **Database Health** - H2 database stored in `/data/examdb.mv.db`
4. **API Performance** - ML predictions take 100-500ms typically

---

## 🎓 Demo Workflow

### Create a Test Scenario:

1. **Login as Admin** (admin/adminpass)
2. **Create Exam** with 20 questions
3. **Create Student Account** (register)
4. **Login as Student** and take exam
5. **View Performance Prediction** - See AI insights
6. **Get Study Plan** - Follow recommendations
7. **Check Admin Dashboard** - View cheat detection results

---

## 📞 File Structure

```
online-exam-system-master/
├── src/main/java/...
│   ├── controller/
│   │   ├── AIAnalyticsController.java  ← NEW
│   │   └── ...
│   └── service/
│       ├── MLAnalyticsService.java     ← NEW
│       └── ...
├── ml_backend/                         ← NEW
│   ├── app.py                          ← Flask API
│   ├── ml_models.py                    ← ML Models
│   ├── requirements.txt
│   └── start_ml_backend.bat
├── ML_FEATURES_README.md               ← Documentation
└── QUICKSTART.md                       ← This file
```

---

## 🎉 That's it! 

Your enhanced exam system is ready with AI/ML capabilities!

**Questions?** Check `ML_FEATURES_README.md` for detailed documentation.
