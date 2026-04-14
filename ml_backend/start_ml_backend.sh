#!/bin/bash

# ML Backend Startup Script for macOS/Linux

echo ""
echo "========================================"
echo "Starting ML Backend (Flask API)"
echo "========================================"
echo ""

# Check if Python is installed
if ! command -v python3 &> /dev/null; then
    echo "ERROR: Python 3 is not installed"
    echo "Please install Python from: https://www.python.org/downloads/"
    exit 1
fi

# Navigate to ml_backend folder
cd ml_backend

# Check if virtual environment exists
if [ ! -d "venv" ]; then
    echo "Creating virtual environment..."
    python3 -m venv venv
fi

# Activate virtual environment
echo "Activating virtual environment..."
source venv/bin/activate

# Install/upgrade dependencies
echo "Installing dependencies..."
pip install -r requirements.txt --quiet

# Start Flask app
echo ""
echo "========================================"
echo "ML Backend starting on http://localhost:5000"
echo "========================================"
echo ""

python app.py
