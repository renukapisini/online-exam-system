# 🏗️ ML-Enhanced Exam System - Architecture & Flow Diagrams

## System Architecture Diagram

```
╔════════════════════════════════════════════════════════════════════════════╗
║                         ONLINE EXAM SYSTEM v2.0                           ║
║                     (Enhanced with AI/ML Capabilities)                    ║
╚════════════════════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│                          🌐 WEB BROWSER LAYER                             │
│                     http://localhost:7890                                  │
│  ┌─────────────────────────────────────────────────────────────────────┐  │
│  │  • Student Dashboard      • Admin Dashboard                         │  │
│  │  • Exam Interface         • Analytics & Reports                     │  │
│  │  • Performance Analysis   • Cheat Detection Dashboard               │  │
│  │  • Study Plans            • Student Management                      │  │
│  └─────────────────────────────────────────────────────────────────────┘  │
│                                    │                                       │
│                                    │ (HTTP/HTML)                          │
│                                    ▼                                       │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│                         🖥️  SPRING BOOT LAYER                              │
│                  Port: 7890 (UI) / 8080 (REST API)                        │
│  ┌─────────────────────────────────────────────────────────────────────┐  │
│  │                                                                     │  │
│  │  Controllers:                                                       │  │
│  │  ├── AdminController                                               │  │
│  │  ├── StudentController                                             │  │
│  │  ├── ExamController                                                │  │
│  │  ├── AuthController          ← 🆕 AIAnalyticsController (NEW)    │  │
│  │  └── ReviewController                                              │  │
│  │                                                                     │  │
│  │  Services:                                                          │  │
│  │  ├── UserService                                                   │  │
│  │  ├── FileStorageService      ← 🆕 MLAnalyticsService (NEW)      │  │
│  │  └── CustomUserDetailsService                                      │  │
│  │                                                                     │  │
│  │  Features:                                                          │  │
│  │  ├── Spring Security (Authentication)                              │  │
│  │  ├── Spring Data JPA (Database)                                    │  │
│  │  ├── Thymeleaf (Templating)   ← 🆕 REST API Endpoints (NEW)     │  │
│  │  └── Dev Tools                                                     │  │
│  │                                                                     │  │
│  └─────────────────────────────────────────────────────────────────────┘  │
│                    │ (JSON/REST)               │ (REST)                    │
│                    ▼                           ▼                           │
├───────────────────────────┬───────────────────────────────────────────────┤
│                           │                                               │
│         ┌─────────────────┴────────────────┐                              │
│         │                                  │                              │
│         ▼                                  ▼                              │
│  ╔═══════════════════╗           ╔════════════════════════╗              │
│  │   DATA LAYER      │           │   ML INTEGRATION       │              │
│  ╠═══════════════════╣           ╠════════════════════════╣              │
│  │ H2 Database       │           │ REST Client            │              │
│  │ ./data/           │           │ (calls ML Backend)     │              │
│  │  examdb.mv.db     │           ├────────────────────────┤              │
│  │                   │           │ Error Handling:        │              │
│  │ Tables:           │           │ • Fallback Mode        │              │
│  │ • users           │           │ • Graceful Degradation │              │
│  │ • exams           │           │ • Retry Logic          │              │
│  │ • questions       │           ╚════════════════════════╝              │
│  │ • exam_results    │                    │ (REST)                       │
│  │ • exam_answers    │                    ▼                              │
│  ╚═══════════════════╝        ┌──────────────────────────┐               │
│                                │                          │               │
│                                │  🤖 ML BACKEND API      │               │
│                                │  (Flask on Port 5000)   │               │
│                                │                          │               │
│                                │  🆕 NEW SERVICE       │               │
│                                └──────────────────────────┘               │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │ (REST/JSON)
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│                        🐍 PYTHON ML BACKEND LAYER                          │
│                           Port: 5000                                       │
│  ┌─────────────────────────────────────────────────────────────────────┐  │
│  │                          FLASK API SERVER                           │  │
│  │  ┌───────────────────────────────────────────────────────────────┐ │  │
│  │  │ Endpoints:                                                    │ │  │
│  │  │ • /api/ml/predict-performance                                │ │  │
│  │  │ • /api/ml/recommend-questions                                │ │  │
│  │  │ • /api/ml/detect-cheating                                    │ │  │
│  │  │ • /api/ml/generate-study-plan                                │ │  │
│  │  │ • /api/ml/batch-analysis                                     │ │  │
│  │  │ • /health                                                     │ │  │
│  │  └───────────────────────────────────────────────────────────────┘ │  │
│  │                                                                     │  │
│  │  ┌───────────────────────────────────────────────────────────────┐ │  │
│  │  │ ML MODELS:                                                    │ │  │
│  │  │                                                                │ │  │
│  │  │ 1️⃣  PerformancePredictor                                     │ │  │
│  │  │     • Random Forest Classifier                               │ │  │
│  │  │     • Random Forest Regressor                                │ │  │
│  │  │     • Predicts: Final Score & Pass Probability              │ │  │
│  │  │                                                                │ │  │
│  │  │ 2️⃣  QuestionRecommender                                      │ │  │
│  │  │     • Topic Analysis Engine                                  │ │  │
│  │  │     • Detects: Weak areas (< 60% accuracy)                  │ │  │
│  │  │     • Returns: Study plan & topic prioritization            │ │  │
│  │  │                                                                │ │  │
│  │  │ 3️⃣  CheatDetector                                            │ │  │
│  │  │     • Statistical Anomaly Detection                          │ │  │
│  │  │     • Flags: Unusual timing patterns                         │ │  │
│  │  │     • Risk Levels: LOW, MEDIUM, HIGH                        │ │  │
│  │  │                                                                │ │  │
│  │  │ 4️⃣  StudyPlanGenerator                                       │ │  │
│  │  │     • Rule-based Engine                                      │ │  │
│  │  │     • Generates: Weekly personalized schedules              │ │  │
│  │  │     • Includes: Daily activities & time allocation           │ │  │
│  │  │                                                                │ │  │
│  │  └───────────────────────────────────────────────────────────────┘ │  │
│  │                                                                     │  │
│  │  Dependencies:                                                      │  │
│  │  • scikit-learn (ML algorithms)                                    │  │
│  │  • pandas (Data manipulation)                                      │  │
│  │  • numpy (Numerical computing)                                     │  │
│  │  • Flask (Web framework)                                           │  │
│  │                                                                     │  │
│  └─────────────────────────────────────────────────────────────────────┘  │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Data Flow Diagram - During Exam

```
┌─────────────────┐
│   Student      │
│   Takes Exam   │
└────────┬────────┘
         │
         │ Answers questions
         │ Submits responses
         │
         ▼
┌──────────────────────┐            ┌─────────────────────────┐
│  Spring Boot: Exam  │ ─────────▶  │  Database: Store        │
│  Controller         │             │  Answers                │
└──────┬───────────────┘             └─────────────────────────┘
       │
       │ Extract metrics:
       │ • Correct answers
       │ • Time per question
       │ • Total time spent
       │ • Difficulty level
       │
       ▼
┌────────────────────────────┐
│ MLAnalyticsService (Java)  │
│ Prepares data payload      │
└──────┬─────────────────────┘
       │
       │ JSON POST Request
       │ w/ exam metrics
       │
       ▼
┌─────────────────────────────────┐
│ Flask API (Port 5000)           │
│ /api/ml/predict-performance     │
└─┬──────────────┬─────────────────┘
  │              │
  │ Features:    │ Extract:
  │ • Current %  │ • Random Forest Model
  │ • Time/Q     │ • Classification & Regression
  │ • Difficulty │ • Apply algorithms
  │ • History    │
  │              ▼
  │         ┌─────────────────┐
  │         │ ML Models Run   │
  │         │ Scikit-learn    │
  │         └────────┬────────┘
  │                  │
  │                  ▼
  │         ┌─────────────────────────┐
  │         │ Predictions Generated   │
  │         │ • Final Score: 82.5%    │
  │         │ • Pass Prob: 0.85       │
  │         │ • Confidence: 0.85      │
  │         │ • Recommendation: "..." │
  │         └────────┬────────────────┘
  │                  │
  │                  │ JSON Response
  │                  │
  ▼──────────────────┘
┌─────────────────────────┐
│ Spring Boot (Java)      │
│ Receives predictions    │
└──────┬──────────────────┘
       │
       │ Add to model/session
       │
       ▼
┌── ─────────────────┐
│ Thymeleaf Template │
│ Renders UI with    │
│ • Score prediction │
│ • Pass probability │
│ • Recommendations  │
└──────────┬─────────┘
           │
           ▼
┌──────────────────────┐
│ Student's Browser    │
│ Displays insights    │
└──────────────────────┘
```

---

## Data Flow Diagram - After Exam (Full Analysis)

```
┌─────────────────────┐
│  Exam Submitted     │
│  Data Available     │
└────────┬────────────┘
         │
         ▼
┌─────────────────────────────────────────┐
│ MLAnalyticsService.performBatchAnalysis │
│ Calls all 4 ML features at once         │
└───┬─┬─┬─┬────────────────────────────────┘
    │ │ │ │
    │ │ │ └─────────────────..................
    │ │ └────────────────.......................
    │ └────────────...........................
    └─────.......
         │  │  │  │
         ▼  ▼  ▼  ▼
    ┌────────────────────────────────────────────┐
    │      Flask API (Python) - Port 5000        │
    ├────────────────────────────────────────────┤
    │                                            │
    │ ┌──────────────────────┐                  │
    │ │ 1. Performance       │  ◄─┐             │
    │ │    Prediction        │────┤─ Features  │
    │ │                      │    │             │
    │ │ Returns:             │    │             │
    │ │ • Score: 82.5        │    │             │
    │ │ • Pass: 0.85         │    │             │
    │ └──────────────────────┘    │             │
    │                             │             │
    │ ┌──────────────────────┐    │             │
    │ │ 2. Question          │  ◄─┤             │
    │ │    Recommendation    │    │             │
    │ │                      │    │             │
    │ │ Returns:             │    │             │
    │ │ • Weak: Science      │    │             │
    │ │ • Plan: 2 weeks      │    │             │
    │ └──────────────────────┘    │             │
    │                             │             │
    │ ┌──────────────────────┐    │             │
    │ │ 3. Cheat Detection   │  ◄─┤             │
    │ │                      │    │             │
    │ │ Returns:             │    │             │
    │ │ • Risk: LOW          │    │             │
    │ │ • Flags: []          │    │             │
    │ └──────────────────────┘    │             │
    │                             │             │
    │ ┌──────────────────────┐    │             │
    │ │ 4. Study Plan        │  ◄─┘             │
    │ │                      │                  │
    │ │ Returns:             │                  │
    │ │ • Schedule: 5 days   │                  │
    │ │ • Topics: Math, Sci  │                  │
    │ └──────────────────────┘                  │
    │                                            │
    └────┬───────────────────────────────────────┘
         │
         │ All results in JSON
         │
         ▼
    ┌─────────────────────────┐
    │ Spring Boot Aggregates  │
    │ All 4 responses         │
    └────┬────────────────────┘
         │
         │ Creates comprehensive
         │ AI dashboard payload
         │
         ▼
    ┌──────────────────────────────┐
    │ Browser Display:             │
    │ • Performance Card           │
    │ • Weak Topics Info           │
    │ • Risk Assessment            │
    │ • Study Plan Widget          │
    │ • Admin Cheat Flags          │
    └──────────────────────────────┘
```

---

## Key Integration Points

```
Java (Spring Boot)              Python (Flask)
─────────────────────────────────────────────────

AIAnalyticsController   ◄────► Flask App
   │                                │
   ├─ POST predict-perf             │ ├─ POST /api/ml/predict-performance
   │  │                             │ │  → PerformancePredictor
   │  └──────────────────────────────► 
   │                                │
   ├─ POST recommend-questions      │ ├─ POST /api/ml/recommend-questions
   │  │                             │ │  → QuestionRecommender
   │  └──────────────────────────────►
   │                                │
   ├─ POST detect-cheating          │ ├─ POST /api/ml/detect-cheating
   │  │                             │ │  → CheatDetector
   │  └──────────────────────────────►
   │                                │
   ├─ POST generate-study-plan      │ ├─ POST /api/ml/generate-study-plan
   │  │                             │ │  → StudyPlanGenerator
   │  └──────────────────────────────►
   │                                │
   └─ GET health                    │ └─ GET /health
      │                             │    → {"status": "running"}
      └──────────────────────────────►


All Responses:
    ◄──────────────────────────────
    JSON with:
    • predictions
    • recommendations
    • risk analysis
    • study plans
```

---

## Deployment Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                 PRODUCTION ENVIRONMENT                     │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│   Server/Machine with:                                     │
│   • Java 17+                                               │
│   • Python 3.8+                                            │
│   • 2GB+ RAM                                               │
│   • 500MB+ Disk                                            │
│                                                             │
│  ┌──────────────────┐      ┌──────────────────┐           │
│  │  Spring Boot     │      │  Flask Server    │           │
│  │  (mvnw.cmd)      │      │  (python app.py) │           │
│  │  Port: 7890      │      │  Port: 5000      │           │
│  │  Port: 8080 (API)│      │                  │           │
│  └────────┬─────────┘      └────────┬─────────┘           │
│           │                         │                     │
│           └─────────────────────────┘                     │
│              (REST/JSON)                                  │
│                                                             │
│  ┌──────────────────────────────────┐                    │
│  │  H2 Database                     │                    │
│  │  ./data/examdb.mv.db             │                    │
│  │  (Auto-created)                  │                    │
│  └──────────────────────────────────┘                    │
│                                                             │
│  ┌──────────────────────────────────┐                    │
│  │  Browser Access                  │                    │
│  │  http://localhost:7890           │                    │
│  └──────────────────────────────────┘                    │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## Startup Sequence

```
Timeline:
─────────

T=0s    ┌─ User double-clicks start_ml_backend.bat
        │  "Creating virtual environment..."
        │  "Installing dependencies..."
        │
T=15s   └─ ✅ Flask API ready on http://localhost:5000
        
T=20s   ┌─ User runs "mvnw spring-boot:run" in new terminal
        │  "Creating database schema..."
        │  "Bootstrapping Spring Data JPA repositories..."
        │
T=35s   └─ ✅ Spring Boot ready on http://localhost:7890

T=40s   ┌─ User accesses http://localhost:7890
        │  
T=45s   └─ ✅ Both systems operational and integrated
        
        System fully initialized!
        Ready to enhance exams with AI/ML
```

---

This architecture ensures:
- ✅ Clean separation of concerns (Java ← → Python)
- ✅ Scalability (each service can scale independently)
- ✅ Fault tolerance (graceful degradation if ML fails)
- ✅ Easy maintenance (modular design)
- ✅ Performance (async API calls with fallback)
