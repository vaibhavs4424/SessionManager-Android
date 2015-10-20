/*
 * Copyright (C) 2015 Vaibhav Shukla
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sessionmanager;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Session Manager Activity that comes on top of the project screen and displays the timer
 *
 * @author VaibhavS
 */

public class SessionActivity extends Activity {

    // ///////////////////Member Variables//////////////////////////////

    //Timer to display the countdown
    private CountDownTimer countDownTimer = null;

    //Timer textviews
    private TextView mTimerTextMin = null;
    private TextView mTimerTextSec = null;

    //Timer layout
    private LinearLayout mTimerScreen = null;


    //Animation variables to animate the activity on top of the existing activity

    private Animation layout_slideIn, layout_slideOut;



    // ///////////////////Member Methods//////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Cancel out any timer if it is previously set
        Constants.screenTimeOutTimer.cancel();

        setContentView(R.layout.session_main);
        mTimerScreen = (LinearLayout) findViewById(com.example.sessionmanager.R.id.timer_screen);
        layout_slideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        mTimerScreen.startAnimation(layout_slideIn);

        //Reset the timer whenever user clicks on the screen
        mTimerScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (countDownTimer != null)
                    countDownTimer.cancel();
                finishActivity();
                (new SessionManager()).resetSessionTimer();
                if (SessionManager.mBackgroundTimer != null) {
                    SessionManager.mBackgroundTimer.cancel();
                    SessionManager.mBackgroundTimer = null;
                }
                Constants.CmTimerDisplayInMillis = Constants.mTimerDisplayInMillis;
            }
        });
        mTimerTextMin = (TextView) findViewById(R.id.txt_timer_min);
        mTimerTextSec = (TextView) findViewById(R.id.txt_timer_sec);
        Long timer = Constants.CmTimerDisplayInMillis;

        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(timer, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    mTimerTextMin.setText((millisUntilFinished / 60000) + "");
                    mTimerTextSec.setText((TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millisUntilFinished))) + "");
                }

                @Override
                public void onFinish() {
                    Log.d("SESS", "Countdown Timer Finished. User Logged out.");
                    Constants.countDownFinishListener.onCountDownFinish();
                    this.cancel();
                    Constants.CmTimerDisplayInMillis = Constants.mTimerDisplayInMillis;
                    finishActivity();
                    // (new SessionManager()).resetSessionTimer();
                    if (SessionManager.mBackgroundTimer != null) {
                        SessionManager.mBackgroundTimer.cancel();
                        SessionManager.mBackgroundTimer = null;
                    }

                }
            };
        }

        // start the countdown just before the view is being displayed on the screen
        countDownTimer.start();

    }

    //Method called whenever the timer is destroyed and activity is about to be finished

    public void finishActivity() {

        layout_slideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out);
        mTimerScreen.startAnimation(layout_slideOut);
        layout_slideOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                mTimerScreen.setVisibility(View.GONE);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
