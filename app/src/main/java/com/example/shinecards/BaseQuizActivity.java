package com.example.shinecards;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.shinecards.model.Question;
import com.example.shinecards.utils.StaticMethods;
import com.example.shinecards.utils.files.SToast;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;


// figure out how to come back up from answer."boolean on handleGamesGesture"
public abstract class BaseQuizActivity extends Activity {

    /**
     * Model that stores the state of the game.
     */
    private GlassCardsModel mModel;
    /**
     * View flipper with two views used to provide the flinging animations between phrases.
     */
    private ViewFlipper mPhraseFlipper;

    /**
     * Animation used to briefly tug a phrase when the user swipes left.
     */
    private Animation mTugRightAnimation;
    private Animation mTugLeftAnimation;
    private Animation mPrevious;
    private Animation mNext;
    private Animation mDown;
    private Animation mUp;

    /**
     * Detects gestures during the game.
     */
    private GestureDetector mGestureDetector;

    /**
     * views for displaying data
     */
    private TextView mQuizIndicatorTextView;
    protected RelativeLayout mQuizActivityLayout;


    private final GestureDetector.BaseListener mBaseListener = new GestureDetector.BaseListener() {
        @Override

        public boolean onGesture(Gesture gesture) {
            switch (gesture) {
                case SWIPE_LEFT:
                    handleGameGesture(gesture);
                    return true;
                case TAP:
                case SWIPE_RIGHT:
                    // Delegate tap and swipe right (forward) to the subclass so that the
                    // tutorial and actual game can handle them differently.
                    handleGameGesture(gesture);
                    return true;
                default:
                    return false;
            }

        }
    };


    protected abstract void handleGameGesture(Gesture gesture);

    /**
     * Subclasses must override this method to create and return the data model that will be used
     * by the game.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gameplay);

        mQuizActivityLayout = (RelativeLayout) findViewById(R.id.quizActivityLayout);
        mPhraseFlipper = (ViewFlipper) findViewById(R.id.phrase_flipper);
        mQuizIndicatorTextView = (TextView) findViewById(R.id.quiz_indicator);

        mGestureDetector = new GestureDetector(this).setBaseListener(mBaseListener);

        mTugRightAnimation = AnimationUtils.loadAnimation(this, R.anim.tug_right);
        mTugLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.tug_left);
        mPrevious = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        mNext = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        mDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        mUp = AnimationUtils.loadAnimation(this,R.anim.slide_up);

        mModel = createGlassCardModel();
        updateDisplayToQuestion();

    }


    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }

    protected GlassCardsModel createGlassCardsModel() {
        return mModel;
    }

    protected abstract GlassCardsModel createGlassCardModel();

    protected void tugRight() {
        mPhraseFlipper.startAnimation(mTugRightAnimation);
    }

    protected void tugLeft() {
        mPhraseFlipper.startAnimation(mTugLeftAnimation);
    }

    private void updateDisplayToQuestion() {
        getCurrentTextView().setText(mModel.getCurrentQuestion());
        getCurrentTextView().setTextColor(Color.WHITE);
        updateQuizBackground();
        int currentQuestionNumber = mModel.getCurrentQuestionIndex()+1;
        mQuizIndicatorTextView.setText(mModel.getCurrentCategory() + ": " + currentQuestionNumber  + " of " + mModel.getQuizSize());
    }

    private void updateDisplayToAnswer() {
        getCurrentTextView().setText(mModel.getCurrentAnswer());
        getCurrentTextView().setTextColor(Color.WHITE);
    }

    private void updateQuizBackground() {
        switch(mModel.getCurrentDifficulty()) {
            case Question.DIFFICULTY_INT_LOW:
                SToast.showS(this, this, "green");
                mQuizActivityLayout.setBackgroundColor(
                        StaticMethods.getColorFromResources(this, R.color.nephritis)
                );
                break;
            case Question.DIFFICULTY_INT_MEDIUM:
                SToast.showS(this, this, "orange");
                mQuizActivityLayout.setBackgroundColor(
                        StaticMethods.getColorFromResources(this, R.color.orange)
                );
                break;
            case Question.DIFFICULTY_INT_HIGH:
                SToast.showS(this, this, "red");
                mQuizActivityLayout.setBackgroundColor(
                        StaticMethods.getColorFromResources(this, R.color.pomegranate)
                );
                break;
            default:
                mQuizActivityLayout.setBackgroundColor(
                        StaticMethods.getColorFromResources(this, R.color.peter_river)
                );
        }



    }

    protected void next() {
        if (mModel.getCurrentQuestionIndex() < mModel.getArraySize() - 1) {
            mModel.next();
            mPhraseFlipper.startAnimation(mNext);
            updateDisplayToQuestion();
        } else {
            tugLeft();
        }
    }

    protected void showPrevious() {
        if (mModel.getCurrentQuestionIndex() > 0) {
            mModel.previous();
            mPhraseFlipper.startAnimation(mPrevious);
            updateDisplayToQuestion();
        } else {
            tugRight();
        }
    }

    protected void showAnswer() {
        mPhraseFlipper.startAnimation(mDown);
        updateDisplayToAnswer();
        int currentQuestionNumber = mModel.getCurrentQuestionIndex()+1;
        mQuizIndicatorTextView.setText(mModel.getCurrentCategory() + ": Answer " + currentQuestionNumber  + " of " + mModel.getQuizSize());
    }


    protected void showQuestion(){
        mPhraseFlipper.startAnimation(mUp);
        updateDisplayToQuestion();

    }


    private TextView getCurrentTextView() {
        return (TextView) mPhraseFlipper.getCurrentView();
    }


}
