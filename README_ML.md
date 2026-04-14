# 🎓 AI-Enhanced Online Exam System

## Welcome! 👋

Your online exam system has been upgraded with **4 powerful AI/ML features** that make it unique and intelligent!

---

## What's New? 🚀

```
✨ Student Performance Prediction      ─ Real-time score & pass probability
✨ Intelligent Question Recommendation  ─ Personalized study paths
✨ Automated Cheat Detection           ─ Suspicious activity monitoring
✨ Smart Study Plan Generator          ─ Personalized weekly schedules
```

---

## Quick Start (3 Steps) ⚡

### 1️⃣ Start ML Backend (Python)

**Windows:**
```bash
cd ml_backend
start_ml_backend.bat
```

**Linux/macOS:**
```bash
cd ml_backend
chmod +x start_ml_backend.sh
./start_ml_backend.sh
```

✅ You'll see: `Running on http://127.0.0.1:5000`

---

### 2️⃣ Start Spring Boot (Java)

**Windows:**
```bash
.\mvnw.cmd clean spring-boot:run
```

**Linux/macOS:**
```bash
./mvnw spring-boot:run
```

✅ You'll see: `Tomcat initialized with port 7890`

---

### 3️⃣ Access Application

👉 **http://localhost:7890**

**Admin Login:**
- Username: `admin`
- Password: `adminpass`

---

## 📚 Documentation

### Choose Your Path:

**🏃 In a Hurry?**
👉 Read: [`QUICKSTART.md`](QUICKSTART.md) (5 min read)

**🔧 Setting Up?**
👉 Read: [`INSTALLATION_GUIDE.md`](INSTALLATION_GUIDE.md) (10 min read)

**🤖 Want to Understand Features?**
👉 Read: [`ML_FEATURES_README.md`](ML_FEATURES_README.md) (15 min read)

**📊 Need Architecture Details?**
👉 Read: [`ARCHITECTURE.md`](ARCHITECTURE.md) (Diagrams & flows)

**🧪 Testing & Demo?**
👉 Read: [`TESTING_GUIDE.md`](TESTING_GUIDE.md) (Practical examples)

**📋 Complete Summary?**
👉 Read: [`ML_ENHANCEMENT_SUMMARY.md`](ML_ENHANCEMENT_SUMMARY.md) (Full overview)

---

## 🎯 How It Works

### For Students:

```
┌──────────────────────────────────────┐
│  1. Take Exam                        │
└──────────────────────────────────────┘
                ▼
┌──────────────────────────────────────┐
│  2. AI Analyzes Performance          │
│     • Checks answers                 │
│     • Calculates score               │
│     • Detects patterns               │
└──────────────────────────────────────┘
                ▼
┌──────────────────────────────────────┐
│  3. Get Personalized Insights        │
│     • Predicted final score          │
│     • Pass probability               │
│     • Weak topics identified         │
│     • Study plan generated           │
└──────────────────────────────────────┘
                ▼
┌──────────────────────────────────────┐
│  4. Follow Study Plan                │
│     • Weekly schedule                │
│     • Daily activities               │
│     • Practice questions             │
│     • Score improvement              │
└──────────────────────────────────────┘
```

### For Teachers/Admins:

```
┌──────────────────────────────────────┐
│  Monitor AI Insights                 │
│  • Class performance trends          │
│  • Individual student progress       │
│  • Cheat detection alerts            │
│  • Weakness patterns across class    │
└──────────────────────────────────────┘
```

---

## 🏗️ System Architecture

```
Web Browser
    ▼
Spring Boot (Port 7890)
    ▼
MLAnalyticsService
    ▼
Flask API (Port 5000)
    ▼
ML Models
    ├─ RandomForest Classifier
    ├─ RandomForest Regressor
    ├─ Statistical Analyzer
    └─ Rule Engine
```

---

## 📁 Project Structure

```
online-exam-system-master/
│
├── 📚 Documentation (Read in this order)
│   ├── README.md                      ← You are here
│   ├── QUICKSTART.md                  ← ⭐ Start here if new
│   ├── INSTALLATION_GUIDE.md
│   ├── ML_FEATURES_README.md
│   ├── ARCHITECTURE.md                ← System design
│   ├── TESTING_GUIDE.md               ← How to test
│   └── ML_ENHANCEMENT_SUMMARY.md      ← Complete summary
│
├── 🐍 ML Backend (Python) - NEW
│   ├── ml_backend/
│   │   ├── app.py                     ← Flask API server
│   │   ├── ml_models.py               ← ML implementations
│   │   ├── requirements.txt           ← Python dependencies
│   │   ├── start_ml_backend.bat       ← Windows startup
│   │   └── start_ml_backend.sh        ← Linux/macOS startup
│
├── ☕ Spring Boot (Java)
│   ├── src/main/java/
│   │   └── com/example/exam/
│   │       ├── controller/
│   │       │   ├── AdminController.java
│   │       │   ├── StudentController.java
│   │       │   ├── AuthController.java
│   │       │   └── AIAnalyticsController.java  ← NEW
│   │       ├── service/
│   │       │   ├── UserService.java
│   │       │   ├── FileStorageService.java
│   │       │   └── MLAnalyticsService.java    ← NEW
│   │       ├── model/ (unchanged)
│   │       ├── repository/ (unchanged)
│   │       ├── config/ (unchanged)
│   │       └── OnlineExamApplication.java
│   │
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── templates/ (HTML files)
│   │
│   ├── pom.xml                         ← Maven config
│   ├── mvnw                            ← Maven wrapper (Linux)
│   └── mvnw.cmd                        ← Maven wrapper (Windows)
│
├── 💾 Database
│   ├── data/
│   │   └── examdb.mv.db               ← H2 database (auto-created)
│
└── 📦 Build/Deploy
    └── target/                         ← Compiled output
```

---

## 🔧 System Requirements

| Component | Version | Purpose |
|-----------|---------|---------|
| Java | 17+ | Spring Boot runtime |
| Python | 3.8+ | ML backend |
| Maven | 3.6+ | Build tool (included) |
| RAM | 2GB+ | Min. system memory |
| Disk | 500MB+ | Database + dependencies |

---

## ✅ Startup Checklist

Before you start:

- [ ] Java 17+ installed
- [ ] Python 3.8+ installed
- [ ] Both terminals available
- [ ] Port 5000 free (ML backend)
- [ ] Port 7890 free (Spring Boot UI)
- [ ] Port 8080 free (Spring Boot API)

---

## 🎯 API Endpoints

### AI Analytics Endpoints

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/api/ai-analytics/predict-performance` | POST | Performance prediction |
| `/api/ai-analytics/recommend-questions` | POST | Question recommendations |
| `/api/ai-analytics/detect-cheating` | POST | Cheat detection |
| `/api/ai-analytics/generate-study-plan` | POST | Study plan generation |
| `/api/ai-analytics/dashboard/{id}` | GET | AI dashboard |
| `/api/ai-analytics/health` | GET | System health |

---

## 🆘 Common Issues

### Issue: "Python not found"
**Solution:** Install Python from https://www.python.org/downloads/

### Issue: "Port 5000 already in use"
**Solution:** Change port in `ml_backend/app.py` line `app.run(..., port=5000)`

### Issue: "Maven build fails"
**Solution:** Run `.\mvnw.cmd clean compile`

### Issue: "ML backend not responding"
**Solution:** Ensure Flask is running and port 5000 is accessible

### For more: See [`TESTING_GUIDE.md`](TESTING_GUIDE.md#-troubleshooting)

---

## 🎓 Demo Workflow

**Complete from start to finish (15 minutes):**

1. ✅ Start both services (ML + Spring Boot)
2. ✅ Login as admin (admin/adminpass)
3. ✅ Create sample exam with 5 questions
4. ✅ Register as student
5. ✅ Take exam with mixed performance
6. ✅ View AI predictions
7. ✅ Check admin dashboard
8. ✅ Review cheat detection results

See [`TESTING_GUIDE.md`](TESTING_GUIDE.md) for detailed steps.

---

## 📊 Feature Overview

### 1. Performance Prediction

**What:** Real-time score prediction during exam
**Input:** # correct, total questions, time spent
**Output:** Predicted score, pass probability, recommendation

### 2. Question Recommendation

**What:** Identifies weak topics for study
**Input:** Exam results by topic
**Output:** Weak topics, priority list, study plan

### 3. Cheat Detection

**What:** Detects suspicious exam behavior
**Input:** Answer timing, copy-paste count
**Output:** Risk score, flags, recommendation

### 4. Study Plans

**What:** Personalized weekly study schedule
**Input:** Weak topics, current/target score
**Output:** Daily activities, time allocation, improvement estimate

---

## 💡 Why This Is Special

Most exam systems have:
- ✗ Basic student scoring
- ✗ Static exam templates
- ✗ No real analytics

**This system has:**
- ✓ AI predictions during exam
- ✓ Personalized learning paths
- ✓ Automated cheat detection
- ✓ Data-driven insights
- ✓ Weekly study plans

---

## 🚀 Next Steps

1. **First Time?**
   - Read [`QUICKSTART.md`](QUICKSTART.md)
   - Follow the 3-step setup
   - Test with sample exam

2. **Want Details?**
   - Read [`ML_FEATURES_README.md`](ML_FEATURES_README.md)
   - Review [`ARCHITECTURE.md`](ARCHITECTURE.md)
   - Explore API endpoints

3. **Ready to Test?**
   - Follow [`TESTING_GUIDE.md`](TESTING_GUIDE.md)
   - Run demo scenarios
   - Verify all features work

4. **Customize?**
   - Edit ML models in `ml_backend/ml_models.py`
   - Add custom thresholds
   - Integrate with your UI

---

## 📞 File Reference

**Quick Access to Key Files:**

| Need | File | Time |
|------|------|------|
| Quick Setup | QUICKSTART.md | 5 min |
| Installation Help | INSTALLATION_GUIDE.md | 10 min |
| Feature Details | ML_FEATURES_README.md | 15 min |
| Architecture | ARCHITECTURE.md | Diagrams |
| Testing Info | TESTING_GUIDE.md | How-to |
| Full Summary | ML_ENHANCEMENT_SUMMARY.md | Complete |

---

## ✨ You're All Set!

Your enhanced exam system is ready with:
- ✅ 4 AI/ML features
- ✅ Full documentation
- ✅ Testing guides
- ✅ Demo scenarios
- ✅ Production-ready code

**Start with Step 1 above and enjoy!**

---

## 📅 Version Info

- **Version:** 1.0
- **Status:** ✅ Production Ready
- **Date:** April 12, 2026
- **Platform:** Windows/macOS/Linux

---

## 🎉 Welcome to AI-Enhanced Exams!

Your online exam system is now powered by machine learning.

**Happy Testing! 🚀**

---

*For questions, refer to the documentation files listed above.*
*Each document focuses on a specific aspect of the system.*
