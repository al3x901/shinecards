package com.example.shinecards;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.example.shinecards.utils.files.SToast;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;



public class WelcomeActivity extends Activity {

	private final Handler mHandler = new Handler();
	
    private final GestureDetector.BaseListener mBaseListener = new GestureDetector.BaseListener() {
        @Override
        public boolean onGesture(Gesture gesture) {
            if (gesture == Gesture.TAP) {
                openOptionsMenu();
                return true;
            } else {
                return false;
            }
        }
    };
	
    /** Gesture detector used to present the options menu. */
    private GestureDetector mGestureDetector;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        
        mGestureDetector = new GestureDetector(this).setBaseListener(mBaseListener);
    }
    
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The startXXX() methods start a new activity, and if we call them directly here then
        // the new activity will start without giving the menu a chance to slide back down first.
        // By posting the calls to a handler instead, they will be processed on an upcoming pass
        // through the message queue, after the animation has completed, which results in a
        // smoother transition between activities.
        switch (item.getItemId()) {
            case R.id.new_game:
                SToast.showL(this,"NEW GAME");
                Log.d("Welcome","new game hit");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        startGame();
                    }
                });
                return true;
            case R.id.instructions:
                SToast.showL(this,"INSTRUCTIONS");
                Log.d("Welcome","instructions hit");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        startTutorial();
                    }
                });

                return true;

            default:
                return false;
        }
    }
    
    private void startGame() {
        startActivity(new Intent(this, QuizActivity.class));
        finish();
    }
    private void startTutorial() {
        startActivity(new Intent(this, TutorialActivity.class));
        finish();
    }


}

