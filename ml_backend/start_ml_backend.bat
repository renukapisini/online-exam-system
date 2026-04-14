@echo off
REM ML Backend Startup Script for Windows

echo.
echo ========================================
echo Starting ML Backend (Flask API)
echo ========================================
echo.

REM Check if Python is installed
python --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Python is not installed or not in PATH
    echo Please install Python from: https://www.python.org/downloads/
    pause
    exit /b 1
)

REM Navigate to ml_backend folder
cd ml_backend

REM Check if virtual environment exists
if not exist venv (
    echo Creating virtual environment...
    python -m venv venv
)

REM Activate virtual environment
echo Activating virtual environment...
call venv\Scripts\activate.bat

REM Install/upgrade dependencies
echo Installing dependencies...
pip install -r requirements.txt --quiet

REM Start Flask app
echo.
echo ========================================
echo ML Backend starting on http://localhost:5000
echo ========================================
echo.
python app.py

pause
