import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier, RandomForestRegressor
from sklearn.preprocessing import StandardScaler
import joblib
import json
from datetime import datetime

class PerformancePredictor:
    """Predicts student performance and pass/fail probability"""
    def __init__(self):
        self.classifier = RandomForestClassifier(n_estimators=100, random_state=42)
        self.regressor = RandomForestRegressor(n_estimators=100, random_state=42)
        self.is_trained = False
    
    def predict_score_and_pass(self, student_data):
        """
        Predict final score and pass/fail probability
        student_data: {
            'correct_answers': int,
            'total_questions': int,
            'time_spent': int (seconds),
            'avg_time_per_q': float,
            'previous_scores': [list],
            'difficulty_level': int (1-5)
        }
        """
        if not self.is_trained:
            # Default prediction if model not trained
            current_percentage = (student_data['correct_answers'] / student_data['total_questions']) * 100
            pass_probability = min(current_percentage / 100, 0.95)
            predicted_final_score = current_percentage + (np.random.normal(0, 5))
            
            return {
                'predicted_final_score': max(0, min(100, predicted_final_score)),
                'pass_probability': pass_probability,
                'confidence': 0.6,
                'recommendation': 'Continue with medium difficulty questions'
            }
        
        # Feature extraction
        features = self._extract_features(student_data)
        predicted_score = self.regressor.predict([features])[0]
        pass_prob = self.classifier.predict_proba([features])[0][1]
        
        return {
            'predicted_final_score': max(0, min(100, predicted_score)),
            'pass_probability': float(pass_prob),
            'confidence': 0.85,
            'recommendation': self._get_recommendation(pass_prob, predicted_score)
        }
    
    def _extract_features(self, student_data):
        current_score = (student_data['correct_answers'] / student_data['total_questions']) * 100
        avg_previous = np.mean(student_data['previous_scores']) if student_data['previous_scores'] else current_score
        
        return [
            current_score,
            student_data['difficulty_level'],
            student_data['avg_time_per_q'],
            len(student_data['previous_scores']),
            avg_previous,
            student_data['time_spent']
        ]
    
    def _get_recommendation(self, pass_prob, score):
        if pass_prob > 0.8:
            return "Excellent! Keep up the momentum"
        elif pass_prob > 0.6:
            return "Good progress. Review difficult topics"
        elif pass_prob > 0.4:
            return "Medium progress. Focus on weak areas"
        else:
            return "Challenge detected. Consider revision"


class QuestionRecommender:
    """Recommends questions based on student weak areas"""
    def __init__(self):
        self.user_performance = {}
    
    def recommend_questions(self, student_id, exam_results):
        """
        Recommend questions based on weak areas
        exam_results: {
            'total_questions': int,
            'questions_by_topic': {
                'topic': {'correct': int, 'total': int}
            }
        }
        """
        weak_topics = []
        
        for topic, scores in exam_results['questions_by_topic'].items():
            accuracy = scores['correct'] / scores['total']
            if accuracy < 0.6:  # Less than 60% accuracy
                weak_topics.append({
                    'topic': topic,
                    'accuracy': accuracy,
                    'priority': 1 if accuracy < 0.4 else 2
                })
        
        # Sort by priority and accuracy
        weak_topics.sort(key=lambda x: (x['priority'], x['accuracy']))
        
        return {
            'weak_topics': weak_topics[:5],
            'top_3_recommendations': [t['topic'] for t in weak_topics[:3]],
            'study_plan': self._create_study_plan(weak_topics)
        }
    
    def _create_study_plan(self, weak_topics):
        plan = []
        for topic in weak_topics[:3]:
            plan.append({
                'day': len(plan) + 1,
                'topic': topic['topic'],
                'focus_areas': f"Practice {topic['topic']} problems",
                'time_allocation': 45 + (10 * (3 - len(plan)))
            })
        return plan


class CheatDetector:
    """Detects suspicious patterns indicating cheating"""
    def __init__(self):
        pass
    
    def detect_suspicious_activity(self, exam_session):
        """
        Detect cheating patterns
        exam_session: {
            'student_id': int,
            'question_answers': [{'q_id': int, 'time_spent': int, 'answer': int}],
            'total_questions': int,
            'exam_duration': int,
            'copy_paste_count': int
        }
        """
        flags = []
        risk_score = 0
        
        # Flag 1: Unusually fast answers
        avg_time = exam_session['exam_duration'] / exam_session['total_questions']
        for q in exam_session['question_answers']:
            if q['time_spent'] < avg_time * 0.2:  # Less than 20% average time
                flags.append(f"Question {q['q_id']}: Suspiciously fast ({q['time_spent']}s)")
                risk_score += 2
        
        # Flag 2: Copy-paste activities
        if exam_session['copy_paste_count'] > exam_session['total_questions'] * 0.3:
            flags.append(f"High copy-paste activity detected ({exam_session['copy_paste_count']} times)")
            risk_score += 3
        
        # Flag 3: Perfect accuracy too fast
        correct_ratio = sum(1 for q in exam_session['question_answers'] if q.get('is_correct')) / exam_session['total_questions']
        avg_session_time = sum(q['time_spent'] for q in exam_session['question_answers']) / exam_session['total_questions']
        
        if correct_ratio > 0.95 and avg_session_time < avg_time * 0.5:
            flags.append("Perfect accuracy with unusually fast timing pattern")
            risk_score += 3
        
        # Flag 4: Inactive patterns
        if any(q['time_spent'] > exam_session['exam_duration'] * 0.5 for q in exam_session['question_answers']):
            flags.append("Unusual inactivity detected on specific questions")
            risk_score += 1
        
        risk_level = 'LOW' if risk_score < 2 else 'MEDIUM' if risk_score < 5 else 'HIGH'
        
        return {
            'risk_score': risk_score,
            'risk_level': risk_level,
            'flags': flags,
            'action': self._get_action(risk_level)
        }
    
    def _get_action(self, risk_level):
        actions = {
            'LOW': 'No action needed',
            'MEDIUM': 'Monitor student - request manual review if score is high',
            'HIGH': 'Flag for manual review by admin - suspicious activity detected'
        }
        return actions.get(risk_level, 'Unknown')


class StudyPlanGenerator:
    """Generates personalized study plans"""
    def __init__(self):
        pass
    
    def generate_study_plan(self, student_performance):
        """
        Generate personalized weekly study plan
        student_performance: {
            'weak_topics': [list of topics],
            'strong_topics': [list of topics],
            'current_score': float,
            'target_score': float
        }
        """
        days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
        plan = []
        
        weak_topics = student_performance['weak_topics']
        strong_topics = student_performance['strong_topics']
        
        # Distribute weak topics across 5 days
        for i, day in enumerate(days[:5]):
            if i < len(weak_topics):
                plan.append({
                    'day': day,
                    'topic': weak_topics[i],
                    'focus': 'Weak Area Improvement',
                    'activities': [
                        'Read theory (20 mins)',
                        'Solve 10 practice questions (30 mins)',
                        'Review mistakes (10 mins)'
                    ],
                    'time_allocation': 60,
                    'difficulty': 'Medium'
                })
        
        # Weekend review
        plan.append({
            'day': 'Saturday',
            'topic': 'Mixed Review',
            'focus': 'Consolidation',
            'activities': [
                'Mini quiz on weak topics (30 mins)',
                'Practice exam questions (30 mins)'
            ],
            'time_allocation': 60,
            'difficulty': 'Mixed'
        })
        
        plan.append({
            'day': 'Sunday',
            'topic': 'Rest & Analysis',
            'focus': 'Recovery',
            'activities': [
                'Review study progress (15 mins)',
                'Plan next week (15 mins)'
            ],
            'time_allocation': 30,
            'difficulty': 'Light'
        })
        
        return {
            'weekly_plan': plan,
            'estimated_improvement': 15 + (5 if strong_topics else 0),
            'expected_score': min(100, student_performance['target_score'] + 10),
            'consistency_required': 'Daily 60-90 minutes'
        }
