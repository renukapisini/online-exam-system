from flask import Flask, request, jsonify
from flask_cors import CORS
from ml_models import PerformancePredictor, QuestionRecommender, CheatDetector, StudyPlanGenerator
import traceback

app = Flask(__name__)
CORS(app)

# Initialize models
predictor = PerformancePredictor()
recommender = QuestionRecommender()
cheat_detector = CheatDetector()
study_plan_gen = StudyPlanGenerator()

@app.route('/health', methods=['GET'])
def health():
    """Health check endpoint"""
    return jsonify({'status': 'ML Backend is running'}), 200


@app.route('/api/ml/predict-performance', methods=['POST'])
def predict_performance():
    """
    Predict student performance
    Expected JSON:
    {
        'correct_answers': int,
        'total_questions': int,
        'time_spent': int,
        'avg_time_per_q': float,
        'previous_scores': [list],
        'difficulty_level': int
    }
    """
    try:
        data = request.json
        prediction = predictor.predict_score_and_pass(data)
        return jsonify(prediction), 200
    except Exception as e:
        return jsonify({'error': str(e), 'trace': traceback.format_exc()}), 400


@app.route('/api/ml/recommend-questions', methods=['POST'])
def recommend_questions():
    """
    Recommend questions based on weak areas
    Expected JSON:
    {
        'student_id': int,
        'exam_results': {
            'total_questions': int,
            'questions_by_topic': {
                'topic': {'correct': int, 'total': int}
            }
        }
    }
    """
    try:
        data = request.json
        recommendations = recommender.recommend_questions(
            data['student_id'],
            data['exam_results']
        )
        return jsonify(recommendations), 200
    except Exception as e:
        return jsonify({'error': str(e), 'trace': traceback.format_exc()}), 400


@app.route('/api/ml/detect-cheating', methods=['POST'])
def detect_cheating():
    """
    Detect suspicious activity
    Expected JSON:
    {
        'student_id': int,
        'question_answers': [
            {'q_id': int, 'time_spent': int, 'answer': int, 'is_correct': bool}
        ],
        'total_questions': int,
        'exam_duration': int,
        'copy_paste_count': int
    }
    """
    try:
        data = request.json
        detection = cheat_detector.detect_suspicious_activity(data)
        return jsonify(detection), 200
    except Exception as e:
        return jsonify({'error': str(e), 'trace': traceback.format_exc()}), 400


@app.route('/api/ml/generate-study-plan', methods=['POST'])
def generate_study_plan():
    """
    Generate personalized study plan
    Expected JSON:
    {
        'weak_topics': [list],
        'strong_topics': [list],
        'current_score': float,
        'target_score': float
    }
    """
    try:
        data = request.json
        plan = study_plan_gen.generate_study_plan(data)
        return jsonify(plan), 200
    except Exception as e:
        return jsonify({'error': str(e), 'trace': traceback.format_exc()}), 400


@app.route('/api/ml/batch-analysis', methods=['POST'])
def batch_analysis():
    """
    Complete analysis: performance, recommendations, cheating, and study plan
    """
    try:
        data = request.json
        
        results = {
            'performance_prediction': predictor.predict_score_and_pass(data.get('performance_data', {})),
            'question_recommendations': recommender.recommend_questions(
                data.get('student_id', 0),
                data.get('exam_results', {})
            ),
            'cheat_detection': cheat_detector.detect_suspicious_activity(data.get('exam_session', {})),
            'study_plan': study_plan_gen.generate_study_plan(data.get('student_performance', {}))
        }
        
        return jsonify(results), 200
    except Exception as e:
        return jsonify({'error': str(e), 'trace': traceback.format_exc()}), 400


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
