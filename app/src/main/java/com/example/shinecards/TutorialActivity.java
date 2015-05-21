package com.example.shinecards;

/**
 * Created by keithmartin on 2/8/15.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

import com.google.android.glass.touchpad.Gesture;

import java.lang.Override;import java.lang.String;import java.util.Arrays;
import java.util.List;

/**
 * An implementation of the game that acts as a tutorial, restricting certain gestures to match
 * the instruction phrases on the screen.
 */

// Method to exit needed
public class TutorialActivity extends BaseQuizActivity {

    /** The index of the "swipe to pass" card in the tutorial model. */
    private static final int SWIPE_TO_PASS_CARD = 0;

    private GlassCardsModel mModel;

    private ViewFlipper mPhraseFlipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar in tutorial mode.
        findViewById(R.id.status_bar).setVisibility(View.GONE);
    }

    /** Overridden to load the fixed tutorial phrases from the application's resources. */


    protected GlassCardsModel createGlassCardModel() {
        List<String> tutorialPhrases = Arrays.asList(getResources().getStringArray(
                R.array.tutorial_phrases));
        List<String> dummyPhrases =  Arrays.asList("hi");
        return new GlassCardsModel(tutorialPhrases,dummyPhrases);
    }



    /**
     * Overridden to only allow the tap gesture on the "Tap to score" screen and to only allow the
     * swipe gesture on the "Swipe to pass" screen. The game is also automatically ended when the
     * final card is either tapped or swiped.
     */

    @Override
    protected void handleGameGesture(Gesture gesture) {
        int phraseIndex = createGlassCardsModel().getCurrentQuestionIndex();
        switch (gesture) {
            case TAP:
                if (phraseIndex != SWIPE_TO_PASS_CARD && phraseIndex != 2) {
                    next();
                } else if (phraseIndex == 2) {
                    showPrevious();
                }
                break;
            case SWIPE_RIGHT:
                if (phraseIndex == SWIPE_TO_PASS_CARD) {
                   next();
                }
                break;
            case SWIPE_LEFT:
                return;
            default:
                return;

        }


        // Finish the tutorial if we transitioned away from the final card.

    }


}
