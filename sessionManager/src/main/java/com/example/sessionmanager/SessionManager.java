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

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.sessionmanager.Constants.FromBackGround;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class to start and maintain the session timer throughout the application
 *
 * @author Vaibhavs
 */
public class SessionManager implements FromBackGround {

    //Context variable
    private Context mContext = null;

    //Variable to check whether the app is in foreground or background
    private boolean mForeground = false;

    //Variables to hold the value till the app was in background
    public static CountDownTimer mBackgroundTimer = null;
    private Long mBackgroundTimerLeftMillis = null;

    public SessionManager() {

    }

    /**
     * Constructor to initialize all the values
     *
     * @param mContext
     * @param mSessionTimeInMillis
     * @param mTimerDisplayInMillis
     * @author Vaibhav Shukla
     */
    public SessionManager(final Context mContext, Long mSessionTimeInMillis, Long mTimerDisplayInMillis) {
        super();
        this.mContext = mContext;
        Constants.mSessionTimeInMillis = mSessionTimeInMillis;
        Constants.CmSessionTimeInMillis = mSessionTimeInMillis;
        Constants.mTimerDisplayInMillis = mTimerDisplayInMillis;
        Constants.CmTimerDisplayInMillis = mTimerDisplayInMillis;
        Constants.fromBackgroundListener = this;
        Constants.countDownFinishListener = (CountDownFinish) mContext;

        if (Constants.screenTimeOutTimer != null) {
            Constants.screenTimeOutTimer.cancel();

        }
        Constants.screenTimeOutTimer = new CountDownTimer(mSessionTimeInMillis - mTimerDisplayInMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Constants.mSessionTimeLeftInMillis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                System.out.println("Timer Completed.");
                Log.d("SESSION", "Show the session time out Dialog");
                this.cancel();

                try {
                    mForeground = new ForegroundCheckTask().execute(mContext).get();
                    Log.i("Foreground", "App is in Foreground: " + mForeground);

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // If the app is in background or foreground
                if (mForeground) {
                    // If in foreground directly call the timer screen
                    Intent intent = new Intent(mContext, SessionActivity.class);
                    mContext.startActivity(intent);
                } else {
                    // If in background start a separate background timer with the same time of timer screen
                    // which runs and if user logs before the timer is finished
                    // show the timer screen with the left time or else logout
                    mBackgroundTimer = new CountDownTimer(Constants.CmTimerDisplayInMillis, 100) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            mBackgroundTimerLeftMillis = millisUntilFinished;
                        }

                        @Override
                        public void onFinish() {

                            Log.d("SESSION", "Countdown Timer Finished. User Logged out.");
                            Constants.countDownFinishListener.onCountDownFinish();
                            this.cancel();
                            // (new SessionManager()).resetSessionTimer();

                            Constants.CmTimerDisplayInMillis = Constants.mTimerDisplayInMillis;

                        }
                    };
                    mBackgroundTimer.start();
                }

            }
        };

        Constants.screenTimeOutTimer.start();
    }

    public void startTimerActivity() {

		/*
         * Intent intent = new Intent(mContext, SessionActivity.class);
		 * 
		 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); mContext.startActivity(intent);
		 */
    }

    public void resetSessionTimer() {
        if (Constants.screenTimeOutTimer != null) {
            Constants.screenTimeOutTimer.cancel();
            Constants.screenTimeOutTimer.start();

        }
    }


    /**
     * method to stop the countdown timer whenever the logout button is clicked
     *
     * @aurthor Vaibhav Shukla
     */
    public void stopCountDownTimer() {
        if (Constants.screenTimeOutTimer != null) {
            Constants.screenTimeOutTimer.cancel();
        }
    }

    /**
     * Class that will be implemented to perform some activity on session timeout
     *
     * @author Vaibhav Shukla
     */

    class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public interface CountDownFinish {

        /**
         * handle end of the countdown
         */
        public void onCountDownFinish();
    }

    @Override
    public void fromBackgroundAction() {

        if (mBackgroundTimer != null && mBackgroundTimerLeftMillis != null && mBackgroundTimerLeftMillis > 1000) {
            Constants.CmTimerDisplayInMillis = mBackgroundTimerLeftMillis;
            Intent intent = new Intent(mContext, SessionActivity.class);
            mContext.startActivity(intent);

        } else if (Constants.mSessionTimeLeftInMillis != null && Constants.mSessionTimeLeftInMillis > 1000) {
            resetSessionTimer();
        }
    }
}
