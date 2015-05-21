package com.example.shinecards;

/**
 * Created by keithmartin on 2/8/15.
 */

import android.os.Bundle;

import com.example.shinecards.model.Question;
import com.example.shinecards.model.Quiz;
import com.example.shinecards.utils.files.ReadInQuizzesFromXML;
import com.google.android.glass.touchpad.Gesture;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends BaseQuizActivity {

    Quiz mQuizzes;
    ArrayList<Question> mQuiz;
    private boolean inAnswer = false;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    protected void onStart(){
        super.onStart();
    }

    @Override
    protected GlassCardsModel createGlassCardModel() {
        //this method is ran before the oncreate of this class

        /**
         * populate the quizzes for the app
         */
        ArrayList<Question> quizzes = ReadInQuizzesFromXML.importQuizzes(this, "quizzes.xml");
        /**
         * get a quiz from quizzes.
         * in this case, get a quiz with all questions of category "Calc 2"
         */
        mQuizzes = new Quiz(quizzes, "astronomy 100", 0);
        mQuiz = mQuizzes.quiz;



        List<String> allQuestions = new ArrayList<String>();
        List<String> allAnswers = new ArrayList<String>();
        if(mQuiz !=null) {
            for (Question q : mQuiz) {
                String question = q.getQuestion();
                String answer = q.getAnswer();
                allQuestions.add(question);
                allAnswers.add(answer);
            }
        } else {
            String question = "#No quiz questions available.";
            allQuestions.add(question);
        }
        GlassCardsModel newGlassCardsModel = new GlassCardsModel(allQuestions,allAnswers);
        newGlassCardsModel.populateQuiz(mQuiz);

        return newGlassCardsModel;
    }

    @Override
    protected void handleGameGesture(Gesture gesture) {

        if(inAnswer){
            inAnswer = false;
            switch (gesture) {
                case SWIPE_LEFT:
                    tugRight();
                    inAnswer = true;
                    break;
                case SWIPE_RIGHT:
                    tugLeft();
                    inAnswer = true;
                    break;
                case TAP:
                    showQuestion();
                    break;
            }


        } else {

        switch (gesture) {
            case TAP:
                showAnswer();
                inAnswer = true;
                break;
            case SWIPE_RIGHT:
                next();
                break;
            case SWIPE_LEFT:
                showPrevious();
                break;
            default:
                break;
        }
        }
    }

}
