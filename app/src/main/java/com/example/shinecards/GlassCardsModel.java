package com.example.shinecards;

import com.example.shinecards.model.Question;

import java.util.ArrayList;
import java.util.List;



/*represents the state of the application, has the list of questions.*/
public class GlassCardsModel {
	// phrases in this instance of the game 
	private final String [] mQuestions;
	// answers with matched up indexes to the corresponding the questions 
	private final String [] mAnswers;

    private ArrayList<Question> mQuiz;
	
	// index of the current question
	private int mCurrentQuestionPosition;
	
	// index of the current answer 
	private int mCurrentAnswerPosition;
	
	public GlassCardsModel (List <String> questions, List <String> answers){
		
		mQuestions = questions.toArray(new String [questions.size()]);
		mAnswers = answers.toArray(new String [questions.size()]);
		
		mCurrentQuestionPosition = 0;
		mCurrentAnswerPosition = 0;
		
	}

    public void populateQuiz(ArrayList<Question> quiz){
        mQuiz = quiz;
    }
	
	public int getCurrentQuestionIndex(){
		return mCurrentQuestionPosition;
	}
	
	// returns the question at the specified index
	public String getQuestion (int index) {
		return mQuestions[index];
	}

    public int getArraySize(){
       return mQuestions.length;
    }

    public int getQuizSize() { return mQuiz.size();}
	
	public String getAnswer(int index){
		return mAnswers[index];
	}
	// returns the current question.
	public String getCurrentQuestion(){
		return getQuestion(mCurrentQuestionPosition);
	}
	
	public String getCurrentAnswer(){
		return getAnswer(mCurrentAnswerPosition);
	}

    public String getCurrentCategory(){
        return mQuiz.get(mCurrentQuestionPosition).getCategory();
    }

    public int getCurrentDifficulty(){
        return mQuiz.get(mCurrentQuestionPosition).getDifficulty();
    }

    public String getCurrentHeading(){
        return mQuiz.get(mCurrentQuestionPosition).getHeading();
    }
	
	public void next(){
		advanceQuestion();
		advanceAnswer();
	}
	
    
	
	public boolean advance(){
		if (mQuestions.length < mCurrentQuestionPosition){
			advanceQuestion();
			advanceAnswer();
			return true;
		}
		return false;
	}
	// advances the apps answer index counter.
	public void advanceQuestion() {
		mCurrentQuestionPosition = mCurrentQuestionPosition + 1;
	} 
	
	// advances the apps answer index
	public void advanceAnswer() {
		mCurrentAnswerPosition = mCurrentAnswerPosition +1;
	}
	
	public void previous() {

        decrementAnswer();
		decrementQuestion();

	}
	
	public void decrementQuestion() {
		mCurrentQuestionPosition = mCurrentQuestionPosition - 1;
	} 
	
	// advances the apps answer index
	public void decrementAnswer() {
		mCurrentAnswerPosition = mCurrentAnswerPosition - 1;
	}
	
	
	
}
